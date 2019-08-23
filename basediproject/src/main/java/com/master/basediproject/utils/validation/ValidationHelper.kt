package com.master.basediproject.utils.validation

import android.view.animation.Animation
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


class ValidationHelper(anim: Animation) {

    private val validator by lazy { Validator(anim) }
    private val validationList: ArrayList<ValidationModel> = ArrayList()

    /**
     * Simple field required Validation
     */
    fun addRequiredValidation(textInputLayout: TextInputLayout, blankMsg: String) {
        validator.errorRemoveOnChange(textInputLayout)
        validationList.add(
            ValidationModel(
                mTextInputLayout = textInputLayout,
                mErrMsg = blankMsg
            )
        )
    }

    fun addRequiredValidation(editText: EditText, blankMsg: String) {
        validator.errorRemoveOnChange(editText)
        validationList.add(ValidationModel(mEditText = editText, mErrMsg = blankMsg))
    }


    /**
     * Email Address Validation
     */
    fun addEmailValidation(
        textInputLayout: TextInputLayout,
        blankEmailMsg: String,
        invalidEmailMsg: String,
        isRequired: Boolean = true
    ) {
        validator.errorRemoveOnChange(textInputLayout)
        if (isRequired) {
            validationList.add(
                ValidationModel(
                    mTextInputLayout = textInputLayout,
                    mErrMsg = blankEmailMsg
                )
            )
        }
        validationList.add(
            ValidationModel(
                mTextInputLayout = textInputLayout,
                mErrMsg = invalidEmailMsg,
                mPattern = EMAIL_ADDRESS
            )
        )
    }

    fun addEmailValidation(
        editText: EditText,
        blankEmailMsg: String,
        invalidEmailMsg: String,
        isRequired: Boolean = true
    ) {
        validator.errorRemoveOnChange(editText)
        if (isRequired) {
            validationList.add(ValidationModel(mEditText = editText, mErrMsg = blankEmailMsg))
        }
        validationList.add(
            ValidationModel(
                mEditText = editText,
                mErrMsg = invalidEmailMsg,
                mPattern = EMAIL_ADDRESS
            )
        )
    }


    /**
     * Mobile number Validation
     */
    fun addMobileValidation(
        textInputLayout: TextInputLayout,
        blankEmailMsg: String,
        invalidEmailMsg: String,
        isRequired: Boolean = true
    ) {
        validator.errorRemoveOnChange(textInputLayout)
        if (isRequired) {
            validationList.add(
                ValidationModel(
                    mTextInputLayout = textInputLayout,
                    mErrMsg = blankEmailMsg
                )
            )
        }
        validationList.add(
            ValidationModel(
                mTextInputLayout = textInputLayout,
                mErrMsg = invalidEmailMsg,
                mPattern = MOBILE
            )
        )
    }

    fun addMobileValidation(
        editText: EditText,
        blankMobileNumberMsg: String,
        invalidMobileNumberMsg: String,
        isRequired: Boolean = true,
        mobileNumberPattern: Pattern = MOBILE
    ) {
        validator.errorRemoveOnChange(editText)
        if (isRequired) {
            validationList.add(ValidationModel(mEditText = editText, mErrMsg = blankMobileNumberMsg))
        }
        validationList.add(
            ValidationModel(
                mEditText = editText,
                mErrMsg = invalidMobileNumberMsg,
                mPattern = mobileNumberPattern
            )
        )
    }

    /**
     * Strong and required password check
     */
    fun addPasswordValidation(
        textInputLayout: TextInputLayout, blankPasswordMsg: String, weakPasswordMSg: String = "",
        isRequired: Boolean = true, isStrong: Boolean = false,
        strongPattern: Pattern = STRONG_PASSWORD_CHECK
    ) {
        validator.errorRemoveOnChange(textInputLayout)
        if (isRequired)
            validationList.add(
                ValidationModel(
                    mTextInputLayout = textInputLayout,
                    mErrMsg = blankPasswordMsg
                )
            )
        if (isStrong)
            validationList.add(
                ValidationModel(
                    mTextInputLayout = textInputLayout,
                    mErrMsg = weakPasswordMSg,
                    mPattern = strongPattern
                )
            )
    }

    fun addPasswordValidation(
        editText: EditText, blankPasswordMsg: String, weakPasswordMSg: String,
        isRequired: Boolean = true, isStrong: Boolean = false,
        strongPattern: Pattern = STRONG_PASSWORD_CHECK
    ) {
        validator.errorRemoveOnChange(editText)
        if (isRequired)
            validationList.add(ValidationModel(mEditText = editText, mErrMsg = blankPasswordMsg))
        if (isStrong)
            validationList.add(
                ValidationModel(
                    mEditText = editText,
                    mErrMsg = weakPasswordMSg,
                    mPattern = strongPattern
                )
            )
    }

    /**
     * Confirm password Validation
     */
    fun addConfirmPasswordValidation(
        textInputLayout: TextInputLayout,
        confirmTextInputLayout: TextInputLayout,
        mismatchPasswordMsg: String = ""
    ) {
        validator.errorRemoveOnChange(textInputLayout)
        validationList.add(
            ValidationModel(
                mTextInputLayout = textInputLayout,
                mConfirmationTextInputLayout = confirmTextInputLayout,
                mErrMsg = mismatchPasswordMsg
            )
        )
    }

    fun addConfirmPasswordValidation(
        editText: EditText,
        confirmEditText: EditText,
        mismatchPasswordMsg: String
    ) {
        validator.errorRemoveOnChange(editText)
        validationList.add(
            ValidationModel(
                mEditText = editText,
                mConfirmationEditText = confirmEditText,
                mErrMsg = mismatchPasswordMsg
            )
        )
    }


    /**
     * Strong and required password check
     */
    fun addCustomePatternValidation(
        textInputLayout: TextInputLayout,
        patternMismatchMsg: String,
        pattern: Pattern
    ) {
        validator.errorRemoveOnChange(textInputLayout)
        validationList.add(
            ValidationModel(
                mTextInputLayout = textInputLayout,
                mErrMsg = patternMismatchMsg,
                mPattern = pattern
            )
        )
    }

    fun addCustomePatternValidation(
        editText: EditText,
        patternMismatchMsg: String,
        pattern: Pattern
    ) {
        validator.errorRemoveOnChange(editText)
        validationList.add(
            ValidationModel(
                mEditText = editText,
                mErrMsg = patternMismatchMsg,
                mPattern = pattern
            )
        )
    }


    fun clearAll() {
        validationList.clear()
    }

    fun validateAll(): Boolean {
        if (validationList.isNotEmpty()) {
            validationList.forEach {
                if (it.mEditText == null) {
                    if (it.mPattern == null) {
                        if (it.mConfirmationTextInputLayout != null) {
                            if (!validator.validateTextInputConfirmPassword(it)) return false
                        } else {
                            if (!validator.validateTextInputRequired(it)) return false
                        }
                    } else {
                        if (!validator.validateTextInputPattern(it)) return false
                    }

                } else {
                    if (it.mPattern == null) {
                        if (it.mConfirmationTextInputLayout != null) {
                            if (!validator.validateEditTextConfirmPassword(it)) return false
                        } else {
                            if (!validator.validateEditTextRequired(it)) return false
                        }
                    } else {
                        if (!validator.validateEditTextPattern(it)) return false
                    }

                }
            }
        } else {
            throw IllegalArgumentException("Please add atleast one validations in validation list to call validateAll method")
        }

        return true
    }


    companion object {
        val EMAIL_ADDRESS: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        val MOBILE: Pattern = Pattern.compile(
            "^\\d{7,12}$"
        )

        val STRONG_PASSWORD_CHECK: Pattern = Pattern.compile(
            "((?=.*\\d)(?=.*[A-Z])(?=.*[!@#\$&^%*]).{6,})"
        )
    }
}

