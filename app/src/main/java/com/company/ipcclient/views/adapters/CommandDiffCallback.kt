

package com.company.ipcclient.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.company.ipcclient.viewmodels.CommandViewModel

class CommandDiffCallback : DiffUtil.ItemCallback<CommandViewModel>() {
    override fun areItemsTheSame(oldItem: CommandViewModel, newItem: CommandViewModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CommandViewModel, newItem: CommandViewModel): Boolean {
        return oldItem.note == newItem.note
    }
}