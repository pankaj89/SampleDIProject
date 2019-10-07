package com.master.samplediproject.ws

import com.google.gson.JsonElement
import io.reactivex.Single
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface WebService {
    companion object {
        val LOCAL_URL = "http://192.168.75.181/mrscentral4389/"
        val STAGING_URL = "http://auth.dfinas.mrsholdings.com/central/"
        val LIVE_URL = "http://auth.finas.mrsholdings.com/central/"

        var BASE_URL = STAGING_URL
        val INCLUDE_HEADER = true
    }

    @FormUrlEncoded
    @POST("api")
    fun testApi(@FieldMap params: HashMap<String, String>)
            : Single<JsonElement>
}