package com.keino.minispotify.di
import android.content.Context
import androidx.room.Room
import com.keino.minispotify.BuildConfig
import com.keino.minispotify.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
@Module @InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton fun database(@ApplicationContext context: Context): MusicDatabase = Room.databaseBuilder(context, MusicDatabase::class.java, "music.db").build()
    @Provides fun favorites(db: MusicDatabase): FavoriteDao = db.favorites()
    @Provides @Singleton fun api(): MusicApi = Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL)
        .client(OkHttpClient.Builder().callTimeout(15, TimeUnit.SECONDS).build())
        .addConverterFactory(GsonConverterFactory.create()).build().create(MusicApi::class.java)
}
