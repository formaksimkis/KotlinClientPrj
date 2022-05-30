

package com.company.ipcclient.views.adapters

import androidx.databinding.ViewDataBinding
import android.view.ViewGroup

class HolderCreator<T : ViewDataBinding>(creator: (parent: ViewGroup) -> Holder<T>) {
    private val holderCreator = creator
    fun create(parent: ViewGroup) = holderCreator(parent)
}