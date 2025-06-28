package dev.pseudo.userapi.data.repository

import dev.pseudo.userapi.data.local.dao.UserDao
import dev.pseudo.userapi.data.mapper.toEntity
import dev.pseudo.userapi.data.mapper.toUser
import dev.pseudo.userapi.data.model.User
import dev.pseudo.userapi.data.remote.RandomUserApi
import dev.pseudo.userapi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: RandomUserApi,
    private val dao: UserDao
) : UserRepository {

    override fun getUsers(forceRefresh: Boolean): Flow<List<User>> = flow {
        val cached = dao.getAll().map { it.toUser() }

        emit(cached)

        if (forceRefresh || cached.isEmpty()) {
            try {
                val fresh = api.getUsers().results
                dao.clearAll()
                dao.insertAll(fresh.map { it.toEntity() })
                emit(fresh)
            } catch (e: Exception) {
                throw e
            }
        }
    }
}