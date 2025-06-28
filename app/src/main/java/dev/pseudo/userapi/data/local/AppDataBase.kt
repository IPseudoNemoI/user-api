package dev.pseudo.userapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.pseudo.userapi.data.local.dao.UserDao
import dev.pseudo.userapi.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}