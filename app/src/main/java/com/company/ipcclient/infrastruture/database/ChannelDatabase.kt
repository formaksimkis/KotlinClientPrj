

package com.company.ipcclient.infrastruture.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.company.ipcclient.model.local_dto.LocalChannel
import com.company.ipcclient.model.local_dto.LocalCommand
import com.company.ipcclient.model.local_dto.LocalProp
import com.company.ipcclient.model.local_dto.Logs

@Database(entities = [LocalChannel::class, LocalCommand::class, LocalProp::class, Logs::class], version = 1, exportSchema = false)
abstract class ChannelDatabase: RoomDatabase() {
    abstract fun Dao(): IChannelDao
}