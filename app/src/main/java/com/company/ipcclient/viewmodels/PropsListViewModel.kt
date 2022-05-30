

package com.company.ipcclient.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.gson.GsonBuilder
import com.company.ipcclient.model.ChannelModel
import com.company.ipcclient.model.CommandModel
import com.company.ipcclient.model.submit.*
import com.company.ipcclient.repositories.interfaces.IChannelRepository
import com.company.ipcclient.repositories.interfaces.ICommandRepository
import com.company.ipcclient.repositories.interfaces.IPropRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropsListViewModel @ViewModelInject constructor(
        private val mRepository: IPropRepository,
        private val mChannelRepository: IChannelRepository,
        private val mCommandRepository: ICommandRepository
) : ViewModel() {

    private val mPropListLVD = MutableLiveData<List<PropsViewModel>>()
    private val mIsLoading = MutableLiveData<Boolean>(true)
    private lateinit var currentChannel: ChannelModel
    private lateinit var currentCommand: CommandModel

    val propList: LiveData<List<PropsViewModel>>
        get() = mPropListLVD

    val isLoading: LiveData<Boolean>
        get() = mIsLoading

    private val mOnPropSend = MediatorLiveData<Boolean>()

    init {
            viewModelScope.launch(Dispatchers.IO) {
            mIsLoading.postValue(true)
            currentChannel = mChannelRepository.getSelectedChannel()
            currentCommand = mCommandRepository.getSelectedCommand()
            val allPropList = currentCommand.props
            if (allPropList != null) {
                mPropListLVD.postValue(allPropList.map { prop ->
                    PropsViewModel(prop)
                })
            }
            mIsLoading.postValue(false)
            mCommandRepository.updateCommand(currentChannel.channel,
                    currentChannel.description + currentCommand.name,
                    currentCommand,
                    false)
        }
    }

    private suspend fun sendJsonPropIPC(jsonString: String) {
        try {
            mRepository.sendProp(jsonString)
            mOnPropSend.postValue(true)
        } catch (e: Exception) {
            mOnPropSend.postValue(false)
            Log.e(LOG_TAG, "Fail to send property to IPC Server: " + e.message)
        }
    }

    suspend fun onSubmit(propType: String, propValue: Any?) {
        val commandObj: Command
        val channelObj: Channel
        when(propType) {
            "boolean" -> {
                val propBoolean = propValue as Boolean
                commandObj = Command(currentCommand.name, PropsBooleanImpl(propBoolean))
                channelObj = Channel(currentChannel.channel, commandObj)
            }
            "bytes" -> {
                val splittedPropValue = propValue.toString().split(",")
                val propBytes = splittedPropValue.map { it.toInt() }
                commandObj = Command(currentCommand.name, PropsBytesImpl(propBytes))
                channelObj = Channel(currentChannel.channel, commandObj)
            }
            "int32" -> {
                val propInt = propValue.toString().toInt()
                commandObj = Command(currentCommand.name, PropsIntImpl(propInt))
                channelObj = Channel(currentChannel.channel, commandObj)
            }
            "" -> {
                commandObj = Command(currentCommand.name, PropsDefaultImpl(null))
                channelObj = Channel(currentChannel.channel, commandObj)
            }
            else -> {
                val propDefault = propValue.toString()
                commandObj = Command(currentCommand.name, PropsDefaultImpl(propDefault))
                channelObj = Channel(currentChannel.channel, commandObj)
            }
        }
        val gson = GsonBuilder().create()
        val jsonString = gson.toJson(channelObj)
        sendJsonPropIPC(jsonString)
    }

    companion object {
        private const val LOG_TAG = "IPCClient"
    }
}