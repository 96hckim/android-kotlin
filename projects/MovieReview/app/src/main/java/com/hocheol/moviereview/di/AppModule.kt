package com.hocheol.moviereview.di

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hocheol.moviereview.data.api.MovieApi
import com.hocheol.moviereview.data.api.MovieFirestoreApi
import com.hocheol.moviereview.data.api.ReviewApi
import com.hocheol.moviereview.data.api.ReviewFirestoreApi
import com.hocheol.moviereview.data.repository.MovieRepository
import com.hocheol.moviereview.data.repository.MovieRepositoryImpl
import com.hocheol.moviereview.data.repository.ReviewRepository
import com.hocheol.moviereview.data.repository.ReviewRepositoryImpl
import com.hocheol.moviereview.domain.usecase.GetAllMoviesUseCase
import com.hocheol.moviereview.domain.usecase.GetRandomFeaturedMovieUseCase
import com.hocheol.moviereview.presentation.home.HomeContract
import com.hocheol.moviereview.presentation.home.HomePresenter
import com.hocheol.moviereview.presentation.home.HomeFragment
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {
    single { Dispatchers.IO }
}

val dataModule = module {
    single { Firebase.firestore }

    single<MovieApi> { MovieFirestoreApi(get()) }
    single<ReviewApi> { ReviewFirestoreApi(get()) }
//    single<UserApi> { UserFirestoreApi(get()) }

    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<ReviewRepository> { ReviewRepositoryImpl(get(), get()) }
//    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }

//    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
//    single<PreferenceManager> { SharedPreferenceManager(get()) }
}

val domainModule = module {
    factory { GetRandomFeaturedMovieUseCase(get(), get()) }
    factory { GetAllMoviesUseCase(get()) }
//    factory { GetAllMovieReviewsUseCase(get(), get()) }
//    factory { GetMyReviewedMoviesUseCase(get(), get(), get()) }
//    factory { SubmitReviewUseCase(get(), get()) }
//    factory { DeleteReviewUseCase(get()) }
}

val presenterModule = module {
    scope<HomeFragment> {
        scoped<HomeContract.Presenter> { HomePresenter(getSource(), get(), get()) }
    }
//    scope<MovieReviewsFragment> {
//        scoped<MovieReviewsContract.Presenter> { (movie: Movie) ->
//            MovieReviewsPresenter(movie, getSource(), get(), get(), get())
//        }
//    }
//    scope<MyPageFragment> {
//        scoped<MyPageContract.Presenter> { MyPagePresenter(getSource(), get()) }
//    }
}
