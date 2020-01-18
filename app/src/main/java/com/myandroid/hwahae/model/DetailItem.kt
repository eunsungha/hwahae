package com.myandroid.hwahae.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class DetailItem(
    var id: Int,
    var full_size_image: String,
    var title: String,
    var description: String,
    var price: Int,
    var oily_score: Int,
    var dry_score: Int,
    var sensitive_score: Int

) : Parcelable

