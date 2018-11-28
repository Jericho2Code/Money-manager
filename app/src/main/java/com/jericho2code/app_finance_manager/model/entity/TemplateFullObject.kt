package com.jericho2code.app_finance_manager.model.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TemplateFullObject(
    @Embedded
    var template: Template? = null,
    @Relation(parentColumn = "transaction_id", entityColumn = "id")
    var transaction: List<Transaction> = emptyList(),
    @Relation(parentColumn = "category_id", entityColumn = "id")
    var category: List<Category> = emptyList()
) : Parcelable