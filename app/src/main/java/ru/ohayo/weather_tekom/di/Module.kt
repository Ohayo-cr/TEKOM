package ru.ohayo.weather_tekom.di

import android.content.Context
import androidx.room.Room
import ru.ohayo.weather_tekom.data.room.city.CityDao
import ru.ohayo.weather_tekom.data.room.database.WeatherDatabase
import ru.ohayo.weather_tekom.data.room.weatherCache.WeatherDao
import ru.ohayo.weather_tekom.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ohayo.weather_tekom.data.remote.api.WeatherApi
import ru.ohayo.weather_tekom.repository.DefaultWeatherRepository
import ru.ohayo.weather_tekom.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            WeatherDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCityDao(appDatabase: WeatherDatabase): CityDao {
        return appDatabase.cityDao()
    }

    @Provides
    @Singleton
    fun provideAccountDao(appDatabase: WeatherDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }
    @Provides
    fun provideWeatherRepository(
        weatherApi: WeatherApi,
        weatherDao: WeatherDao
    ): WeatherRepository {
        return DefaultWeatherRepository(weatherApi, weatherDao)
    }
}