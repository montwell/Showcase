package com.montwell.showcase

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import com.montwell.showcase.api.TheMovieDbOrgApi
import com.montwell.showcase.db.MovieDb
import com.montwell.showcase.repos.MovieRepository

interface ServiceLocator {

    companion object {

        private val LOCK = Any()

        private var instance: ServiceLocator? = null

        fun instance(context: Context): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator(
                        application = context.applicationContext as Application,
                        useInMemoryDb = false
                    )
                }
                return instance!!
            }
        }

        @VisibleForTesting
        fun swap(locator: ServiceLocator) {
            instance = locator
        }
    }

    fun getMovieRepository(): MovieRepository
}

open class DefaultServiceLocator(
    val application: Application,
    val useInMemoryDb: Boolean
) : ServiceLocator {

    private val movieDb by lazy {
        MovieDb.create(application, useInMemoryDb)
    }

    private val movieApi by lazy {
        TheMovieDbOrgApi.create()
    }

    override fun getMovieRepository(): MovieRepository {
        return MovieRepository(movieDb, movieApi)
    }
}