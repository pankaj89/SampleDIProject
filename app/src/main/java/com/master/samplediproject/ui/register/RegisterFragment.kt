package com.master.samplediproject.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.master.basediproject.extensions.loadImage
import com.master.basediproject.utils.MediaPicker
import com.master.samplediproject.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_register, container, false)
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    lateinit var mediapicker: MediaPicker
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (ivProfile.parent as View).visibility = View.VISIBLE
        mediapicker = MediaPicker(
            fragment = this,
            requiresCrop = true,
            mediaType = MediaPicker.MEDIA_TYPE_IMAGE,
            action = MediaPicker.ACTION_TYPE_GALLERY or MediaPicker.ACTION_TYPE_CAMERA
        )

        ivProfile.setOnClickListener {
            mediapicker.start { path, mediaType ->
                ivProfile.loadImage(path)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mediapicker.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mediapicker.onActivityResult(requestCode, resultCode, data)
    }
}