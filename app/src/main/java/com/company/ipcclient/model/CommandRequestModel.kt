

package com.company.ipcclient.model

import com.google.gson.annotations.SerializedName

//dto
data class CommandRequestModel (
    @SerializedName("name") val name: String,
    @SerializedName("props") val props: CommandPropsRequestModel)