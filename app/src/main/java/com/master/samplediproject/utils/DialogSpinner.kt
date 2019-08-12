package com.master.samplediproject.utils

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.master.basediproject.utils.dialog.DialogFragmentHelper
import com.master.samplediproject.R
import com.master.samplediproject.databinding.DialogSpinnerBinding
import com.master.samplediproject.databinding.ItemDialogSpinnerBinding
import com.simpleadapter.SimpleAdapter

class DialogSpinner<M>(val activity: Activity, val list: ArrayList<M>) {
    companion object {
        fun <M> with(activity: Activity, list: ArrayList<M>): DialogSpinner<M> {
            return DialogSpinner(activity, list)
        }
    }

    private var getValueCallback: (value: M) -> String = { value -> value.toString() }
    fun setMap(callback: (value: M) -> String): DialogSpinner<M> {
        getValueCallback = callback
        return this
    }

    private var enableSearch: Boolean = true
    fun setEnableSearch(value: Boolean): DialogSpinner<M> {
        enableSearch = value
        return this
    }

    private var onValueSelected: (value: M) -> Unit = {}
    fun setOnValueSelectedCallback(callback: (value: M) -> Unit): DialogSpinner<M> {
        onValueSelected = callback
        return this
    }

    var dialogFragment: DialogFragment? = null
    fun build(): DialogSpinner<M> {

        dialogFragment = DialogFragmentHelper.with<DialogSpinnerBinding>(
            R.layout.dialog_spinner,
            isCancellable = true,
            fullScreenType = DialogFragmentHelper.FULL_WIDTH,
            isCancellableOnTouchOutSide = true

        ) { it, dialog ->
            it.recyclerView.layoutManager = LinearLayoutManager(activity)
            if (enableSearch) {
                it.etKeyword.visibility = View.VISIBLE
            } else {
                it.etKeyword.visibility = View.GONE
            }
            val adapter =
                SimpleAdapter.with<M, ItemDialogSpinnerBinding>(R.layout.item_dialog_spinner) { adapaterPosition, model, binding ->
                    //                    val color =
//                        ALTERNATE_ROW_COLORS.get(adapaterPosition % ALTERNATE_ROW_COLORS.size)
//                    binding.llRoot.setBackgroundColor(color)

                    binding.tvText.setText(getValueCallback(model))
                }
            adapter.setClickableViews({ view: View, model: M, adapterPosition: Int ->
                onValueSelected(model)
                dialog.dismiss()
            }, R.id.llRoot)

            it.etKeyword.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    adapter.performFilter(s.toString()) { text: String, model: M ->
                        getValueCallback(model).toLowerCase().contains(text.toLowerCase())
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            adapter.addAll(list)
            it.recyclerView.adapter = adapter
        }
        return this
    }

    fun show(fragmentManager: FragmentManager) {
        dialogFragment?.show(fragmentManager, "dialog")
    }
}