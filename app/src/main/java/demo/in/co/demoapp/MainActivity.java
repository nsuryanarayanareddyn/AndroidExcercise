package demo.in.co.demoapp;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import Adapters.ArticleAdapter;
import Models.Row;
import Uitility.Helper;


public class MainActivity extends AppCompatActivity implements ArticleContract.MainView {

    private ArticleAdapter mArticleAdapter;


    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private ArticleContract.presenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeToolbarAndRecyclerView();
        initProgressBar();
        presenter = new ArticlePresenterImpl(this, new GetRowInteractorImpl());
        if (Helper.hasNetworkConnection(MainActivity.this)) {
            presenter.requestDataFromServer();
        } else {
            this.showToast(MainActivity.this, "Please check your netwrok connection and try again.");
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh


                if (Helper.hasNetworkConnection(MainActivity.this)) {

                    presenter.requestDataFromServer();

                } else {
                    showToast(MainActivity.this, "Please check your netwrok connection and try again.");
                }

            }
        });

    }


    @Override
    public void setDataToRecyclerView(List<Row> mRowList, String title) {
        getSupportActionBar().setTitle(title);
        swipeRefreshLayout.setRefreshing(false);
        if (mRowList.size() > 0) {
            mArticleAdapter = new ArticleAdapter(MainActivity.this, mRowList, recyclerItemClickListener);
            mRecyclerView.setAdapter(mArticleAdapter);
        } else {
            showToast(MainActivity.this, "No  data found. Please try again");
        }

    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(MainActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Initializing Toolbar and RecyclerView
     */
    private void initializeToolbarAndRecyclerView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayoutLayout);
        mRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);


    }


    /**
     * Initializing progressbar programmatically
     */
    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);
    }


    /**
     * RecyclerItem click event listener
     */
    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(Row mRow) {

            Toast.makeText(MainActivity.this,
                    "You clicked on : " + mRow.getTitle(),
                    Toast.LENGTH_LONG).show();

        }
    };


    private void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}
