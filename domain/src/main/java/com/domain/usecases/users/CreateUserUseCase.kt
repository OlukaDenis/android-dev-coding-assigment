package com.domain.usecases.users

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.model.sealed.Resource
import com.domain.repository.LocalRepository
import com.domain.repository.PreferenceRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class CreateUserUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val remote: RemoteRepository,
    private val local: LocalRepository,
    private val preferences: PreferenceRepository,
    private val utilRepository: UtilRepository
) : BaseFlowUseCase<CreateUserUseCase.Param, Resource<UserEntity>>(dispatcher) {

    data class Param(
        val name: String,
        val email: String,
        val phone: String,
        val password: String
    )

    override fun run(param: Param?): Flow<Resource<UserEntity>> = flow {
        emit(Resource.Loading)

        try {
            param?.let {
                val request = HashMap<String, Any>().apply {
                    this["name"] = it.name
                    this["username"] = it.name.replace("\\s".toRegex(), "-").lowercase(Locale.ROOT)
                    this["email"] = it.email
                    this["phone"] = it.phone
                }

                val user = remote.createUser(request)

                local.saveUser(user)

                preferences.saveUser(user)
                preferences.savePassword(it.password)

                emit(Resource.Success(user))
            }
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }
    }

}