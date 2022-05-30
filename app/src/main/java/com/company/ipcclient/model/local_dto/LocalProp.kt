

package com.company.ipcclient.model.local_dto

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(foreignKeys = [ForeignKey(
        entity = LocalCommand::class,
        parentColumns = arrayOf("channelAndCommandName"),
        childColumns = arrayOf("propToRelatedCommand"),
        onDelete = CASCADE)])

data class LocalProp(
        @ColumnInfo(index = true)
        @PrimaryKey(autoGenerate = false)
        var channelCommandPropName: String,
        var propName: String = "",
        var propNote: String = "",
        var propType: String = "",
        @ColumnInfo(index = true)
        var propToRelatedCommand: String ="")

data class CommandWithProps(
        @Embedded val command: LocalCommand,
        @Relation(
                parentColumn = "channelAndCommandName",
                entityColumn = "propToRelatedCommand"
        )
        val propsList: List<LocalProp>)