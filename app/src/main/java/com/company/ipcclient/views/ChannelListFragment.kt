

package com.company.ipcclient.views

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.company.ipcclient.R
import com.company.ipcclient.databinding.ChannelItemBinding
import com.company.ipcclient.databinding.ChannelListLayoutBinding
import com.company.ipcclient.viewmodels.ChannelListViewModel
import com.company.ipcclient.viewmodels.ChannelViewModel
import com.company.ipcclient.views.adapters.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChannelListFragment: Fragment() {
    private val mViewModel: ChannelListViewModel by viewModels()
    private val mAdapter = SimpleListAdapter(
            HolderCreator(::createHolder),
            HolderBinder(::bindHolder),
            ChannelDiffCallback()
    )

    private lateinit var mBinding: ChannelListLayoutBinding
    private var mAlreadyLoaded = false

    private fun bindHolder(viewModel: ChannelViewModel, holder: Holder<ChannelItemBinding>){
        holder.binding.viewModel = viewModel
        holder.binding.mainViewModel = mViewModel
        holder.binding.channelNum.gravity = Gravity.LEFT
        holder.binding.channelDescription.gravity = Gravity.LEFT
        holder.binding.channelCard.setOnClickListener {
            val extras =
                FragmentNavigator.Extras.Builder()
                    .addSharedElement(holder.binding.channelNum, "channelId")
                    .build()
            findNavController().navigate(ChannelListFragmentDirections.actionChannelListToCommandList(viewModel), extras)
            mViewModel.onClickChannel(viewModel)
        }
    }

    private fun createHolder(parent: ViewGroup): Holder<ChannelItemBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChannelItemBinding.inflate(inflater, parent, false)
        binding.channelCard.minimumWidth = (parent.width * 0.4).toInt()
        return Holder(binding)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.channel_list_layout, container, false)
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.channelList.adapter = mAdapter
        return mBinding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.channelList.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
        if (savedInstanceState == null && !mAlreadyLoaded) {
            mAlreadyLoaded = true;
            // Do this code only first time, not after rotation or reuse fragment from backstack
        } else {
            mViewModel.onRejectSelectedChannels()
        }
    }
}