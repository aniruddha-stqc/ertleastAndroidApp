package com.ertleast.android;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DrugSearchAdapter extends RecyclerView.Adapter<DrugSearchAdapter.ViewHolder> {


    private List<DrugSearchModel> DrugSearchModelList;

    public DrugSearchAdapter(List<DrugSearchModel> DrugSearchModelList) {
        this.DrugSearchModelList = DrugSearchModelList;
    }

    @NonNull
    @Override
    public DrugSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drug_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String resource = DrugSearchModelList.get(position).getProductType();
        final String title = DrugSearchModelList.get(position).getProductTitle();
        final String id = DrugSearchModelList.get(position).getProductID();
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

        //holder.setProductImage(resource, position);
        holder.setProductTitle(title);
        holder.setProductType(resource,position);

        holder.productType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), DrugSearchModelList.get(position).getProductTitle(), Toast.LENGTH_SHORT).show();
                //Log.d("Clicked", DrugSearchModelList.get(position).getProductTitle());

                String url = null;
                switch (DrugSearchModelList.get(position).getProductType()){
                    case "B":
                        //url = "http://thaipharmaindex.com/bio_web/product_details.php?by=";
                        url = "http://thaipharmaindex.com/product_details.php?by=";
                        break;
                    case "G":
                        //url = "http://thaipharmaindex.com/bio_web/generic_details.php?by=";
                        url = "http://thaipharmaindex.com/generic_details.php?by=";
                        break;
                    case "P":
                        //url = "http://thaipharmaindex.com/bio_web/pharma_details.php?by=";
                        url = "http://thaipharmaindex.com/pharma_details.php?by=";
                        break;
                    default:
                        url = "Type: Miscellaneous";
                }

                Intent intent = new Intent(view.getContext(), SpecialityDetailsActivity.class);


                intent.putExtra("speciality_uri", url + id);
                //intent.putExtra("speciality_name", title);
                intent.putExtra("speciality_name", "Search Details");
                view.getContext().startActivity(intent);
            }
        });

        holder.productTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), DrugSearchModelList.get(position).getProductTitle(), Toast.LENGTH_SHORT).show();
                //Log.d("Clicked", DrugSearchModelList.get(position).getProductTitle());

                String url = null;
                switch (DrugSearchModelList.get(position).getProductType()){
                    case "B":
                        //url = "http://thaipharmaindex.com/bio_web/product_details.php?by=";
                        url = "http://thaipharmaindex.com/product_details.php?by=";
                        break;
                    case "G":
                        //url = "http://thaipharmaindex.com/bio_web/generic_details.php?by=";
                        url = "http://thaipharmaindex.com/generic_details.php?by=";
                        break;
                    case "P":
                        //url = "http://thaipharmaindex.com/bio_web/pharma_details.php?by=";
                        url = "http://thaipharmaindex.com/pharma_details.php?by=";
                        break;
                    default:
                        url = "Type: Miscellaneous";
                }

                Intent intent = new Intent(view.getContext(), SpecialityDetailsActivity.class);


                intent.putExtra("speciality_uri", url + id);
                //intent.putExtra("speciality_name", title);
                intent.putExtra("speciality_name", "Search Details");
                view.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), DrugSearchModelList.get(position).getProductTitle(), Toast.LENGTH_SHORT).show();
                //Log.d("Clicked", DrugSearchModelList.get(position).getProductTitle());

                String url = null;
                switch (DrugSearchModelList.get(position).getProductType()){
                    case "B":
                        //url = "http://thaipharmaindex.com/bio_web/product_details.php?by=";
                        url = "http://thaipharmaindex.com/product_details.php?by=";
                        break;
                    case "G":
                        //url = "http://thaipharmaindex.com/bio_web/generic_details.php?by=";
                        url = "http://thaipharmaindex.com/generic_details.php?by=";
                        break;
                    case "P":
                        //url = "http://thaipharmaindex.com/bio_web/pharma_details.php?by=";
                        url = "http://thaipharmaindex.com/pharma_details.php?by=";
                        break;
                    default:
                        url = "Type: Miscellaneous";
                }
                Intent intent = new Intent(view.getContext(), SpecialityDetailsActivity.class);
                intent.putExtra("speciality_uri", url + id);
                //intent.putExtra("speciality_name", title);
                intent.putExtra("speciality_name", "Search Details");
                view.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {


        //return 16;
        return DrugSearchModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView productTitle;
        private TextView productType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //productImage = itemView.findViewById(R.id.h_s_product_image);
            productTitle = itemView.findViewById(R.id.textView_search_result);
            productType = itemView.findViewById(R.id.textView2_search_result);

        }

        private void setProductType(String resource, int position) {
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
            switch (resource){

                case "B":
                    resource = itemView.getContext().getString(R.string.type_brand);
                    break;
                case "G":
                    resource = itemView.getContext().getString(R.string.type_generic);
                    break;
                case "P":
                    resource = itemView.getContext().getString(R.string.type_pharma);
                    break;
                default:
                    resource = "Type: Unknown";
            }

            productType.setText(resource); //= "http://freetesting.site/bio_admin/" +  resource;
            //Glide.with(itemView).load(image_url).into(productImage);
        }

        private void setProductTitle(String title) {
            productTitle.setText(title);
        }
    }
}
