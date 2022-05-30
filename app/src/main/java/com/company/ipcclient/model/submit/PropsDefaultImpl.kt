

package com.company.ipcclient.model.submit

import com.google.gson.annotations.SerializedName

data class PropsDefaultImpl(
        @SerializedName("default") val default: String? = ""
): Props()