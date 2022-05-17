package com.ertleast.android;

import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HorizonalProductScrollAdapter extends RecyclerView.Adapter<HorizonalProductScrollAdapter.ViewHolder> {


    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizonalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public HorizonalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String resource = horizontalProductScrollModelList.get(position).getProductImage();
        final String title = horizontalProductScrollModelList.get(position).getProductTitle();
        final String id = horizontalProductScrollModelList.get(position).getProductID();

        /*switch (position)
        {
            case 1:
                title = "GASTROENTEROLOGY";
                break;
            case 2:
                title = "CARDIOLOGY";
                break;
            case 3:
                title = "Multidisciplinary";
                break;

        }*/

        holder.setProductImage(resource, position);
        holder.setProductTitle(title);

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), horizontalProductScrollModelList.get(position).getProductTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), SpecialityDetailsActivity.class);
                String current_language = LocaleHelper.getLanguage(view.getContext());
                if(current_language.equals("th")){
                    //intent.putExtra("speciality_uri", "http://thaipharmaindex.com/bio_web/specialtythai.php?by=" + id);
                    intent.putExtra("speciality_uri", "http://thaipharmaindex.com/specialtythai.php?by=" + id);
                }
                else{
                    //intent.putExtra("speciality_uri", "http://thaipharmaindex.com/bio_web/specialty.php?by=" + id);
                    intent.putExtra("speciality_uri", "http://thaipharmaindex.com/specialty.php?by=" + id);
                }//intent.putExtra("speciality_name", title);
                Resources resources = view.getResources();
                intent.putExtra("speciality_name", resources.getString(R.string.speciality_details));
                view.getContext().startActivity(intent);

            }
        });

        holder.productTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), horizontalProductScrollModelList.get(position).getProductTitle(), Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(view.getContext(), SpecialityDetailsActivity.class);

                String current_language = LocaleHelper.getLanguage(view.getContext());
                if(current_language.equals("th")){
                    //intent.putExtra("speciality_uri", "http://thaipharmaindex.com/bio_web/specialtythai.php?by=" + id);
                    intent.putExtra("speciality_uri", "http://thaipharmaindex.com/specialtythai.php?by=" + id);
                }
                else{
                    //intent.putExtra("speciality_uri", "http://thaipharmaindex.com/bio_web/specialty.php?by=" + id);
                    intent.putExtra("speciality_uri", "http://thaipharmaindex.com/specialty.php?by=" + id);
                }

                //intent.putExtra("speciality_name", title);
                Resources resources = view.getResources();
                intent.putExtra("speciality_name", resources.getString(R.string.speciality_details));
                view.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), horizontalProductScrollModelList.get(position).getProductTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), SpecialityDetailsActivity.class);
                String current_language = LocaleHelper.getLanguage(view.getContext());
                if(current_language.equals("th")){
                   // intent.putExtra("speciality_uri", "http://thaipharmaindex.com/bio_web/specialtythai.php?by=" + id);
                    intent.putExtra("speciality_uri", "http://thaipharmaindex.com/specialtythai.php?by=" + id);
                }
                else{
                    //intent.putExtra("speciality_uri", "http://thaipharmaindex.com/bio_web/specialty.php?by=" + id);
                    intent.putExtra("speciality_uri", "http://thaipharmaindex.com/specialty.php?by=" + id);
                }//intent.putExtra("speciality_name", title);
                Resources resources = view.getResources();
                intent.putExtra("speciality_name", resources.getString(R.string.speciality_details));
                view.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {


        //return 16;
        return horizontalProductScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView productImage;
        private TextView productTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.h_s_product_image);
            productTitle = itemView.findViewById(R.id.h_s_product_title);


        }

        private void setProductImage(String resource, int position) {
            //productImage.setImageResource(resource);
            String image_url;
            /*switch (position){
                case 0:
                    image_url = "http://freetesting.site/bio_admin/images/specility_icon_image/19.jpg";
                    break;
                case 1:
                    image_url = "http://freetesting.site/bio_admin/images/specility_icon_image/Gi-Science.jpg";
                    break;
                case 2:
                    image_url = "http://freetesting.site/bio_admin/images/specility_icon_image/20.jpg";
                    break;
                default:
                    image_url = "test";
            }*/

            image_url = "http://thaipharmaindex.com/bio_admin/" +  resource;
            Glide.with(itemView).load(image_url).into(productImage);
        }

        private void setProductTitle(String title) {
            productTitle.setText(title);
        }
    }
}
