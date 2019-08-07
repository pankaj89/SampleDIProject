package com.master.samplediproject.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.master.basediproject.dihelpers.DIViewModelFactory
import com.master.basediproject.extensions.loadImage
import com.master.basediproject.extensions.startActivity
import com.master.basediproject.extensions.viewModelProvider
import com.master.basediproject.utils.MediaPicker
import com.master.basediproject.utils.imageviewer.ImagePreviewActivity
import com.master.samplediproject.R
import com.master.samplediproject.utils.DialogSpinner
import com.master.samplediproject.utils.Pref
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RegisterActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: DIViewModelFactory
    val registerViewModel by lazy { viewModelProvider<RegisterViewModel>(viewModelFactory) }

    @Inject
    lateinit var pref: Pref

    lateinit var mediapicker: MediaPicker

    var path: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val list= arrayListOf<String>("Pankaj", "Prashant", "Punit", "Priya")
        Timber.i("List ${list.sortBy { it }}")

        mediapicker = MediaPicker(
            activity = this@RegisterActivity,
            requiresCrop = true,
            mediaType = MediaPicker.MEDIA_TYPE_VIDEO,
            action = MediaPicker.ACTION_TYPE_GALLERY or MediaPicker.ACTION_TYPE_CAMERA
        )

        ivProfile.setOnClickListener {
            mediapicker.start {
                path = it
                ivProfile.loadImage(it)
            }
        }

        btnRegister.setOnClickListener {
            if (path?.isNotBlank() == true)
                ImagePreviewActivity.getIntent(this, path).startActivity(this)

            DialogSpinner.with(this, arrayListOf<String>("India","Pakistan","Australia")).setEnableSearch(true).setMap { value: String -> "" + value}.setOnValueSelectedCallback { model ->
            }.build().show(supportFragmentManager)

        }

//        registerViewModel.callWS()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mediapicker.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mediapicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}