

package com.company.ipcclient.infrastruture.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.company.ipcclient.model.ChannelModel
import com.company.ipcclient.model.LogModel
import com.company.ipcclient.model.local_dto.*

@Dao
abstract class IChannelDao {

    //For "channels" table

    @Query("SELECT * FROM LocalChannel")
    abstract fun getChannelsOnLiveData(): LiveData<List<LocalChannel>>

    @Query("SELECT * FROM LocalChannel")
    abstract fun getAllChannels(): List<LocalChannel>

    @Query("SELECT * FROM LocalChannel WHERE isChannelSelected = :selected")
    abstract fun getSelectedChannel(selected: Boolean): LocalChannel

    @Delete
    abstract fun deleteChannel(channel: LocalChannel)

    fun deleteChannel(channel: ChannelModel) {
        getAllChannels().firstOrNull{
            it.channelNum == channel.channel && it.channelDescription == channel.description
        }?.let { deleteChannel(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertChannel(channel: LocalChannel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllChannels(channelList: List<LocalChannel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateChannel(channel: LocalChannel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateAllChannels(channelList: List<LocalChannel>)

    //For "commands" table

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCommand(command: LocalCommand)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllCommands(commandList: List<LocalCommand>)

    @Transaction
    @Query("SELECT * FROM LocalChannel WHERE channelNum = :channelNum")
    abstract fun getChannelWithCommands(channelNum: Int) : ChannelWithCommands

    @Query("SELECT * FROM LocalCommand WHERE isCommandSelected = :selected")
    abstract fun getSelectedCommand(selected: Boolean): LocalCommand

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateCommand(command: LocalCommand)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateAllCommands(commandList: List<LocalCommand>)

    //For "props" table

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProp(prop: LocalProp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllProps(propList: List<LocalProp>)

    @Transaction
    @Query("SELECT * FROM LocalCommand WHERE channelAndCommandName = :channelAndCommandName")
    abstract fun getCommandWithProps(channelAndCommandName: String) : CommandWithProps

    //For "Logs" table

    @Query("SELECT * FROM Logs")
    abstract fun getAllLogs(): List<Logs>

    @Delete
    abstract fun deleteLog(log: Logs)

    fun deleteLog(log: LogModel) {
        getAllLogs().firstOrNull{
            it.logMessage == log.message
        }?.let { deleteLog(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertLog(log: Logs)
}