package com.picpay.desafio.android.home.ui.home

import android.content.Intent
import androidx.test.rule.ActivityTestRule
import com.appfactory.data.source.remote.services.PicPayService
import com.picpay.desafio.android.home.robot.home.HomeRobot
import com.picpay.desafio.android.home.robot.home.command
import com.picpay.desafio.android.ui.home.HomeActivity
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class HomeActivityTest {

    private val server = MockWebServer()
    lateinit var robot: HomeRobot

    @Before
    fun setup() {
        loadKoinModules(mockedRetrofit(server.url("").toString()))
        robot = HomeRobot(
            server,
            Intent(),
            ActivityTestRule(HomeActivity::class.java)
        )
        robot.initActivity()
    }

    @Test
    fun shouldBeShowFeedbackWhenError() {
        robot.command {
            configureResponseError()
            matcherFeedbackIsVisible()
        }
    }

    @Test
    fun shouldBeShowContactWhenSuccess() {
        robot.command {
            configureResponseSuccess()
            matcherContact()
        }
    }

    private fun mockedRetrofit(mockUrl: String) = module {
        single(override = true) {
            get<Retrofit>().create(PicPayService::class.java)
        }

        single(override = true) {
            Retrofit.Builder()
                .baseUrl(mockUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PicPayService::class.java)
        }
    }
}