package com.oza.challenge.di

import com.oza.challenge.data.local.AppCache
import com.oza.challenge.data.remote.AuthService
import com.oza.challenge.data.remote.MockAuthService
import com.oza.challenge.data.remote.PixabayService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *  Define the dependencies and their providers here
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppCache(): AppCache {
        return AppCache()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(appCache: AppCache): OkHttpClient {
        val headerInterceptor = Interceptor { chain ->
            chain.run {
                val builder = chain.request().newBuilder()
//                builder.addHeader("Authorization", "Bearer ${appCache.token}")
                builder.addHeader("Content-Type", "application/json;charset=UTF-8")
                builder.addHeader("App-Version", BuildConfig.VERSION_NAME)
                return@Interceptor chain.proceed(builder.build())
            }
        }

        val logging = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

        return OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(headerInterceptor)
            .build()

    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pixabay.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        // return retrofit.create(AuthService::class.java)
        return MockAuthService()
    }

    @Singleton
    @Provides
    fun providePixabayService(retrofit: Retrofit): PixabayService {
        return retrofit.create(PixabayService::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideDatabase(app: Application): AppDatabase {
//        return Room
//            .databaseBuilder(app, AppDatabase::class.java, "database.db")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideUserDao(db: AppDatabase): UserDao {
//        return db.userDao()
//    }
//
//    @Singleton
//    @Provides
//    fun providePixabayDao(db: AppDatabase): PixabayDao {
//        return db.repoDao()
//    }


}
