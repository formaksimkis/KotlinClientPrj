

package com.company.ipcclient.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.company.ipcclient.model.ChannelModel
import com.company.ipcclient.repositories.interfaces.IChannelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChannelListViewModel @ViewModelInject constructor(
    private val mRepository: IChannelRepository
) : ViewModel() {

    private val mChannelList = MutableLiveData<List<ChannelViewModel>>()
    private val mIsLoading = MutableLiveData<Boolean>(true)

    val channelList: LiveData<List<ChannelViewModel>>
        get() = mChannelList

    val isLoading: LiveData<Boolean>
        get() = mIsLoading

    init {
        viewModelScope.launch(Dispatchers.IO) {
            var allChannelsList: List<ChannelModel>
            mIsLoading.postValue(true)
            if (mRepository.getAllChannels().isEmpty()) {
                allChannelsList = mRepository.getChannels()
                mRepository.putAllChannels(allChannelsList)
            } else {
                allChannelsList = mRepository.getAllChannels()
                mRepository.updateAllChannels(allChannelsList, false)
            }
            mChannelList.postValue(allChannelsList.map { channel ->
                ChannelViewModel(channel)
            })
            mIsLoading.postValue(false)
        }
    }

    fun onClickChannel(channel: ChannelViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.updateChannel(channel.channelModel, true)
        }
    }

    fun onRejectSelectedChannels() {
        viewModelScope.launch(Dispatchers.IO) {
            val channels = mRepository.getAllChannels()
            mRepository.updateAllChannels(channels, false)
        }
    }
}