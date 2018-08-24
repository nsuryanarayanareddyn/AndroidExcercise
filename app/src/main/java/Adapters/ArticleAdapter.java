package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import Models.Row;
import Uitility.CustomFonts;
import demo.in.co.demoapp.R;
import demo.in.co.demoapp.RecyclerItemClickListener;


public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private Context mContext;
    private List<Row> mArticleList;
    private RecyclerItemClickListener recyclerItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mArticletitle, mArticledeslable;
        public ImageView mArticleThumbnail;

        public MyViewHolder(View view) {
            super(view);
            mArticletitle = (TextView) view.findViewById(R.id.aticletitle);
            mArticledeslable = (TextView) view.findViewById(R.id.deslable);
            mArticleThumbnail = (ImageView) view.findViewById(R.id.articleimage);
            mArticletitle.setTypeface(CustomFonts.getNexaBold(mContext));
            mArticledeslable.setTypeface(CustomFonts.getNexaRegular(mContext));

        }
    }


    public ArticleAdapter(Context mContext, List<Row> results, RecyclerItemClickListener recyclerItemClickListener) {
        this.mContext = mContext;
        this.mArticleList = results;
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Row mResult = mArticleList.get(position);

        if (mResult.getTitle() != null && mResult.getTitle() != "") {
            holder.mArticletitle.setText(mResult.getTitle());
        } else {
            holder.mArticletitle.setText("No title");
        }

        if (mResult.getDescription() != null && mResult.getDescription() != "") {
            holder.mArticledeslable.setText(mResult.getDescription());
        } else {
            holder.mArticledeslable.setText("No descrption");
        }

        if (mResult.getImageHref() != null && mResult.getImageHref() != "") {
            Picasso.with(mContext).load(mResult.getImageHref()).fit().placeholder(R.drawable.image_preview)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .error(R.drawable.image_preview).into(holder.mArticleThumbnail);
        } else {
            holder.mArticleThumbnail.setImageResource(R.drawable.image_preview);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(mArticleList.get(position));
            }
        });


    }

}