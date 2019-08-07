package com.sbardyuk.sixtapp.ui.maps

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class PicassoMarker internal constructor(internal var marker: Marker) : Target {

    override fun hashCode(): Int {
        return marker.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is PicassoMarker) {
            val marker = other.marker
            return this.marker.equals(marker)
        } else {
            return false
        }
    }

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
    }

    override fun onBitmapFailed(errorDrawable: Drawable) {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(errorDrawable.getIntrinsicWidth(), errorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        errorDrawable.setBounds(0, 0, errorDrawable.getIntrinsicWidth(), errorDrawable.getIntrinsicHeight())
        errorDrawable.draw(canvas)

        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
}