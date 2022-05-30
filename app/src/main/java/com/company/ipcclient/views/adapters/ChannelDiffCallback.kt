

package com.company.ipcclient.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.company.ipcclient.viewmodels.ChannelViewModel

class ChannelDiffCallback : DiffUtil.ItemCallback<ChannelViewModel>() {
    override fun areItemsTheSame(oldItem: ChannelViewModel, newItem: ChannelViewModel): Boolean {
        return oldItem.channel == newItem.channel
    }

    override fun areContentsTheSame(oldItem: ChannelViewModel, newItem: ChannelViewModel): Boolean {
        return oldItem.description == newItem.description
    }
}