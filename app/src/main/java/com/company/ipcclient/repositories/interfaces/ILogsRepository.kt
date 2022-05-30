

package com.company.ipcclient.repositories.interfaces

import com.company.ipcclient.model.LogModel

interface ILogsRepository {
    fun getAllLogs(): List<LogModel>
    fun putLog(logModel : LogModel)
    fun deleteLog(logModel: LogModel)
}