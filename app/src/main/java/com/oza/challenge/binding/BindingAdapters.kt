package com.oza.challenge.binding

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
        Glide.with(imageView).load(url).listener(listener).into(imageView)
    }

}
