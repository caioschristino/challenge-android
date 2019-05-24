package br.com.amedigital.lodjinha.plugin.network

import br.com.amedigital.lodjinha.BuildConfig
import br.com.amedigital.lodjinha.base.business.dto.Pageable
import br.com.amedigital.lodjinha.model.Banner
import br.com.amedigital.lodjinha.model.Categoria
import br.com.amedigital.lodjinha.model.Produto
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serializable

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
        @Query("limit") limit : Int,
        @Query("offset") offset: Int
        ): Call<Pageable<Produto>>

    @GET("produto/{prodId}")
    fun getProdById(@Path("routeId") prodId: Long)
}

internal fun getAPI(): API {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.ENDPOINT)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
        .create(API::class.java)
}