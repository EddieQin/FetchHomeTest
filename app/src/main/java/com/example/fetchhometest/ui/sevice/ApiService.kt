import com.example.fetchhometest.ui.entity.HiringItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    fun getHiringItems(): Call<List<HiringItem>>

    companion object {
        private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
