package com.ertleast.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapterExampleTop extends SliderViewAdapter<SliderAdapterExampleTop.SliderAdapterVH> {

    private Context context;
    private int count;
    private String[][] AdsArray = new String[100][2];

    public SliderAdapterExampleTop(Context context, int count, String[][] AdsArray) {
        this.context = context;
        this.count = count;
        this.AdsArray = AdsArray;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_top_autoslider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        //viewHolder.textViewDescription.setText("This is slider item " + position);


        Glide.with(viewHolder.itemView)
                .load( "http://thaipharmaindex.com/bio_admin/" + AdsArray[position][1])
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
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
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}