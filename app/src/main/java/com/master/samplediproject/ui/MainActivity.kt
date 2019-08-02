package com.master.samplediproject.ui

import android.os.Bundle
import com.master.basediproject.utils.validation.ValidationHelper
import com.master.samplediproject.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var validationHelper: ValidationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            validate()
        }
    }

    private fun validate() {
        validationHelper.clearAll()
        validationHelper.validateEmail(tilEmail, blankEmailMsg = "Enter email address", invalidEmailMsg = "Enter valid email", isRequired = true)
        validationHelper.validateMobile(tilMobile, blankEmailMsg = "Enter mobile number", invalidEmailMsg = "Enter valid mobile number", isRequired = true)

        validationHelper.validateAll()
    }
}
