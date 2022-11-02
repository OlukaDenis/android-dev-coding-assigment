package com.data.local.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.data.local.di.DatabaseModule
import com.data.local.localMappers.LocalCommentMapper
import com.data.local.localMappers.LocalPostMapper
import com.data.local.localMappers.LocalUserMapper
import com.data.local.room.AppDatabase
import com.data.local.room.dao.CommentDao
import com.data.local.room.dao.PostDao
import com.data.local.room.dao.UserDao
import com.data.utils.dummyUser
import com.domain.repository.LocalRepository
import com.google.common.truth.Truth.assertThat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
class LocalRepositoryImplTest {

    private lateinit var localRepository: LocalRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject lateinit var localUserMapper: LocalUserMapper
    @Inject lateinit var localCommentMapper: LocalCommentMapper
    @Inject lateinit var localPostMapper: LocalPostMapper

    @Inject lateinit var postDao: PostDao

    @Inject lateinit var userDao: UserDao

    @Inject lateinit var commentDao: CommentDao

    @Module
    @InstallIn(SingletonComponent::class)
    object TestDatabaseModule {

        @Provides
        fun provideRoomDatabase(): AppDatabase {
            return Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                AppDatabase::class.java
            ).allowMainThreadQueries()
                .build()
        }

        @Provides
        fun providePostDao(database: AppDatabase) = database.postDao()

        @Provides
        fun provideUserDao(database: AppDatabase) = database.userDao()

        @Provides
        fun provideCommentDao(database: AppDatabase) = database.commentDao()
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        localRepository = LocalRepositoryImpl(
            userDao,
            postDao,
            commentDao,
            localPostMapper,
            localUserMapper,
            localCommentMapper
        )
    }

    @Test
    fun insertUser_findUser() = runBlocking {
        // Given

        // When
        userDao.insert(dummyUser)
        val savedUser = userDao.getById(10).first()

        // Then
        assertThat(savedUser).isNotNull()
        assertThat(savedUser?.email).isEqualTo("test@gmail.com")

    }

}