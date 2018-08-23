package RetrofitConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    protected static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    protected static TokenInterceptor tokenInterceptor = new TokenInterceptor();
    protected static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(tokenInterceptor).build();
    protected static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();
    /**
     * Create an instance of Retrofit object
     * */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    /*
      This calss will  hel us to send comman headers to the request like access token, device type etc.
     */
    private static class TokenInterceptor implements Interceptor {


        private TokenInterceptor() {

        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request initialRequest = chain.request();
            String sToken = "";
            if (sToken != null) {
                initialRequest = initialRequest.newBuilder()
                        .addHeader("Accept", "application/json; charset=utf-8")
                        .build();
            }

            Response response = chain.proceed(initialRequest);
            return response;
        }
    }

}