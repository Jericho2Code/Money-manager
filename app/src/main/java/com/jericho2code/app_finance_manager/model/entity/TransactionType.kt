package com.jericho2code.app_finance_manager.model.entity

import android.os.Parcel
import android.os.Parcelable

enum class TransactionType(
    val value: String
) : Parcelable {
    SPENDING_TRANSACTION("spending"), PROFIT_TRANSACTION("profit"), TRANSFER_TRANSACTION("transfer");

    companion object {
        fun fromString(value: String) = when(value) {
            "spending" -> SPENDING_TRANSACTION
            "profit" -> PROFIT_TRANSACTION
            "transfer" -> PROFIT_TRANSACTION
            else -> SPENDING_TRANSACTION
        }

        @JvmField
        val CREATOR = object : Parcelable.Creator<TransactionType> {
            override fun createFromParcel(source: Parcel?): TransactionType {
                return fromString(source?.readString() ?: "")
            }

            override fun newArray(size: Int): Array<TransactionType?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }
}