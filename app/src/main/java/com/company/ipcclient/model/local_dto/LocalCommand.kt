

package com.company.ipcclient.model.local_dto

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(foreignKeys = [ForeignKey(
        entity = LocalChannel::class,
        parentColumns = arrayOf("channelNum"),
        childColumns = arrayOf("commandToRelatedChannel"),
        onDelete = CASCADE)])

data class LocalCommand(
        @ColumnInfo(index = true)
        @PrimaryKey(autoGenerate = false)
        var channelAndCommandName: String,
        var commandName: String = "",
        var commandNote: String = "",
        var isCommandSelected: Boolean = false,
        @ColumnInfo(index = true)
        var commandToRelatedChannel: Int = -1)

data class ChannelWithCommands(
        @Embedded val channel: LocalChannel,
        @Relation(
                parentColumn = "channelNum",
                entityColumn = "commandToRelatedChannel"
        )
        val commandsList: List<LocalCommand>)