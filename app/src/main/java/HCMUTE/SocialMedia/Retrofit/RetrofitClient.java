package HCMUTE.SocialMedia.Retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJST0xFIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sInN1YiI6IjIxMTEwNTg3QHN0dWRlbnQuaGNtdXRlLmVkdS52biIsImlhdCI6MTcxNTY5ODIyMCwiZXhwIjoxNzE1Njk5NjYwfQ.SYPV3c-18DC5VXrqFaeOSgJ4pvT_aBmawVHi1bx3fQ0";
    private static final String BASE_URL = "http://localhost:8181/api/";
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