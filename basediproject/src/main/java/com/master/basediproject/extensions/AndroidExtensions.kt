package com.master.basediproject.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.job.JobScheduler
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.StateSet
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.jakewharton.rxbinding2.widget.RxTextView
import com.master.basediproject.utils.ProgressRequestBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream
import java.io.Serializable
import java.net.URLEncoder
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


fun runAsync(func: () -> Unit): Thread {
    val thread = Thread(Runnable { func() })
    thread.start()
    return thread
}

fun <T> Fragment.getFromArgument(key: String, defaultValue: T): T {
    if (arguments != null && arguments?.containsKey(key) == true) {
        return arguments!!.get(key) as T
    }
    return defaultValue
}

fun <T> Activity.getFromIntent(key: String, defaultValue: T): T {
    if (intent.extras != null && intent.extras?.containsKey(key) == true) {
        return intent.extras!!.get(key) as T
    }
    return defaultValue
}

fun WebView?.loadUrlWithPostParam(url: String, postParam: HashMap<String, String>) {
    var postData: String = ""
    for ((key, value) in postParam) {
        postData += "$key=${URLEncoder.encode(value, "UTF-8")}&"
    }
    this?.postUrl(url, postData.toByteArray())
}

/*fun runOnUI(func: () -> Unit): Handler {
    val mainHandler = Handler(myAppContext.mainLooper)
    val myRunnable = Runnable {
        func()
    }
    mainHandler.post(myRunnable)
    return mainHandler
}*/

fun View.showKeyBoard() {
    /*this.postDelayed({
        val inputManager = this.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(this, InputMethodManager.HIDE_NOT_ALWAYS)
    }, 100)*/

    val inputManager = this.context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, 0)
}

fun AppCompatActivity.hideKeyBoard() {
    /*this.postDelayed({
        val inputManager = this.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.windowToken, 0)
    }, 100)*/

    val inputManager = this
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus
    if (view != null)
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.hideKeyBoard() {
    /*this.postDelayed({
        val inputManager = this.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.windowToken, 0)
    }, 100)*/

    val inputManager = this.context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Context.getJobScheduler(): JobScheduler? {
    var jobScheduler: JobScheduler? = null
    if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        jobScheduler = getSystemService(JobScheduler::class.java)
    } else {
        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    }
    return jobScheduler
}

fun Bundle.putObject(key: String, data: Serializable) {
    putSerializable(key, data)
}

fun Intent.putObject(key: String, data: Serializable) {
    putExtra(key, data)
}

fun <T> Bundle.getObject(key: String): T {
    return getSerializable(key) as T
}

fun <T> Intent.getObject(key: String): T {
    return getSerializableExtra(key) as T
}

fun Intent.startActivity(activity: AppCompatActivity) {
    try {
        activity.startActivity(this)
    } catch (e: Exception) {
    }
}

fun Intent.startActivity(fragment: Fragment) {
    try {
        fragment.startActivity(this)
    } catch (e: Exception) {
    }
}

fun Intent.startActivityForResult(activity: AppCompatActivity, requestCode: Int) {
    try {
        activity.startActivityForResult(this, requestCode)
    } catch (e: Exception) {
    }
}

fun Intent.startActivityForResult(fragment: Fragment, requestCode: Int) {
    try {
        fragment.startActivityForResult(this, requestCode)
    } catch (e: Exception) {
    }
}

fun EditText.delayTextChangeListener(delay: Long, func: (char: CharSequence) -> Unit) {

    RxTextView.textChanges(this)
//            .filter { charSequence -> charSequence.length > 1 }
        .debounce(delay, TimeUnit.MILLISECONDS)
        .skip(1)
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : io.reactivex.Observer<CharSequence> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(value: CharSequence) {
                func(value)
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }
        }
        )
}

fun JsonObject.forEachKeyValue(func: (key: String, value: JsonElement) -> Unit) {
    keySet().forEach { key ->
        func(key, this.get(key))
    }
}

fun Long.secondsToFormatedString(): String {
    if (this <= 0) {
        return "00:00"
    }
    val totalSeconds = this
    val seconds = totalSeconds % 60
    val minutes = totalSeconds / 60 % 60
    val hours = totalSeconds / 3600//  Add this % 24 for removing days also
    val stringBuilder = StringBuilder()
    val mFormatter = Formatter(stringBuilder, Locale.getDefault())
    return if (hours > 0) {
        mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
    } else {
        mFormatter.format("%02d:%02d", minutes, seconds).toString()
    }
}

fun AssetManager.read(assetFilePath: String, func: (content: String) -> Unit) {
    val inputStream: InputStream = open(assetFilePath)
    val inputString = inputStream.bufferedReader().use { it.readText() }
    func(inputString)
}

fun Activity.rateApp(defaultAllowed: Boolean = false): Boolean {
    val uri = Uri.parse("market://details?id=" + packageName)
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    // To count with Play market backstack, After pressing back button,
    // to taken back to our application, we need to add following flags to intent.
    goToMarket.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        startActivity(goToMarket)
        return true
    } catch (e: ActivityNotFoundException) {
        if (defaultAllowed) {
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)
                    )
                )
                return true
            } catch (e: Exception) {
                return false
            }
        }
    }
    return true
}

fun Activity.openUrl(
    url: String? = null,
    appName: Array<String>? = null,
    defaultAllowed: Boolean = false
): Boolean {
    try {
        val shareIntent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url))
        if (appName != null) {
            val pm = packageManager
            val activityList = pm?.queryIntentActivities(shareIntent, 0)
            val filteredPackage =
                activityList?.filter { appName.contains(it.activityInfo.packageName) == true }
                    ?.map { it.activityInfo.packageName }
            if (filteredPackage?.isNotEmpty() == true) {
                shareIntent.`package` = filteredPackage.get(0)
                startActivity(shareIntent)
                return true
            } else if (defaultAllowed) {
                startActivity(shareIntent)
                return true
            } else {
                return false
            }
        } else {
            startActivity(shareIntent)
            return true
        }
    } catch (e: Exception) {
        return false
    }
}

fun Activity.share(
    subject: String? = "",
    text: String,
    imageUrl: Uri? = null,
    appName: Array<String>? = null,
    defaultAllowed: Boolean = false
): Boolean {
    try {
        val shareIntent = Intent(android.content.Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text)
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject)
        imageUrl?.apply {
            shareIntent.putExtra(Intent.EXTRA_STREAM, this)
            shareIntent.type = "image/*"
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        if (appName != null) {
            val pm = packageManager
            val activityList = pm?.queryIntentActivities(shareIntent, 0)
            val filteredPackage =
                activityList?.filter { appName.contains(it.activityInfo.packageName) == true }
                    ?.map { it.activityInfo.packageName }
            if (filteredPackage?.isNotEmpty() == true) {
                shareIntent.`package` = filteredPackage.get(0)
                startActivity(shareIntent)
                return true
            } else if (defaultAllowed) {
                startActivity(shareIntent)
                return true
            } else {
                return false
            }
        } else {
            startActivity(shareIntent)
            return true
        }
    } catch (e: Exception) {
        return false
    }
}

fun Fragment.share(text: String, appName: Array<String>? = null): Boolean {
    return activity?.share(text = text, appName = appName) == true
}

fun String.toStringPart(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}

//fun String.toMultibodyFilePart(key: String, mimeType: String = "image/*"): MultipartBody.Part {
//    val file = File(this)
//    val filebody = RequestBody.create(MediaType.parse(mimeType), file)
//    return MultipartBody.Part.createFormData(key, file.name, filebody)
//}

fun String.toMultiBodyFilePart(key: String, mimeType: String = ""): MultipartBody.Part {
    val file = File(this)
    val actualMimeType = mimeType.stringIfBlank(this.getMimeType())
    val fileBody = file.asRequestBody(actualMimeType.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(key, file.name, fileBody)
}

fun String?.getMimeType(defaultMimeType: String = "image/*"): String {
    var type: String = defaultMimeType
    try {
        val extension = MimeTypeMap.getFileExtensionFromUrl(this?.toLowerCase()).toLowerCase()
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: defaultMimeType
        }
    } catch (e: Exception) {

    }
    return type
}

@SuppressLint("CheckResult")
fun String.toProgressFilePart(callback: (percentage: Float) -> Unit): ProgressRequestBody {
    val videoPart = ProgressRequestBody(File(this))
    videoPart.getProgressSubject()
        .subscribeOn(Schedulers.io())
        .subscribe(callback)
    return videoPart
}

fun String.showToast(context: Context?) {
    if (context != null)
        Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

/*fun String.showSnackBar(context: Activity?) {
    showSnackBar(context, SNAKBAR_TYPE_ERROR)
}

fun String.showValidationSnakeBar(context: Activity?) {
    showSnackBar(context, SNAKBAR_TYPE_WARNING)
}

fun String.showErrorSnakeBar(context: Activity?) {
    showSnackBar(context, SNAKBAR_TYPE_ERROR)
}

fun String.showSuccessSnakeBar(context: Activity?) {
    showSnackBar(context, SNAKBAR_TYPE_SUCCESS)
}

fun String.showSnackBar(context: Activity?, type: Int) {
    if (context != null) {
        var color = R.color.snackbar_green
        when (type) {
            SNAKBAR_TYPE_ERROR -> color = R.color.snackbar_error
            SNAKBAR_TYPE_WARNING -> color = R.color.snackbar_warning
            SNAKBAR_TYPE_SUCCESS -> color = R.color.snackbar_green
            SNAKBAR_TYPE_MESSAGE -> color = R.color.snackbar_green
        }
        val flashbar = Flashbar.Builder(context)
            .gravity(Flashbar.Gravity.TOP)
            .messageAppearance(1)
            *//* .title("\n\n" + context.getString(R.string.app_name))*//*
            *//*.title("\n")*//*
            .message(this)
            .titleColor(ContextCompat.getColor(context, R.color.white))
            .messageColor(ContextCompat.getColor(context, R.color.white))
            .backgroundColorRes(color)
            .enableSwipeToDismiss()
            .duration(2000)
            .build()
        if (!(flashbar.isShowing() || flashbar.isShown()))
            flashbar.show()
    }
}*/

fun EditText.onLeftDrawableClickListener(threshHold: Int = 0, func: () -> Unit) {
    this.setOnTouchListener(View.OnTouchListener { v, event ->
        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3

        if (event.action == MotionEvent.ACTION_DOWN) {
            val bound = this.compoundDrawables[DRAWABLE_LEFT]
            if (event.rawX <= this.left + (bound?.bounds?.width() ?: 0) + threshHold) {
                func()
                return@OnTouchListener true
            }
        }
        false
    })
}

fun EditText.onRightDrawableClickListener(threshHold: Int = 0, func: () -> Unit) {
    this.setOnTouchListener(View.OnTouchListener { v, event ->
        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3

        if (event.action == MotionEvent.ACTION_DOWN) {
            val bound = this.compoundDrawables[DRAWABLE_RIGHT]
            if (event.rawX > this.right - (bound?.bounds?.width() ?: 0) - threshHold) {
                func()
                return@OnTouchListener true
            }
        }
        false
    })
}


@Suppress("DEPRECATION")
fun String.getHtmlFormattedText(): Spanned {
    val result: Spanned =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(this)
        }
    return result
}


@BindingAdapter(value = arrayOf("selectedRes", "pressedRes", "normalRes"), requireAll = false)
fun setBackgroundSelector(
    view: View,
    selectedRes: Drawable?,
    pressedRes: Drawable?,
    normalRes: Drawable?
) {

    if (!(selectedRes == null && pressedRes == null && normalRes == null)) {
        val stateListDrawable = StateListDrawable()
        if (selectedRes != null) {
            stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), selectedRes)
        }

        if (pressedRes != null) {
            stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), pressedRes)
        }
        stateListDrawable.addState(StateSet.WILD_CARD, normalRes)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = stateListDrawable
        } else {
            view.setBackgroundDrawable(stateListDrawable)
        }
    }
}

@BindingAdapter(
    value = arrayOf("selectedImageRes", "pressedImageRes", "normalImageRes"),
    requireAll = false
)
fun setImageSelector(
    view: ImageView,
    selectedImageRes: Drawable?,
    pressedImageRes: Drawable?,
    normalImageRes: Drawable?
) {

    if (!(selectedImageRes == null && pressedImageRes == null && normalImageRes == null)) {
        val stateListDrawable = StateListDrawable()
        if (selectedImageRes != null) {
            stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), selectedImageRes)
        }

        if (pressedImageRes != null) {
            stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), pressedImageRes)
        }
        stateListDrawable.addState(StateSet.WILD_CARD, normalImageRes)

        view.setImageDrawable(stateListDrawable)
    }
}

@BindingAdapter(
    value = arrayOf("selectedColorRes", "pressedColorRes", "normalColorRes"),
    requireAll = false
)
fun setTextSelector(
    view: TextView,
    selectedColorRes: Int?,
    pressedColorRes: Int?,
    normalColorRes: Int?
) {

    if (!(selectedColorRes == null && pressedColorRes == null && normalColorRes == null)) {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_selected), // pressed
            intArrayOf(android.R.attr.state_pressed), // focused
            intArrayOf(android.R.attr.state_focused), // focused
            intArrayOf()
        )
        val colors = intArrayOf(
            selectedColorRes ?: 0,
            pressedColorRes ?: 0,
            normalColorRes ?: 0,
            normalColorRes ?: 0
        )

        val list = ColorStateList(states, colors)
        view.setTextColor(list)
    }
}

fun <T> List<T>?.toArrayList(): ArrayList<T> {
    return if (this == null) {
        ArrayList()
    } else {
        ArrayList(this)
    }
}

fun String.toUnformatValue(): String {
    val s = this.replace(",", "")
    return s.replace("\u20A6", "").trim()
}

fun String.toformatedValue(): String {
    try {
        return DecimalFormat("###,###.##").format(this.toDouble())
    } catch (e: Exception) {
        return this
    }
}

fun String.doubleValueToformatedWithZero(): String {
    try {
        return DecimalFormat("###,##0.00").format(this.stringIfBlank("0").toDouble())
    } catch (e: Exception) {
        return this
    }
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.dpRestoPx(context: Context? = null): Float {
    val r: Resources

    if (context == null) {
        r = Resources.getSystem()
    } else {
        r = context.resources
    }
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        r.getDimension(this),
        context?.resources?.displayMetrics
    )
}

// to get file name from path
fun String.getFileName(): String {
    return this.substring(this.lastIndexOf("/") + 1, this.length)
}

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            // We've found a CoordinatorLayout, use it
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                // If we've hit the decor content view, then we didn't find a CoL in the
                // hierarchy, so use it.
                return view
            } else {
                // It's not the content view but we'll use it as our fallback
                fallback = view
            }
        }

        if (view != null) {
            // Else, we will loop and crawl up the view hierarchy and try to find a parent
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
    return fallback
}