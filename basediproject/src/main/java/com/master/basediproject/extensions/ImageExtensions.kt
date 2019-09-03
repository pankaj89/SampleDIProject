package com.master.basediproject.extensions

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String?, placeHolder: Int = -1) {
    val requestManager = Glide.with(this.context.applicationContext).load(url)
    if (placeHolder != -1) {
        requestManager.apply(RequestOptions().placeholder(placeHolder).error(placeHolder)).into(this)
    } else {
        requestManager.into(this)
    }
}

fun ImageView.loadImageWithCircle(url: String?, placeHolder: Int = -1) {
    val requestManager = Glide.with(this.context.applicationContext).load(url).apply(RequestOptions.bitmapTransform(CircleCrop()))
    if (placeHolder != -1) {
        requestManager.apply(RequestOptions().placeholder(placeHolder).error(placeHolder)).into(this)
    } else {
        requestManager.into(this)
    }
}

fun ImageView.loadImageWithRoundedCorner(url: String?, placeHolder: Int = -1, radius:Int = 0) {
    val requestManager = Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
    if (placeHolder != -1) {
        requestManager.apply(RequestOptions().placeholder(placeHolder).error(placeHolder)).into(this)
    } else {
        requestManager.into(this)
    }
}

fun ImageView.loadImageWithThumb(url: String, placeHolder: Int = -1) {

    // setup Glide request without the into() method
    val thumbnailRequest = Glide
        .with(context.applicationContext)
        .load(url)

    val requestManager = Glide.with(this).load(url).thumbnail(thumbnailRequest)
    if (placeHolder != -1) {
        requestManager.apply(RequestOptions().placeholder(placeHolder).error(placeHolder)).into(this)
    } else {
        requestManager.into(this)
    }
}


fun ImageView.loadImage(placeHolder: Int) {
    Glide.with(this.context.applicationContext).load(placeHolder).into(this)
}
fun ImageView.loadImageWithCircle(placeHolder: Int) {
    Glide.with(this.context.applicationContext).load(placeHolder).apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(this)
}

fun ImageView.loadImageNoAnimate(placeHolder: Int) {
    Glide.with(this.context.applicationContext).load(placeHolder).dontAnimate().into(this)
}

@BindingAdapter("bind:imageUrl")
fun setImageUrl(imageView: AppCompatImageView, url: String?) {

    url?.let {
        Glide.with(imageView.context.applicationContext).load(it).apply(RequestOptions.noAnimation()).into(imageView)
    }

}
