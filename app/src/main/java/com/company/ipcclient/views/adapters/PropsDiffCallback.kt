

package com.company.ipcclient.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.company.ipcclient.viewmodels.PropsViewModel

class PropsDiffCallback : DiffUtil.ItemCallback<PropsViewModel>() {
    override fun areItemsTheSame(oldItem: PropsViewModel, newItem: PropsViewModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PropsViewModel, newItem: PropsViewModel): Boolean {
        return oldItem.note == newItem.note
    }
}