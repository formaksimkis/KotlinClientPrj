

package com.company.ipcclient.viewmodels

import com.company.ipcclient.model.LogModel
import java.io.Serializable

data class LogsViewModel (val logModel: LogModel,
                             val message: String = logModel.message): Serializable