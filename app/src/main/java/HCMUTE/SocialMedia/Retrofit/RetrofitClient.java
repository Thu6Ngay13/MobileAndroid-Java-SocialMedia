package HCMUTE.SocialMedia.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
private static final String BASE_URL = "http://192.168.1.10:8181/api/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory (GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}