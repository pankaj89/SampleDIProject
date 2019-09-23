package com.master.basediproject.utils.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.master.basediproject.R

class DialogFragmentHelper<B : ViewDataBinding> : androidx.fragment.app.DialogFragment() {

    var viewCreatedCallback: ((binding: B, dialogFragment: DialogFragment) -> Unit)? = null
    var onStartCallback: ((dialogFragment: DialogFragment) -> Unit)? = null

    companion object {
        const val FULL_WIDTH = 1
        const val FULL_HEIGHT = 2
        const val FULL_WIDTH_HEIGHT = 3
        fun <B : ViewDataBinding> with(
            layout: Int,
            fullScreenType: Int = FULL_WIDTH,
            isAnimationRequire: Boolean = true,
            isCancellable: Boolean = true,
            isCancellableOnTouchOutSide: Boolean = true,
            forceKeyboardOpen:Boolean = false,
            onStartCallback: ((bottomSheetDialogFragment: DialogFragment) -> Unit)? = null,
            viewCreatedCallback: (binding: B, dialogFragment: DialogFragment) -> Unit
        ): androidx.fragment.app.DialogFragment {
            val dialog = DialogFragmentHelper<B>().apply {
                arguments = Bundle().apply {
                    putInt("RES", layout)
                    putBoolean("IS_CANCELLABLE", isCancellable)
                    putBoolean("IS_CANCELLABLE_ON_TOUCH_OUTSIDE", isCancellableOnTouchOutSide)
                    putInt("IS_FULL_SCREEN", fullScreenType)
                    putBoolean("IS_ANIMATION_REQUIRE", isAnimationRequire)
                    putBoolean("IS_KEYBOARD_OPEN", forceKeyboardOpen)
                }
            }
            dialog.isCancelable = isCancellable
            dialog.viewCreatedCallback = viewCreatedCallback
            dialog.onStartCallback = onStartCallback
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(arguments?.getInt("RES")!!, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewCreatedCallback?.invoke(DataBindingUtil.bind<B>(view)!!, this)
        if (viewCreatedCallback == null) {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(
            arguments?.getBoolean("IS_CANCELLABLE_ON_TOUCH_OUTSIDE")
                ?: true
        )
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val isKeyboardOpen = arguments?.getBoolean("IS_KEYBOARD_OPEN") ?: true

        if(isKeyboardOpen){
            dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    override fun onStart() {
        super.onStart()

        if (dialog != null && arguments?.containsKey("IS_FULL_SCREEN") == true) {

            val type = arguments?.getInt("IS_FULL_SCREEN", FULL_WIDTH) ?: FULL_WIDTH
            when (type) {
                FULL_WIDTH -> {
                    dialog?.window?.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
                FULL_HEIGHT -> {
                    dialog?.window?.setLayout(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                FULL_WIDTH_HEIGHT -> {
                    dialog?.window?.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }
        }

        if (arguments?.getBoolean("IS_ANIMATION_REQUIRE") == true) {
            dialog?.window?.setWindowAnimations(R.style.SlideAnim)
        }

        if (view?.parent != null)
            (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)

        onStartCallback?.invoke(this)
    }
}