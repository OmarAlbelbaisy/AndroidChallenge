package com.oza.challenge.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.google.android.material.textfield.TextInputLayout

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
        Glide.with(imageView).load(url).listener(listener).into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["error", "errorMessage"], requireAll = true)
    fun showError(textInputLayout: TextInputLayout, isError: Boolean, errorMessage: String) {
        textInputLayout.error = if (isError) {
            errorMessage
        } else {
            null
        }
    }

}
