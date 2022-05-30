

package com.company.ipcclient.model.local_dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalChannel (
    @ColumnInfo(index = true)
    @PrimaryKey(autoGenerate = false)
    var channelNum: Int = -1,
    var isChannelSelected: Boolean = false,
    var channelDescription: String = "") {
}