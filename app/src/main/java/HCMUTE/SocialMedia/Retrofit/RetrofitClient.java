package HCMUTE.SocialMedia.Retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJST0xFIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sInN1YiI6ImFiY0BnbWFpbC5jb20iLCJpYXQiOjE3MTU1MDg1NzIsImV4cCI6MTcxNTU5NDk3Mn0.n40hIILkl3YnkjeTTXWY17RGJlLrAQOuEcL_k56D3yY";
    private static final String BASE_URL = "http://192.168.1.10:8181/api/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", " Bearer " + TOKEN)
                    .build();
            return chain.proceed(newRequest);
        }).build();
        return client;
    }
}