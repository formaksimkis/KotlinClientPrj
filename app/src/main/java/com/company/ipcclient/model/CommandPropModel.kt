

package com.company.ipcclient.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//dto
data class CommandPropModel (
    @SerializedName("name") var name: String = "",
    @SerializedName("note") var note: String = "",
    @SerializedName("type") var type: String = "",
    @Expose var booleanCheckProp: Boolean = false,
    @Expose var propVal: String = "")