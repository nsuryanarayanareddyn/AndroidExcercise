package demo.in.co.demoapp;



import java.util.ArrayList;
import java.util.List;

import Models.Row;

/**
 * Created by Surya on 23/08/18.
 */

public class MainPresenterImpl implements MainContract.presenter, MainContract.GetRowIntractor.OnFinishedListener {

    private MainContract.MainView mainView;
    private MainContract.GetRowIntractor getRowIntractor;

    public MainPresenterImpl(MainContract.MainView mainView, MainContract.GetRowIntractor getRowIntractor) {
        this.mainView = mainView;
        this.getRowIntractor = getRowIntractor;
    }

    @Override
    public void onDestroy() {

        mainView = null;

    }

    @Override
    public void onRefreshButtonClick() {

        if(mainView != null){
            mainView.showProgress();
        }
        getRowIntractor.getRowArrayList(this);

    }

    @Override
    public void requestDataFromServer() {
        getRowIntractor.getRowArrayList(this);
    }


    @Override
    public void onFinished(List<Row> mRowList,String title) {
        if(mainView != null){
            mainView.setDataToRecyclerView(mRowList,title);
            mainView.hideProgress();
        }
    }




    @Override
    public void onFailure(Throwable t) {
        if(mainView != null){
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }
}
