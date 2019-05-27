package br.com.amedigital.lodjinha.plugin.network

import br.com.amedigital.lodjinha.BuildConfig
import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.base.business.exception.HttpException
import br.com.amedigital.lodjinha.base.business.exception.InternetConnectionException
import br.com.amedigital.lodjinha.model.Banner
import br.com.amedigital.lodjinha.model.Categoria
import br.com.amedigital.lodjinha.model.Produto
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import java.util.concurrent.TimeUnit


internal interface API {
    @GET("banner")
    fun getBanners(): Call<Pageable<Banner>>

    @GET("categoria")
    fun getCategories(): Call<Pageable<Categoria>>

    @GET("produto/maisvendidos")
    fun getProdSalesRank(): Call<Pageable<Produto>>

    @GET("produto")
    fun getProdByCategory(
        @Query("categoriaId") categoryId : Long,
        @Query("offset") offset: Int,
        @Query("limit") limit : Int
    ): Call<Pageable<Produto>>

    @GET("produto/{prodId}")
    fun getProdById(@Path("prodId") prodId: Long): Call<Produto>
}

internal fun getAPI(baseUrl: String = BuildConfig.ENDPOINT): API {
    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    val client = OkHttpClient.Builder().let {
        it.addInterceptor(interceptor)
        it.connectTimeout(5, TimeUnit.SECONDS)
        it.readTimeout(5, TimeUnit.SECONDS)
        it.writeTimeout(5, TimeUnit.SECONDS)
        it.build()
    }

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
        .create(API::class.java)
}

internal fun <P> executeCall(call: Call<P>): Response<P>? {
    try {
        val response = call.execute()
        if(!response.isSuccessful) throw HttpException(response.code(), response.message())
        return response
    } catch(e: IOException) {
        throw InternetConnectionException()
    }
}