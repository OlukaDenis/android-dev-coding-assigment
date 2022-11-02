package com.data.local.room.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.data.local.model.LocalUser
import com.data.local.room.AppDatabase
import com.data.utils.dummyUser
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
    }

    @Test
    fun insertUser() = runBlocking {
        // Given
        userDao.insert(dummyUser)

        // When
        val savedUser = userDao.getById(10).first()

        // Then
        assertThat(savedUser).isNotNull()
        assertThat(savedUser?.email).isEqualTo("test@gmail.com")
    }

    @Test
    fun updateUser() = runBlocking {
        // Given
        userDao.insert(dummyUser)
        val updateUser = LocalUser(10, "Test update", "update@gmail.com", "90898172", "1234", "")

        // When
        val oldUser = userDao.getById(10).first()
        assertThat(oldUser?.email).isEqualTo("test@gmail.com")

        userDao.update(updateUser)
        val newUser = userDao.getById(10).first()
        assertThat(newUser?.email).isNotEqualTo("test@gmail.com")
        assertThat(newUser?.email).isEqualTo("update@gmail.com")
    }

    @After
    fun tearDown() {
        database.close()
    }
}