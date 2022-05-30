

package com.company.ipcclient.repositories.interfaces

import com.company.ipcclient.model.CommandPropModel

interface IPropRepository {
    suspend fun getAllProps(channelCommandName: String): List<CommandPropModel>
    suspend fun putProp(channelCommandName: String, propModel : CommandPropModel)
    suspend fun putAllProps(channelCommandName: String, listCommandPropModel: List<CommandPropModel>)
    suspend fun sendProp(jsonString: String)
}