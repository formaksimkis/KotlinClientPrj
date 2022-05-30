

package com.company.ipcclient.repositories.interfaces

import com.company.ipcclient.model.ChannelModel

interface IChannelRepository {
    suspend fun getChannels(): List<ChannelModel>
    suspend fun getAllChannels(): List<ChannelModel>
    fun getSelectedChannel(): ChannelModel
    suspend fun putChannel(channelModel : ChannelModel, selected: Boolean)
    suspend fun putAllChannels(listChannelModel: List<ChannelModel>)
    suspend fun deleteChannel(channelModel: ChannelModel)
    fun updateChannel(channelModel: ChannelModel, selected: Boolean)
    fun updateAllChannels(channelModelList: List<ChannelModel>, selected: Boolean)
}