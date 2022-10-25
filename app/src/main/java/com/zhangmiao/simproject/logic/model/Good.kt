package com.zhangmiao.simproject.logic.model

import android.os.Parcel
import android.os.Parcelable

data class Good(
    /**
     * 商品id
     */
    var id: String,
    /**
     * 商品名称
     */
    var name: String,

    /**
     * 商品初始金额
     */
    var amount_initial: Int,

    /**
     * 商品最终金额
     */
    var amount_final: Int,

    /**
     * 折扣
     */
    var discount: Int,

    ):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(amount_initial)
        parcel.writeInt(amount_final)
        parcel.writeInt(discount)
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
