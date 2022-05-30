

package com.company.ipcclient.model.local_dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Logs(
        var logMessage: String = ""
){
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0
}