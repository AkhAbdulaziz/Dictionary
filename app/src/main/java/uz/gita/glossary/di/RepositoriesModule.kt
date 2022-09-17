package uz.gita.glossary.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.glossary.domain.repository.AppRepository
import uz.gita.glossary.domain.repository.impl.AppRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {
    @Binds
    @Singleton
    fun getAppRepository(repository: AppRepositoryImpl): AppRepository
}