package fa.kz.aua.data

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/group?")
    fun getData(@Query("id") name: String, @Query("units") u: String, @Query("appid") appId: String): Observable<ResponseBody>

    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("https://api.openweathermap.org/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}



