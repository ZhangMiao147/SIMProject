package com.zhangmiao.simproject.logic.model

import android.os.Parcel
import android.os.Parcelable

data class Good(

    val id: String,

    val name: String,

    val regular_price: Int,

    val amount_primary: Int,

    val description: String,

    val detail: String

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(regular_price)
        parcel.writeInt(amount_primary)
        parcel.writeString(description)
        parcel.writeString(detail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Good> {
        val REGULAR_PRICE_NO = -1
        override fun createFromParcel(parcel: Parcel): Good {
            return Good(parcel)
        }

        override fun newArray(size: Int): Array<Good?> {
            return arrayOfNulls(size)
        }
    }



}
