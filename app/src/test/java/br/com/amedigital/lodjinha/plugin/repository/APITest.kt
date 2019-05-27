package br.com.amedigital.lodjinha.plugin.repository

import br.com.amedigital.lodjinha.plugin.network.API
import br.com.amedigital.lodjinha.plugin.network.getAPI
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class APITest {

    private val baseUrl = "/"
    private val server = MockWebServer()

    private lateinit var api: API

    @Before
    fun setup() {
        server.start()
        val httpUrl = server.url(baseUrl)
        api = getAPI(httpUrl.toString())
    }

    @Test
    fun givenGetBannersCall_whenErrorIs400_throwHttpException() {
        val data = "{\"error\" : \"Bad request\" }"

        val response = MockResponse()
            .apply {
                setResponseCode(400)
                setBody(data)
            }
        server.enqueue(response)

        val apiResponse = api.getBanners().execute()

        server.takeRequest()

        Assert.assertFalse(apiResponse.isSuccessful)
        Assert.assertEquals(apiResponse.code(), 400)
    }

    @After
    fun teardown() {
        server.shutdown()
    }
}