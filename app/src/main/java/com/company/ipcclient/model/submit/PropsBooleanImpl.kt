

package com.company.ipcclient.model.submit

import com.google.gson.annotations.SerializedName

data class PropsBooleanImpl(
        @SerializedName("enabled") val enabled: Boolean
): Props()