package com.data.qualifiers

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthQualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiQualifier