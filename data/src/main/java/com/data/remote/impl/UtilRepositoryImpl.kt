package com.data.remote.impl

import com.data.local.utils.resolveError
import com.domain.repository.UtilRepository
import javax.inject.Inject

class UtilRepositoryImpl @Inject constructor(): UtilRepository {

    override fun getNetworkError(throwable: Throwable): String = throwable.resolveError()
}