package com.data.local.room.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.data.local.model.LocalPost
import com.data.local.room.AppDatabase
import com.data.utils.dummyPost
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PostDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var postDao: PostDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        postDao = database.postDao()
    }

    @Test
    fun insertPost() = runBlocking {
        // Given
        postDao.insert(dummyPost)

        // When
        val savedPost = postDao.getById(12)

        // Then
        assertThat(savedPost).isNotNull()
        assertThat(savedPost.title).isEqualTo("Testing")
    }

    @Test
    fun updatePost() = runBlocking {
        // Given
        postDao.insert(dummyPost)
        val updatedPost = LocalPost(12, "testing body", "Testing update", 1, "", "")

        // When
        val oldPost = postDao.getById(dummyPost.id)
        assertThat(oldPost.title).isEqualTo("Testing")

        postDao.update(updatedPost)
        val newPost = postDao.getById(dummyPost.id)
        assertThat(newPost.title).isNotEqualTo("Testing")
        assertThat(newPost.title).isEqualTo("Testing update")
    }

    @After
    fun tearDown() {
        database.close()
    }
}