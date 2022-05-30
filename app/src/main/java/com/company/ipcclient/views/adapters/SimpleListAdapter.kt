

package com.company.ipcclient.views.adapters

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class SimpleListAdapter<TBindignType : ViewDataBinding, TModelType> (holderCreator: HolderCreator<TBindignType>,
                                                                              holderBinder: HolderBinder<TModelType, TBindignType>,
                                                                              diffCallback:  DiffUtil.ItemCallback<TModelType>) : ListAdapter<TModelType, Holder<TBindignType>>(diffCallback) {
    private val mHolderCreator = holderCreator
    private val mBinder = holderBinder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<TBindignType> {
        return mHolderCreator.create(parent)
    }

    override fun onBindViewHolder(holder: Holder<TBindignType>, position: Int) {
        mBinder.bind(getItem(position), holder)
    }
}