package com.master.samplediproject.ui.register

import android.content.Intent
import android.os.Bundle
import com.master.basediproject.dihelpers.DIViewModelFactory
import com.master.basediproject.extensions.loadImage
import com.master.basediproject.extensions.startActivity
import com.master.basediproject.extensions.toMultibodyFilePart
import com.master.basediproject.extensions.viewModelProvider
import com.master.basediproject.utils.MediaPicker
import com.master.basediproject.utils.imageviewer.ImagePreviewActivity
import com.master.samplediproject.R
import com.master.samplediproject.utils.DialogSpinner
import com.master.samplediproject.utils.Pref
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import timber.log.Timber
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

        val list = arrayListOf<String>("Pankaj", "Prashant", "Punit", "Priya")
        Timber.i("List ${list.sortBy { it }}")

        mediapicker = MediaPicker(
            activity = this@RegisterActivity,
            requiresCrop = true,
            mediaType = MediaPicker.MEDIA_TYPE_IMAGE or MediaPicker.MEDIA_TYPE_OTHER,
            action = MediaPicker.ACTION_TYPE_GALLERY or MediaPicker.ACTION_TYPE_CAMERA or MediaPicker.ACTION_TYPE_FILE
        )

        pref.userDetail = User("1", "Pankaj")

        ivProfile.setOnClickListener {
            mediapicker.start { path, mediaType ->
                this.path = path
                ivProfile.loadImage(path)
            }
        }

        btnRegister.setOnClickListener {
            Timber.i(pref.userDetail?.name?:"")
            /*if (path?.isNotBlank() == true)
                ImagePreviewActivity.getIntent(this, path).startActivity(this)

            DialogSpinner.with(this, arrayListOf<String>("India", "Pakistan", "Australia"))
                .setEnableSearch(true).setMap { value: String -> "" + value }
                .setOnValueSelectedCallback { model ->
                }.build().show(supportFragmentManager)*/

        }
//        supportFragmentManager.beginTransaction().replace(R.id.container, RegisterFragment())
//            .commit()
//        registerViewModel.callWS()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
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