package HCMUTE.SocialMedia.Retrofit;

import HCMUTE.SocialMedia.Consts.Const;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJST0xFIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sInN1YiI6InBoYXAiLCJpYXQiOjE3MTU5NTI1MzEsImV4cCI6MTcxNjAzODkzMX0.AsTZPfUvm58CdbFcydQxBBpX3F7uE0U1BG2pQoeslgk";
    private static final String BASE_URL = "http://192.168.1.6:8181/api/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl(Const.SERVER_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", " Bearer " + Const.TOKEN)
                    .build();
            return chain.proceed(newRequest);
        }).build();
        return client;
    }
}