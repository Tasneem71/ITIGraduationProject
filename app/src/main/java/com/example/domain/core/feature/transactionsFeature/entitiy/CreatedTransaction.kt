package com.example.domain.core.feature.transactionsFeature.entitiy

import com.example.graduationapp.data.Addresses


data class CreatedTransaction (

    val createdTransaction : TransactionBody
)


data class TransactionBody(

    val currency: String,
    val amount: Double,
    val kind: String,
    val parent_id: Long

)
