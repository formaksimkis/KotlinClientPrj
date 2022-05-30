

package com.company.ipcclient.repositories.interfaces

import com.company.ipcclient.model.CommandModel

interface ICommandRepository {
    suspend fun getCommands(channel: Int): List<CommandModel>
    suspend fun getAllCommands(channelNum: Int): List<CommandModel>
    suspend fun getSelectedCommand(): CommandModel
    suspend fun putCommand(channelNum: Int, channelCommandName: String, commandModel: CommandModel, selected: Boolean)
    suspend fun putAllCommands(channelNum: Int, channelCommandName: String, listCommandModel: List<CommandModel>)
    suspend fun updateCommand(channelNum: Int, channelCommandName: String, commandModel: CommandModel, selected: Boolean)
}