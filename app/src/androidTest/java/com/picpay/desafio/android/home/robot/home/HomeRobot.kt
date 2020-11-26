package com.picpay.desafio.android.home.robot.home

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.picpay.desafio.android.R
import com.picpay.desafio.android.home.ui.RecyclerViewMatchers
import com.picpay.desafio.android.ui.home.HomeActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.IOException
import java.io.InputStream

fun HomeRobot.command(func: HomeRobot.() -> Unit) = this.apply { func() }

class HomeRobot(
    private val server: MockWebServer,
    private val intent: Intent,
    private val activityRule: ActivityTestRule<HomeActivity>
) {

    fun initActivity() {
        activityRule.launchActivity(intent)
    }

    fun configureResponseSuccess() {
        server.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(readFileFromAssets())
        )
    }

    fun configureResponseError() {
        server.enqueue(MockResponse().setResponseCode(404))
    }

    fun matcherContact() {
        RecyclerViewMatchers.checkRecyclerViewItem(
            R.id.recyclerView, 0,
            withText("Eduardo Santos")
        )
    }

    fun matcherFeedbackIsVisible() {
        onView(withId(R.id.text_error))
            .check(matches(withText("Ocorreu um erro. Tente novamente.")))
            .check(matches(isDisplayed()))
    }

    private fun readFileFromAssets(): String {
        return try {
            val inputStream: InputStream =
                InstrumentationRegistry.getInstrumentation()
                    .targetContext.assets.open("mockResponse.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}