

package com.company.ipcclient.views

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.company.ipcclient.KeyboardListener
import com.company.ipcclient.R
import com.company.ipcclient.databinding.PropsItemBinding
import com.company.ipcclient.databinding.PropsListLayoutBinding
import com.company.ipcclient.viewmodels.PropsListViewModel
import com.company.ipcclient.viewmodels.PropsViewModel
import com.company.ipcclient.views.adapters.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CommandItemPropsFragment : Fragment() {
    private val mViewModel: PropsListViewModel by viewModels()
    private val mAdapter = SimpleListAdapter(
        HolderCreator(::createHolder),
        HolderBinder(::bindHolder),
        PropsDiffCallback()
    )

    private val mArguments: CommandItemPropsFragmentArgs by navArgs()
    private lateinit var mBinding: PropsListLayoutBinding

    private fun createHolder(parent: ViewGroup): Holder<PropsItemBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PropsItemBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    private fun bindHolder(viewModel: PropsViewModel, holder: Holder<PropsItemBinding>){
        holder.binding.propViewModel = viewModel
        holder.binding.propListViewModel = mViewModel
        holder.binding.commandModel = mArguments.commandViewModel

        if (viewModel.propsModel.name.isNotEmpty()) {
            if (viewModel.propsModel.type == "boolean") {
                holder.binding.switchPropState.visibility = View.VISIBLE
                holder.binding.propSubmit.isEnabled = true
            } else {
                holder.binding.propValue.visibility = View.VISIBLE
            }
        }
        else {
            holder.binding.propSubmit.isEnabled = true
        }

        //add TextWatcher for parameter input fields
        val mTextWatcher = KeyboardListener()
        holder.binding.propValue.addTextChangedListener(mTextWatcher.textWatcherProps(holder))

        holder.binding.propSubmit.setOnClickListener {
            val value: Any = if (holder.binding.switchPropState.isVisible) {
                viewModel.booleanCheckProp
            } else {
                viewModel.value
            }
            GlobalScope.launch {
                mViewModel.onSubmit(viewModel.type, value)
            }
            hideKeyboard()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.props_list_layout, container, false)
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.propList.adapter = mAdapter
        return mBinding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.propList.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    // Hide keyboard after submit or leave fragment
    fun hideKeyboard() {
        val view = activity?.currentFocus
        val imm = this.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}