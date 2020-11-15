package com.salamy.azora.azoramap;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devs.readmoreoption.ReadMoreOption;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.salamy.azora.azoramap.Model.NewsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private final List<NewsModel> mValues;
    NewsFragment mContext;

    public NewsListAdapter(NewsFragment context, List<NewsModel> items)
    {
        mValues = items;
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mName.setText(mValues.get(position).getName());

      /*  ReadMoreOption readMoreOption = new ReadMoreOption.Builder(mContext.getContext())
                .textLength(50, ReadMoreOption.TYPE_CHARACTER)
                .moreLabel("\t"+"read more")
                .moreLabelColor(R.color.link_color)
                .labelUnderLine(false)
                .build();

        readMoreOption.addReadMoreTo(holder.mContent,mValues.get(position).getContent());

       */

        holder.mContent.setText(mValues.get(position).getContent());
        Glide.with(mContext).load(mValues.get(position).getImage()).into(holder.mImage);

        //Format Date
        //Note : We Can Format Date Time In Server To Speed  up processing

        String strCurrentDate = mValues.get(position).getDate();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("MMM dd , yyyy");
        String date = format.format(newDate);

        holder.mDate.setText(date);
        holder.mTitle.setText(mValues.get(position).getTitle());
        holder.mContent.setText(mValues.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final CircularImageView mImage;
        public final TextView mDate;
        public final TextView mTitle;
        public final TextView mContent;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.personName);
            mImage = (CircularImageView)view.findViewById(R.id.personImage);
            mDate = (TextView) view.findViewById(R.id.newsDate);
            mTitle = (TextView) view.findViewById(R.id.newsTitle);
            mContent = (TextView) view.findViewById(R.id.newsContent);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContent.getText() + "'";
        }
    }
}