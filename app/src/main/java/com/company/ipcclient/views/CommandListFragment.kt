

package com.company.ipcclient.views

import android.os.Bundle
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
import androidx.navigation.fragment.navArgs
import com.company.ipcclient.R
import com.company.ipcclient.databinding.CommandItemBinding
import com.company.ipcclient.databinding.CommandListLayoutBinding
import com.company.ipcclient.model.CommandPropModel
import com.company.ipcclient.viewmodels.CommandListViewModel
import com.company.ipcclient.viewmodels.CommandViewModel
import com.company.ipcclient.views.adapters.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CommandListFragment : Fragment() {
    val mViewModel: CommandListViewModel by viewModels()
    private val mAdapter = SimpleListAdapter(
            HolderCreator(::createHolder),
            HolderBinder(::bindHolder),
            CommandDiffCallback()
    )

    private val mArguments: ChannelListFragmentArgs by navArgs()
    private lateinit var mBinding: CommandListLayoutBinding

    private fun bindHolder(viewModel: CommandViewModel, holder: Holder<CommandItemBinding>){
        if (viewModel.props == null) {
            viewModel.props = Arrays.asList(CommandPropModel())
        }
        holder.binding.viewModel = viewModel
        holder.binding.mainViewModel = mViewModel
        holder.binding.channelModel = mArguments.channelViewModel
        holder.binding.commandCard.setOnClickListener {
            val extras =
                FragmentNavigator.Extras.Builder()
                    .addSharedElement(holder.binding.channelId, "channelId")
                    .build()
            findNavController().navigate(CommandListFragmentDirections.actionCommandListToPropList(viewModel), extras)
            mViewModel.onClickCommand(mArguments.channelViewModel.channel,
                mArguments.channelViewModel.description, viewModel)
        }
    }

    private fun createHolder(parent: ViewGroup): Holder<CommandItemBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CommandItemBinding.inflate(inflater, parent, false)
        binding.commandCard.minimumWidth = (parent.width * 0.4).toInt()
        return Holder(binding)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.command_list_layout, container, false)
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.commandList.adapter = mAdapter
        return mBinding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.commandList.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
    }
}