package com.data.local.localMappers

interface BaseLocalMapper<LOCAL, DOMAIN> {

    fun toDomain(entity: LOCAL): DOMAIN

    fun toLocal(entity: DOMAIN): LOCAL
}