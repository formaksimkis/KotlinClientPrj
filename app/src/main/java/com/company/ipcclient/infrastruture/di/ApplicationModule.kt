

package com.company.ipcclient.infrastruture.di

import android.app.Application
import androidx.room.Room
import com.company.ipcclient.infrastruture.clients.ServerClient
import com.company.ipcclient.infrastruture.clients.interfaces.IServerClient
import com.company.ipcclient.infrastruture.database.ChannelDatabase
import com.company.ipcclient.repositories.RemoteRepository
import com.company.ipcclient.repositories.interfaces.IChannelRepository
import com.company.ipcclient.repositories.interfaces.ICommandRepository
import com.company.ipcclient.repositories.interfaces.ILogsRepository
import com.company.ipcclient.repositories.interfaces.IPropRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    companion object{
        const val DB_NAME = "ChannelDataBase"
    }

    @Provides
    @Singleton
    fun provideServerClient(): IServerClient {
        return ServerClient()
    }

    @Provides
    @Singleton
    fun provideRepo(serverClient: IServerClient, channelDatabase: ChannelDatabase): RemoteRepository {
        return RemoteRepository(serverClient, channelDatabase)
    }

    @Provides
    @Singleton
    fun provideIChannelRepository(remoteRepository: RemoteRepository): IChannelRepository {
        return remoteRepository
    }

    @Provides
    @Singleton
    fun provideICommandRepository(remoteRepository: RemoteRepository): ICommandRepository {
        return remoteRepository
    }

    @Provides
    @Singleton
    fun provideIPropRepository(remoteRepository: RemoteRepository): IPropRepository {
        return remoteRepository
    }

    @Provides
    @Singleton
    fun provideILogsRepository(remoteRepository: RemoteRepository): ILogsRepository {
        return remoteRepository
    }

    @Provides
    @Singleton
    fun provideRoom(application: Application): ChannelDatabase {
        return Room.databaseBuilder(
            application, ChannelDatabase::class.java, DB_NAME
        ).build()
    }
}