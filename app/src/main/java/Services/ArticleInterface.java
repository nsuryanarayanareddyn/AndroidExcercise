package Services;

import Models.ResponseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ArticleInterface {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("facts.json")
    Call<ResponseData> getArticles();
}
