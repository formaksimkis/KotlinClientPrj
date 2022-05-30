

package com.company.ipcclient.model.submit

import com.google.gson.annotations.SerializedName

data class Channel(
        @SerializedName("channel") val channel : Int,
        @SerializedName("command") val command : Command)