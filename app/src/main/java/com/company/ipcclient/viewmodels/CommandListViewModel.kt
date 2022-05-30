

package com.company.ipcclient.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.company.ipcclient.model.CommandModel
import com.company.ipcclient.model.CommandPropModel
import com.company.ipcclient.repositories.interfaces.IChannelRepository
import com.company.ipcclient.repositories.interfaces.ICommandRepository
import com.company.ipcclient.repositories.interfaces.IPropRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CommandListViewModel @ViewModelInject constructor(
    private val mRepository: ICommandRepository,
    private val mChannelRepository: IChannelRepository,
    private val mCommandRepository: ICommandRepository,
    private val mPropsRepository: IPropRepository
) : ViewModel() {

    private val mCommandListLVD = MutableLiveData<List<CommandViewModel>>()
    private val mIsLoading = MutableLiveData<Boolean>(true)

    val commandList: LiveData<List<CommandViewModel>>
        get() = mCommandListLVD

    val isLoading: LiveData<Boolean>
        get() = mIsLoading

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mIsLoading.postValue( true)
            var currentChannel = mChannelRepository.getSelectedChannel()
            var allCommandsList: List<CommandModel>
            if (mCommandRepository.getAllCommands(currentChannel.channel).isEmpty()) {
                allCommandsList = mRepository.getCommands(currentChannel.channel)
                for(command in allCommandsList) {
                    val channelCommandName = currentChannel.description + command.name
                    mRepository.putCommand(currentChannel.channel, channelCommandName, command, false)
                    //TODO remove "&& command.name.isNotEmpty()" due to it's temporary decision
                    // while Debug channel is not implementd and has no any commands
                    if (command.props == null && command.name.isNotEmpty()) {
                        command.props = Arrays.asList(CommandPropModel())
                    }
                    command.props?.let { mPropsRepository.putAllProps(channelCommandName, it) }
                }
            } else {
                allCommandsList = mCommandRepository.getAllCommands(currentChannel.channel)
            }
            mCommandListLVD.postValue(allCommandsList.map { command ->
                CommandViewModel(command)
            })
            mIsLoading.postValue(false)
        }
    }

    fun onClickCommand(channelNum: Int, channelName: String, command: CommandViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.updateCommand(channelNum, channelName + command.name,
                    command.commandModel,true)
        }
    }
}