package com.master.samplediproject.ui.register

import androidx.lifecycle.ViewModel
import com.master.samplediproject.ws.WebService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val webservice: WebService) : ViewModel() {
    fun callWS() {
        webservice.testApi(HashMap<String, String>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})
    }
}