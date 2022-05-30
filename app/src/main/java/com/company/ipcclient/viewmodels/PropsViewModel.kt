

package com.company.ipcclient.viewmodels

import com.company.ipcclient.model.CommandPropModel
import java.io.Serializable

data class PropsViewModel(
        var propsModel: CommandPropModel,
        var name: String = propsModel.name,
        var note: String = propsModel.note,
        var type: String = propsModel.type,
        var booleanCheckProp: Boolean = propsModel.booleanCheckProp,
        var value: String = propsModel.propVal) : Serializable