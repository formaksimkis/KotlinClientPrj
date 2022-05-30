package com.company.ipcclient

import android.text.Editable
import android.text.TextWatcher
import com.company.ipcclient.databinding.PropsItemBinding
import com.company.ipcclient.viewmodels.LogsListViewModel
import com.company.ipcclient.views.adapters.Holder


open class KeyboardListener() {

    fun textWatcherLog(mViewModel: LogsListViewModel): TextWatcher {
        val textWatcherLogs = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mViewModel.filter(s.toString())
            }
        }
        return textWatcherLogs
    }

    fun textWatcherProps(holder: Holder<PropsItemBinding>): TextWatcher {
        val textWatcherProps = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                holder.binding.propSubmit.isEnabled =
                        holder.binding.propValue.text.toString().trim().isNotEmpty()
            }
        }
        return textWatcherProps
    }
}