package com.ertleast.android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapterExampleNews extends SliderViewAdapter<SliderAdapterExampleNews.SliderAdapterVH> {

    private Context context;
    private int count;
    private String[][] AdsArray = new String[100][2];

    public SliderAdapterExampleNews(Context context, int count, String[][] AdsArray) {
        this.context = context;
        this.count = count;
        this.AdsArray = AdsArray;
    }

    @Override
    public SliderAdapterExampleNews.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_autoslider, null);
        return new SliderAdapterExampleNews.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterExampleNews.SliderAdapterVH viewHolder, final int position) {
        viewHolder.textViewDescription.setText(AdsArray[position][1]);


        Glide.with(viewHolder.itemView)
                //.load( "http://freetesting.site/bio_admin/" + AdsArray[position][2])
                .load( AdsArray[position][2])
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, AdsArray[position][1], Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, sliderModelList.get(position).getBanner_text(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                //intent.putExtra("news_uri", "http://freetesting.site/bio_web/news_highlights.php?by=" + AdsArray[position][0]);
                intent.putExtra("news_uri",  AdsArray[position][0]);
                intent.putExtra("news_name", "News Details");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getCount() {

        //slider view count could be dynamic size
        return count;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider_news);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider_news);
            this.itemView = itemView;
        }
    }
}