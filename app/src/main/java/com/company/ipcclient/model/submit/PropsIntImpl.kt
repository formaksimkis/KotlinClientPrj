

package com.company.ipcclient.model.submit

import com.google.gson.annotations.SerializedName

data class PropsIntImpl (
        @SerializedName("value") val value: Int
): Props()