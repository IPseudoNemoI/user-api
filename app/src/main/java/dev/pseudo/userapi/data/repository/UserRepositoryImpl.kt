package dev.pseudo.userapi.data.repository

import dev.pseudo.userapi.data.model.User
import dev.pseudo.userapi.data.remote.RandomUserApi
import dev.pseudo.userapi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: RandomUserApi
) : UserRepository {

    override fun getUsers(forceRefresh: Boolean): Flow<List<User>> = flow {
        try {
            val response = api.getUsers().results
            emit(response)
        } catch (e: Exception) {
            throw e
        }
    }
}