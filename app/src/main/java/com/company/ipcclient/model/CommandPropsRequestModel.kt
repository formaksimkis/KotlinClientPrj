

package com.company.ipcclient.model

import com.google.gson.annotations.SerializedName

//dto
data class CommandPropsRequestModel (
    @SerializedName("channel") val channel: Int)