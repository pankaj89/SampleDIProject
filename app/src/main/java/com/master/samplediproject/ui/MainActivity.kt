package com.master.samplediproject.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.master.basediproject.utils.snackbar.SimpleSnackbar
import com.master.basediproject.utils.validation.ValidationHelper
import com.master.samplediproject.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private var view: View? = null
    @Inject
    lateinit var validationHelper: ValidationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initValidation()
        button.setOnClickListener {
            SimpleSnackbar.make(this).show()
        }
    }

    private fun initValidation() {
        validationHelper.addEmailValidation(
                tilEmail,
                blankEmailMsg = "Enter email address",
                invalidEmailMsg = "Enter valid email",
                isRequired = true
        )
        /*     validationHelper.addMobileValidation(
                 tilMobile,
                 blankEmailMsg = "Enter mobile number",
                 invalidEmailMsg = "Enter valid mobile number",
                 isRequired = true
             )*/
        validationHelper.addPasswordValidation(
                textInputLayout = tilPassword,
                blankPasswordMsg = "Enter password",
                weakPasswordMSg = "Enter strong password",
                isRequired = true,
                isStrong = true
        )
        validationHelper.addConfirmPasswordValidation(
                textInputLayout = tilPassword,
                confirmTextInputLayout = tilConfirmPassword,
                mismatchPasswordMsg = "Password and confirm password must be same"
        )
    }
}
