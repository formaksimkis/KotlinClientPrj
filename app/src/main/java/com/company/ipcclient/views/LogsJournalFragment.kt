

package com.company.ipcclient.views

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.company.ipcclient.KeyboardListener
import com.company.ipcclient.R
import com.company.ipcclient.databinding.LogsLayoutBinding
import com.company.ipcclient.databinding.LogsListLayoutBinding
import com.company.ipcclient.viewmodels.LogsListViewModel
import com.company.ipcclient.viewmodels.LogsViewModel
import com.company.ipcclient.views.adapters.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogsJournalFragment: Fragment() {
    private val mViewModel: LogsListViewModel by viewModels()
    private val mAdapter = SimpleListAdapter(
        HolderCreator(::createHolder),
        HolderBinder(::bindHolder),
        LogsDiffCallback()
    )

    private lateinit var mBinding: LogsListLayoutBinding

    private fun bindHolder(viewModel: LogsViewModel, holder: Holder<LogsLayoutBinding>){
        holder.binding.viewModel = viewModel
        holder.binding.mainViewModel = mViewModel
        holder.binding.logMessage.gravity = Gravity.LEFT
        holder.binding.logCard.setOnClickListener {
            hideKeyboard()
            Toast.makeText(context, "It's a log, do nothing!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createHolder(parent: ViewGroup): Holder<LogsLayoutBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LogsLayoutBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.logs_list_layout, container, false)
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.logsList.adapter = mAdapter
        return mBinding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add TextWatcher for search-log field
        val mTextWatcher = KeyboardListener()
        mBinding.searchLog.addTextChangedListener(mTextWatcher.textWatcherLog(mViewModel))
        // set listener for hard Enter-key press and hide keyboard
        mBinding.searchLog.setOnKeyListener(keyListenerHard)
        // set listener for soft Enter-key press and hide keyboard
        mBinding.searchLog.setOnEditorActionListener(keyListenerSoft)

        mViewModel.logsFilterList.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
        mViewModel.logsList.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }
    // Hide keyboard after leave fragment or input search value
    private fun hideKeyboard() {
        val view = activity?.currentFocus
        val imm = this.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private val keyListenerHard = View.OnKeyListener { p0, keyCode, event->
        if((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            hideKeyboard()
            true
        }else{
            false
        }
    }

    private val keyListenerSoft = TextView.OnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideKeyboard()
            true
        } else {
            false
        }}
}