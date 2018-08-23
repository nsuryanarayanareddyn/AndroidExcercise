package demo.in.co.demoapp;



import java.util.ArrayList;
import java.util.List;

import Models.Row;

/**
 * Created by Surya on 23/08/18.
 */

public interface MainContract {

    /**
     * Call when user interact with the view and other when view OnDestroy()
     * */
    interface presenter{

        void onDestroy();

        void onRefreshButtonClick();

        void requestDataFromServer();

    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetNoticeInteractorImpl class
     **/
    interface MainView {
        void showProgress();

        void hideProgress();


        void setDataToRecyclerView(List<Row> mRowList,String title);

        void onResponseFailure(Throwable throwable);

    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface GetRowIntractor {

        interface OnFinishedListener {
            void onFinished(List<Row> mRowList,String title);
            void onFailure(Throwable t);
        }

        void getRowArrayList(OnFinishedListener onFinishedListener);
    }
}
