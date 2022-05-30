

package com.company.ipcclient.model

import com.google.gson.annotations.SerializedName

//dto
data class CommandModel (
    @SerializedName("name") var name: String = "",
    @SerializedName("note") var note: String = "",
    @SerializedName("props") var props: List<CommandPropModel>? = null)