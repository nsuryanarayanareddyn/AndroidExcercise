package demo.in.co.demoapp;

import android.util.Log;

import java.util.List;

import Models.ResponseData;
import Models.Row;
import RetrofitConfig.RetrofitInstance;
import Services.ArticleInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRowInteractorImpl implements ArticleContract.GetRowIntractor {

    @Override
    public void getRowArrayList(final OnFinishedListener onFinishedListener) {

        /** Create handle for the RetrofitInstance interface*/
        ArticleInterface service = RetrofitInstance.getRetrofitInstance().create(ArticleInterface.class);

        /** Call the method with parameter in the interface to get the Rows data*/
        Call<ResponseData> call = service.getArticles();

        /**Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Response<ResponseData> mResponse = (Response<ResponseData>) response;
                List<Row> mRowList = mResponse.body().getRows();

                onFinishedListener.onFinished(mRowList, mResponse.body().getTitle());

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                onFinishedListener.onFailure(t);
                int x = 0;
            }
        });

    }

}
