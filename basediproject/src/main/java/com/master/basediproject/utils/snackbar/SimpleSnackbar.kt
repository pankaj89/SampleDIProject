package com.master.basediproject.utils.snackbar

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.master.basediproject.R
import com.master.basediproject.extensions.findSuitableParent

class SimpleSnackbar(
        parent: ViewGroup,
        content: CustomSnackbarView
) : BaseTransientBottomBar<SimpleSnackbar>(parent, content, content) {

    init {
        getView().setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(activity: Activity): SimpleSnackbar {

            var view = activity.findViewById<View>(android.R.id.content)
            if (view == null) {
                view = activity.window.decorView.findViewById(android.R.id.content)
            }
            // First we find a suitable parent for our custom view
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                    "No suitable parent found from the given view. Please provide a valid view."
            )

            // We inflate our custom view
            val customView = LayoutInflater.from(view.context).inflate(
                    R.layout.view_snackbar,
                    parent,
                    false
            ) as CustomSnackbarView

            // We create and return our Snackbar
            return SimpleSnackbar(
                    parent,
                    customView
            )
        }

    }

}