

package com.company.ipcclient.views.adapters

import androidx.databinding.ViewDataBinding

class HolderBinder<TData, THolder : ViewDataBinding>(binder: (data: TData, holder: Holder<THolder>) -> Unit) {
    private val mBinder = binder
    fun bind(data: TData, holder: Holder<THolder>) = mBinder(data, holder)
}