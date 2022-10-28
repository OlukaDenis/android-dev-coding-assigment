package com.domain.dispacher

import kotlinx.coroutines.CoroutineDispatcher

interface AppDispatcher {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}