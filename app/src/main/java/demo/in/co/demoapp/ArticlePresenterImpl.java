package demo.in.co.demoapp;


import java.util.List;

import Models.Row;

/**
 * Created by Surya on 23/08/18.
 */

public class ArticlePresenterImpl implements ArticleContract.presenter, ArticleContract.GetRowIntractor.OnFinishedListener {

    private ArticleContract.MainView mainView;
    private ArticleContract.GetRowIntractor getRowIntractor;

    public ArticlePresenterImpl(ArticleContract.MainView mainView, ArticleContract.GetRowIntractor getRowIntractor) {
        this.mainView = mainView;
        this.getRowIntractor = getRowIntractor;
    }


    @Override
    public void requestDataFromServer() {
        getRowIntractor.getRowArrayList(this);
    }


    @Override
    public void onFinished(List<Row> mRowList, String title) {
        if (mainView != null) {
            mainView.setDataToRecyclerView(mRowList, title);
            mainView.hideProgress();
        }
    }


    @Override
    public void onFailure(Throwable t) {
        if (mainView != null) {
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }
}
