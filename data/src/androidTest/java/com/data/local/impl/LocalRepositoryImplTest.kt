package com.data.local.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.makao.data.local.room.dao.UtilityDao
import com.makao.data.local.di.DatabaseModule
import com.makao.data.local.localMappers.LocalUserMapper
import com.makao.data.local.room.AppDatabase
import com.makao.data.local.room.dao.*
import com.makao.domain.model.User
import com.makao.domain.repository.LocalRepository
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
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
class LocalRepositoryImplTest {

    private lateinit var localRepository: LocalRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var beneficiaryDao: BeneficiaryDao

    @Inject
    lateinit var contactDao: ContactDao

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var searchHistoryDao: SearchHistoryDao

    @Inject
    lateinit var addressDao: DeliveryAddressDao

    @Inject
    lateinit var utilityDao: UtilityDao

    @Inject
    lateinit var checkoutDao: CheckoutDao

    @Inject
    lateinit var categoryDao: CategoryDao

    @Inject
    lateinit var localAddressMapper: LocalAddressMapper

    @Inject
    lateinit var localUserMapper: LocalUserMapper

    @Inject
    lateinit var localCheckoutMapper: LocalCheckoutMapper

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
        fun provideBeneficiaryDao(database: AppDatabase) = database.beneficiaryDao()


        @Provides
        fun provideContactDao(database: AppDatabase) = database.contactDao()

        @Provides
        fun provideUserDao(database: AppDatabase) = database.userDao()

        @Provides
        @Singleton
        fun provideSearchHistoryDao(database: AppDatabase) = database.searchHistoryDao()

        @Provides
        fun providesCategoryDao(database: AppDatabase) = database.categoryDao()

        @Provides
        fun provideAddressDao(database: AppDatabase) = database.addressDao()

        @Provides
        fun provideUtilityDao(database: AppDatabase) = database.utilityDao()

        @Provides
        fun provideCheckout(database: AppDatabase) = database.checkoutDao()
    }


    @Before
    fun setUp() {
        hiltRule.inject()

        localRepository = LocalRepositoryImpl(
            beneficiaryDao,
            contactDao,
            userDao,
            searchHistoryDao,
            addressDao,
            utilityDao,
            checkoutDao,
            categoryDao,
            localAddressMapper,
            localUserMapper,
            localCheckoutMapper
        )
    }

    @Test
    fun test_getSavedUser_failure() = runBlocking {
        // Given

        // When
        val savedUser = localRepository.getUser()

        // Then
       assertThat(savedUser).isNotNull()

    }

    @Test
    fun test_getSavedUser_success() = runBlocking {
        // Given
        val user = User(
            id = "782",
            activationKey = "",
            dob = "",
            email = "email",
            genderId = "",
            enabled = "",
            firstName = "",
            lastName = "",
            middleName = "",
            password = "",
            phone = "",
            pin = "",
            username = "denis",
            isLoyalty = ""
        )

        // When
        localRepository.saveUserToDb(user)
        val savedUser = localRepository.getUser()

        // Then
        assertThat(savedUser).isNotNull()

    }

    @Test
    fun test_savedCheckoutModel_failure() = runBlocking {
        // Given

        // When
        val checkout = localRepository.getCheckoutModel().first()

        // Then
        println("Checkout: $checkout")
        assertThat(checkout).isEmpty()
    }

}