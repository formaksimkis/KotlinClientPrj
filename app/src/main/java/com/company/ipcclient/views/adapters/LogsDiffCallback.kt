

package com.company.ipcclient.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.company.ipcclient.viewmodels.LogsViewModel

class LogsDiffCallback : DiffUtil.ItemCallback<LogsViewModel>() {
    override fun areItemsTheSame(oldItem: LogsViewModel, newItem: LogsViewModel): Boolean {
        return oldItem.logModel == newItem.logModel
    }

    override fun areContentsTheSame(oldItem: LogsViewModel, newItem: LogsViewModel): Boolean {
        return oldItem.message == newItem.message
    }
}