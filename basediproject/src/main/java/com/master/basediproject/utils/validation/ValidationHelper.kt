package com.master.basediproject.utils.validation

import android.view.animation.Animation
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


class ValidationHelper(anim: Animation) {

    private val validator = Validator(anim)
    private val validationList: ArrayList<ValidationModel> = ArrayList()

    fun validateEmail(textInputLayout: TextInputLayout, blankEmailMsg: String = "", invalidEmailMsg: String = "", isRequired: Boolean = true) {
        val edtText: EditText = textInputLayout.editText!!
        validator.errorRemoveOnChange(textInputLayout)
        if (isRequired) {
            validationList.add(ValidationModel(mTextInputLayout = textInputLayout, mErrMsg = blankEmailMsg))
        }
        validationList.add(ValidationModel(mTextInputLayout = textInputLayout, mErrMsg = invalidEmailMsg, mPattern = EMAIL_ADDRESS))
    }

    fun validateEmail(editText: EditText, blankEmailMsg: String = "", invalidEmailMsg: String = "", isRequired: Boolean = true) {
        validator.errorRemoveOnChange(editText)
        if (isRequired) {
            validationList.add(ValidationModel(mEditText = editText, mErrMsg = blankEmailMsg))
        }
        validationList.add(ValidationModel(mEditText = editText, mErrMsg = invalidEmailMsg, mPattern = EMAIL_ADDRESS))
    }

    fun validateMobile(textInputLayout: TextInputLayout, blankEmailMsg: String = "", invalidEmailMsg: String = "", isRequired: Boolean = true) {
        val edtText: EditText = textInputLayout.editText!!
        validator.errorRemoveOnChange(textInputLayout)

        if (isRequired) {
            validationList.add(ValidationModel(mTextInputLayout = textInputLayout, mErrMsg = blankEmailMsg))
        }
        validationList.add(ValidationModel(mTextInputLayout = textInputLayout, mErrMsg = invalidEmailMsg, mPattern = MOBILE))
    }

    fun validateMobile(editText: EditText, blankEmailMsg: String = "", invalidEmailMsg: String = "", isRequired: Boolean = true) {
        validator.errorRemoveOnChange(editText)

        if (isRequired) {
            validationList.add(ValidationModel(mEditText = editText, mErrMsg = blankEmailMsg))
        }
        validationList.add(ValidationModel(mEditText = editText, mErrMsg = invalidEmailMsg, mPattern = MOBILE))
    }


    fun clearAll() {
        validationList.clear()
    }
    fun validateAll() {
        validationList.forEach {
            if (it.mEditText == null) {
                if (it.mPattern == null) {
                    if (!validator.validateTextInputRequired(it)) return
                } else {
                    if (!validator.validateTextInputPattern(it)) return
                }
            } else {
                if (it.mPattern == null) {
                    if (!validator.validateEditTextRequired(it)) return
                } else {
                    if (!validator.validateTextInputPattern(it)) return
                }
            }
        }
    }


    companion object {
        val EMAIL_ADDRESS = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        )

        val MOBILE = Pattern.compile(
                "^[6-9]\\d{9}\$"
        )
    }
}

