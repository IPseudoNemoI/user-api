package dev.pseudo.userapi.domain.repository

import dev.pseudo.userapi.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(forceRefresh: Boolean = false): Flow<List<User>>
}