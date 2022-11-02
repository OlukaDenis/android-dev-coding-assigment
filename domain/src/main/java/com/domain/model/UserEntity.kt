package com.domain.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val id: Long,
    val name: String,
    val email: String,
    val username: String,
    val phone: String,
    val website: String
) : Parcelable {

    fun isEmpty() = email.isEmpty() && username.isEmpty() && phone.isEmpty()
}