package dev.pseudo.userapi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val city: String,
    val country: String,
    val imageUrl: String,
)