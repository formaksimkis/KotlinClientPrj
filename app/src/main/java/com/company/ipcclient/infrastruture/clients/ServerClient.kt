

package com.company.ipcclient.infrastruture.clients

import com.google.gson.Gson
import com.company.ipcclient.infrastruture.clients.interfaces.IServerClient
import com.company.ipcclient.model.ChannelModel
import com.company.ipcclient.model.CommandModel
import com.company.ipcclient.model.LogModel
import com.company.ipcclient.model.RootModel
import java.util.*

/**
 * A ServerClient class.
 *
 * This class is used for sending and getting JSON formatted strings to IPC Server.
 * It uses Root Model object to parse incoming JSON for model needed.
 * It uses a Connector object as a holder of connection with IPC Server.
 *
 * @author Maksim Kiselev
 */
class ServerClient: IServerClient {
    companion object {
        const val SERVER = "127.0.0.1"
        const val PORT = 30000
        private const val GET_ALL_CHANNELS = "{\"command\": {\"name\": \"lschannels\"}}"
    }

    private val mConnector = Connector(SERVER, PORT)
    private lateinit var mChannelsList: List<ChannelModel>
    private lateinit var mCommandsList: List<CommandModel>
    private var mListLogs = ArrayList<LogModel>()

    private fun sendToIPC(stringJsonToSend: String): Boolean {
        if (!mConnector.isConnected) {
            if(mConnector.connect()) {
                mConnector.writeLine(stringJsonToSend)
            }
        }
        else {
            mConnector.writeLine(stringJsonToSend)
        }
        return true;
    }

    /**
     * This is a suspend function.
     *
     * This function sends request to IPC Server to receive all channels.
     * It's used by only suspend function in coroutine scope: ChannelListViewModel.viewModelScope.launch
     *
     * @return List<ChannelModel>
     * @author Maksim Kiselev
     */
    override suspend fun getChannels(): List<ChannelModel> {
        mChannelsList = emptyList()
        sendToIPC(GET_ALL_CHANNELS)
        while (true) {
            if (mChannelsList.isNotEmpty()) return mChannelsList as List<ChannelModel>
        }
    }

    /**
     * This is a suspend function.
     *
     * This function sends request to IPC Server to receive all commands.
     * It's used by only suspend function in coroutine scope: CommandListViewModel.viewModelScope.launch
     *
     * @param channel the port number.
     * @return List<CommandModel>
     * @author Maksim Kiselev
     */
    override suspend fun getCommands(channel: Int): List<CommandModel> {
        mCommandsList = emptyList()
        val channel: String = channel.toString()
        val getCommands = "{\"command\": {\"name\": \"lscommands\",\"props\": {\"channel\": $channel}}}"
        sendToIPC(getCommands)
        while (true) {
            if (mCommandsList.isNotEmpty()) return mCommandsList
        }
    }

    private fun readResponse() {
        val readingThread = Thread {
            while (true) {
                if (!mConnector.isConnected) {
                    if (!mConnector.connect()) {
                        Thread.sleep(1000)
                        continue
                    }
                }
                while (true) {
                    val line = mConnector.readLine()
                    val gson = Gson()
                    val root: RootModel = gson.fromJson(line, RootModel::class.java)
                    if (line.isNotEmpty()) {
                        mListLogs.add(LogModel(line + "\n"))
                        if (null != root.response) {
                            if (null != root.response.channels) {
                                mChannelsList = root.response.channels
                            }
                            if (null != root.response.commands) {
                                mCommandsList = root.response.commands
                            }
                        }
                    }
                }
            }
        }
        readingThread.isDaemon = true
        readingThread.start()
    }

    override suspend fun sendProp(jsonString: String) {
        sendToIPC(jsonString)
    }

    override fun startReading() {
        readResponse()
    }

    override fun getAllLogs(): List<LogModel> {
        return mListLogs
    }
}