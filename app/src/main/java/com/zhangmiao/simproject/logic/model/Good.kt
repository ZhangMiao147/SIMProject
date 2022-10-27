package com.zhangmiao.simproject.logic.model

import android.os.Parcel
import android.os.Parcelable

data class Good(

    var id: String,

    var name: String,

    var regular_price: Int,

    var amount_primary: Int,

    val description: String,

    var detail: String

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
        override fun createFromParcel(parcel: Parcel): Good {
            return Good(parcel)
        }

        override fun newArray(size: Int): Array<Good?> {
            return arrayOfNulls(size)
        }
    }

}
