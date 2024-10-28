package com.example.idlegame

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUser(userId: String): UserEntity? {
        return userDao.getUser(userId)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }
}
