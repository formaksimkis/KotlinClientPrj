

package com.company.ipcclient.viewmodels

import com.company.ipcclient.model.ChannelModel
import java.io.Serializable

data class ChannelViewModel (val channelModel: ChannelModel,
                             val channel: Int = channelModel.channel,
                             val description: String = channelModel.description): Serializable