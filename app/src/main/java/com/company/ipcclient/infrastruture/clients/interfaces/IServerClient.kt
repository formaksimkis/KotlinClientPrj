

package com.company.ipcclient.infrastruture.clients.interfaces

import com.company.ipcclient.model.ChannelModel
import com.company.ipcclient.model.CommandModel
import com.company.ipcclient.model.LogModel

interface IServerClient {
    suspend fun getChannels(): List<ChannelModel>
    suspend fun getCommands(channel : Int): List<CommandModel>
    suspend fun sendProp(jsonString: String)
    fun startReading()
    fun getAllLogs(): List<LogModel>
}