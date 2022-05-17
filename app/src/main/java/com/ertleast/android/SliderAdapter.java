package com.ertleast.android;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private List<SliderModel> sliderModelList;

    public SliderAdapter(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout, container, false);
        ConstraintLayout banner_container = view.findViewById(R.id.banner_container);
        banner_container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sliderModelList.get(position).getBackgroundColor())));
        ImageView banner = view.findViewById(R.id.banner_slide);
        TextView banner_text = view.findViewById(R.id.banner_text);

        String image_url, image_title = "";
        /*switch (position){
            case 1:
                image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/news2.jpg";
                image_title = "Air Pollution Accelerates Worsening Of Bone Health";
                //image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/news_icon.jpg";
                break;
            case 2:
                image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/news_icon.jpg";
                image_title = "Bangkok’s First Medical Marijuana Clinic Opens With Free Handouts Of Cannabis Oil";
                //image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/Aspirin-Like Compounds.jpg";
                break;
            case 3:
                image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/Aspirin-Like Compounds.jpg";
                image_title = "Aspirin-Like Compounds Could Treat Various Human Diseases And Ailments";
                //image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/news2.jpg";
                break;
            case 4:
                image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/news2.jpg";
                image_title = "Air Pollution Accelerates Worsening Of Bone Health";
                //image_url = "https://images.pexels.com/photos/60022/microscope-slide-research-close-up-60022.jpeg";
                break;
            case 5:
                image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/news_icon.jpg";
                image_title = "Bangkok’s First Medical Marijuana Clinic Opens With Free Handouts Of Cannabis Oil";
                break;
            case 6:
                image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/Aspirin-Like Compounds.jpg";
                image_title = "Aspirin-Like Compounds Could Treat Various Human Diseases And Ailments";
                break;
            case 7:
                image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/news2.jpg";
                image_title = "Air Pollution Accelerates Worsening Of Bone Health";
                break;
            *//*case 8:
                image_url = "https://images.pexels.com/photos/242178/pexels-photo-242178.jpeg";
                break;
            case 9:
                image_url = "https://images.pexels.com/photos/3279196/pexels-photo-3279196.jpeg";
                break;
            case 10:
                image_url = "https://images.pexels.com/photos/3376788/pexels-photo-3376788.jpeg";
                break;
            case 11:
                image_url = "https://images.pexels.com/photos/1161682/pexels-photo-1161682.jpeg";
                break;
            case 12:
                image_url = "https://images.pexels.com/photos/235922/pexels-photo-235922.jpeg";
                break;*//*

            default:
                image_url = "http://freetesting.site/bio_admin/uploads/news/news_image/news2.jpg";
                image_title = "default";
        }*/

        image_url = "http://thaipharmaindex.com/bio_admin/" + sliderModelList.get(position).getBanner();
        Glide.with(container).load(image_url).into(banner);
        image_title = sliderModelList.get(position).getBanner_text();
        //banner.setImageResource(sliderModelList.get(position).getBanner());
        banner_text.setText(image_title);
        container.addView(view, 0);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(container.getContext(), sliderModelList.get(position).getBanner_text(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(container.getContext(), NewsDetailsActivity.class);
                intent.putExtra("news_uri", "http://thaipharmaindex.com/bio_web/news_highlights.php?by=" + sliderModelList.get(position).getBanner_id());
                //intent.putExtra("news_name", sliderModelList.get(position).getBanner_text());
                intent.putExtra("news_name", "News Details");
                container.getContext().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return sliderModelList.size();
    }
}


