

package com.company.ipcclient.repositories

import com.company.ipcclient.infrastruture.clients.interfaces.IServerClient
import com.company.ipcclient.infrastruture.database.ChannelDatabase
import com.company.ipcclient.model.ChannelModel
import com.company.ipcclient.model.CommandModel
import com.company.ipcclient.model.CommandPropModel
import com.company.ipcclient.model.LogModel
import com.company.ipcclient.model.local_dto.LocalChannel
import com.company.ipcclient.model.local_dto.LocalCommand
import com.company.ipcclient.model.local_dto.LocalProp
import com.company.ipcclient.model.local_dto.Logs
import com.company.ipcclient.repositories.interfaces.IChannelRepository
import com.company.ipcclient.repositories.interfaces.ICommandRepository
import com.company.ipcclient.repositories.interfaces.ILogsRepository
import com.company.ipcclient.repositories.interfaces.IPropRepository

class RemoteRepository(private val mServerClient: IServerClient,
                       private val mChannelDatabase: ChannelDatabase) : IChannelRepository,
                                                                        ICommandRepository,
                                                                        IPropRepository,
                                                                        ILogsRepository {
    //Channel methods

    //First request to IPC Server to get all channels
    override suspend fun getChannels(): List<ChannelModel> {
        return mServerClient.getChannels()
    }

    override suspend fun getAllChannels(): List<ChannelModel> {
        return mChannelDatabase.Dao().getAllChannels().map { list ->
            ChannelModel(list.channelNum, list.channelDescription)
        }
    }

    override fun getSelectedChannel(): ChannelModel {
        val daoChannel = mChannelDatabase.Dao().getSelectedChannel(true)
        return ChannelModel(daoChannel.channelNum, daoChannel.channelDescription)
    }

    override suspend fun putChannel(channelModel : ChannelModel, selected: Boolean) {
        mChannelDatabase.Dao().insertChannel(LocalChannel(channelModel.channel).apply {
            channelNum = channelModel.channel
            channelDescription = channelModel.description
            isChannelSelected = selected
        })
    }

    override suspend fun putAllChannels(listChannelModel: List<ChannelModel>) {
        val daoListChannel: List<LocalChannel>
        daoListChannel = listChannelModel.map { chModel -> LocalChannel(chModel.channel).apply {
            channelNum = chModel.channel
            channelDescription = chModel.description
            isChannelSelected = false
        } }
        mChannelDatabase.Dao().insertAllChannels(daoListChannel)
    }

    override suspend fun deleteChannel(channelModel: ChannelModel) {
        mChannelDatabase.Dao().deleteChannel(channelModel)
    }

    override fun updateChannel(channelModel: ChannelModel, selected: Boolean) {
        mChannelDatabase.Dao().updateChannel(LocalChannel(channelModel.channel).apply {
            channelNum = channelModel.channel
            channelDescription = channelModel.description
            isChannelSelected = selected
        })
    }

    override fun updateAllChannels(channelModelList: List<ChannelModel>, selected: Boolean) {
        val daoListChannel: List<LocalChannel>
        daoListChannel = channelModelList.map { chModel -> LocalChannel(chModel.channel).apply {
            channelNum = chModel.channel
            channelDescription = chModel.description
            isChannelSelected = selected
        } }
        mChannelDatabase.Dao().updateAllChannels(daoListChannel)
    }

    //Command methods

    //First request to IPC Server to get all commands by channel num
    override suspend fun getCommands(channel: Int): List<CommandModel> {
        return mServerClient.getCommands(channel)
    }

    override suspend fun getAllCommands(channelNum: Int): List<CommandModel> {
        return mChannelDatabase.Dao().getChannelWithCommands(channelNum).commandsList.map { list ->
            CommandModel(list.commandName, list.commandNote, getAllProps(list.channelAndCommandName)) }
    }

    override suspend fun getSelectedCommand(): CommandModel {
        val daoChannel = mChannelDatabase.Dao().getSelectedCommand(true)
        return CommandModel(daoChannel.commandName, daoChannel.commandNote, getAllProps(daoChannel.channelAndCommandName))
    }

    override suspend fun putCommand(channelNum: Int, channelCommandName: String, commandModel: CommandModel, selected: Boolean) {
        mChannelDatabase.Dao().insertCommand(LocalCommand(commandModel.name).apply {
            channelAndCommandName = channelCommandName
            commandName = commandModel.name
            commandNote = commandModel.note
            isCommandSelected = selected
            commandToRelatedChannel = channelNum
        })
    }

    override suspend fun putAllCommands(channelNum: Int, channelCommandName: String, listCommandModel: List<CommandModel>) {
        val daoListCommands: List<LocalCommand>
        daoListCommands = listCommandModel.map { commandModel -> LocalCommand(commandModel.name).apply {
            channelAndCommandName = channelCommandName
            commandName = commandModel.name
            commandNote = commandModel.note
            isCommandSelected = false
            commandToRelatedChannel = channelNum
        } }
        mChannelDatabase.Dao().insertAllCommands(daoListCommands)
    }

    override suspend fun updateCommand(channelNum: Int, channelCommandName: String, commandModel: CommandModel, selected: Boolean) {
        mChannelDatabase.Dao().updateCommand(LocalCommand(commandModel.name).apply {
            channelAndCommandName = channelCommandName
            commandName = commandModel.name
            commandNote = commandModel.note
            isCommandSelected = selected
            commandToRelatedChannel = channelNum
        })
    }

    //Prop methods

    override suspend fun getAllProps(channelCommandName: String): List<CommandPropModel> {
        return mChannelDatabase.Dao().getCommandWithProps(channelCommandName).propsList.map { list ->
            CommandPropModel(list.propName, list.propNote, list.propType) }
    }

    override suspend fun putProp(channelCommandName: String, propModel: CommandPropModel) {
        mChannelDatabase.Dao().insertProp(LocalProp(propModel.name).apply {
            channelCommandPropName = channelCommandName + propModel.name
            propName = propModel.name
            propNote = propModel.note
            propType = if (propModel.type.isNotEmpty()) propModel.type else "request"
            propToRelatedCommand = channelCommandName
        })
    }

    override suspend fun putAllProps(channelCommandName: String, listCommandPropModel: List<CommandPropModel>) {
        val daoListProps: List<LocalProp>
        daoListProps = listCommandPropModel.map { propModel -> LocalProp(propModel.name).apply {
            channelCommandPropName = channelCommandName + propModel.name
            propName = propModel.name
            propNote = propModel.note
            propType = if (propModel.type.isNotEmpty()) propModel.type else "request"
            propToRelatedCommand = channelCommandName
        } }
        mChannelDatabase.Dao().insertAllProps(daoListProps)
    }

    override suspend fun sendProp(jsonString: String) {
        mServerClient.sendProp(jsonString)
    }

    //Logs methods

    override fun getAllLogs(): List<LogModel> {
        return mServerClient.getAllLogs()
    }

    override fun putLog(logModel: LogModel) {
        mChannelDatabase.Dao().insertLog(Logs(logModel.message))
    }

    override fun deleteLog(logModel: LogModel) {
        mChannelDatabase.Dao().deleteLog(logModel)
    }
}