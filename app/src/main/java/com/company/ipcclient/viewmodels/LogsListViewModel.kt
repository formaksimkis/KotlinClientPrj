

package com.company.ipcclient.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.ipcclient.repositories.interfaces.ILogsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogsListViewModel @ViewModelInject constructor(
        private val mRepository: ILogsRepository
        ) : ViewModel() {
    private val mLogsList = MutableLiveData<List<LogsViewModel>>()
    private val mIsLoading = MutableLiveData<Boolean>(true)
    private val mFilterLogList = MutableLiveData<List<LogsViewModel>>()

    val mFilterString = MutableLiveData<String>("")

    val logsList: LiveData<List<LogsViewModel>>
        get() = mLogsList

    val isLoading: LiveData<Boolean>
        get() = mIsLoading

    val logsFilterList: LiveData<List<LogsViewModel>>
        get() = mFilterLogList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val logs = mRepository.getAllLogs()
            mIsLoading.postValue(true)
            mLogsList.postValue(logs.map { log ->
                LogsViewModel(log)
            })
            mIsLoading.postValue(false)
        }
    }

    fun filter(text: String) {
        if (text.isNotEmpty()){
            val list = mLogsList.value!!.filter { it -> it.message.contains(text, ignoreCase = true) }
            mFilterLogList.postValue(list)
        } else {
            mFilterLogList.postValue(mLogsList.value)
        }
    }
}