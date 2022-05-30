

package com.company.ipcclient.viewmodels

import com.company.ipcclient.model.CommandModel
import com.company.ipcclient.model.CommandPropModel
import java.io.Serializable

data class CommandViewModel(
    val commandModel: CommandModel,
    val name: String = commandModel.name,
    val note: String = commandModel.note,
    var props: List<CommandPropModel>? = commandModel.props) : Serializable