package com.master.basediproject.utils.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.master.basediproject.R

class DialogFragmentHelper<B : ViewDataBinding> : androidx.fragment.app.DialogFragment() {

    var viewCreatedCallback: ((binding: B, dialogFragment: DialogFragment) -> Unit)? = null
    var onStartCallback: ((dialogFragment: DialogFragment) -> Unit)? = null

    companion object {
        fun <B : ViewDataBinding> with(
            layout: Int,
            isFullScreen: Boolean = false,
            isAnimationRequire: Boolean = true,
            isCancellable: Boolean = true,
            isCancellableOnTouchOutSide: Boolean = true,
            viewCreatedCallback: (binding: B, dialogFragment: DialogFragment) -> Unit
        ): androidx.fragment.app.DialogFragment {
            val dialog = DialogFragmentHelper<B>().apply {
                arguments = Bundle().apply {
                    putInt("RES", layout)
                    putBoolean("IS_CANCELLABLE", isCancellable)
                    putBoolean("IS_CANCELLABLE_ON_TOUCH_OUTSIDE", isCancellableOnTouchOutSide)
                    putBoolean("IS_FULL_SCREEN", isFullScreen)
                    putBoolean("IS_ANIMATION_REQUIRE", isAnimationRequire)
                }
            }
            dialog.isCancelable = isCancellable
            dialog.viewCreatedCallback = viewCreatedCallback
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

        if (arguments?.getBoolean("IS_FULL_SCREEN") == true) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        if (arguments?.getBoolean("IS_ANIMATION_REQUIRE") == true) {
            dialog.window?.setWindowAnimations(R.style.SlideAnim)
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        onStartCallback?.invoke(this)
        if (view?.parent != null)
            (view?.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }
}