package com.ertleast.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    /// Horizontal Product Layout
    private TextView horizontalLayoutTitle;
    private RecyclerView horizontalRecyclerView;

    /// Horizontal Product Layout

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);


        /// Search Bar

        final EditText search_text = view.findViewById(R.id.search_text);

        search_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){

                    Intent searchIntent = new Intent(view.getContext(), SearchActivity.class);
                    startActivity(searchIntent);
                    //Snackbar snackbar = Snackbar.make(view, "Search Click Under Construction", Snackbar.LENGTH_SHORT);
                   // snackbar.show();

                }
                return true;// return is important..
            }
        });
        /// Search bar


        ///Auto Slider


        ///Auto Slider


        //// Banner Slider

        //FetchNewsUpdate(view);
        FetchRSSNewsUpdate(view);

        /// Banner Slider


        /// Strip Ad

        FetchAdsTopBottomUpdate(view);

        /// Strip Ad


        /// Horizontal Product Layout
        FetchSpecialityUpdate(view);


        /// Horizontal Product Layout

        /// Strip Ad Bottom


        /// Strip Ad Bottom
        return view;
    }


    private void FetchAdsTopBottomUpdate(final View view) {

        String url = "http://thaipharmaindex.com/bio_mobile/android/ads_home.php";

        makeRequest(url, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {


                String server_response_code = result.getString("status");

                switch (server_response_code) {

                    case "0":
                        int ads_item_count = Integer.parseInt(result.getString("ads_item_count"));
                        JSONArray news_list = result.getJSONArray("ads_list");

                        String[][] AdsTopArray = new String[100][2];
                        String[][] AdsBottomArray = new String[100][2];
                        int top = 0;
                        int bottom = 0;
                        for (int i = 0; i < ads_item_count; i++) {
                            JSONObject ads_item = news_list.getJSONObject(i);

                            if (ads_item.getString("ads_position").equals("T")) {
                                AdsTopArray[top][0] = ads_item.getString("ads_id");
                                AdsTopArray[top][1] = ads_item.getString("ads_url");
                                top++;
                            }
                            if (ads_item.getString("ads_position").equals("B")) {
                                AdsBottomArray[bottom][0] = ads_item.getString("ads_id");
                                AdsBottomArray[bottom][1] = ads_item.getString("ads_url");
                                bottom++;
                            }


                        }

                        //ShowAdsTopBannerSlider(view, top,AdsTopArray );
                        //ShowAdsBottomBannerSlider(view, bottom,AdsBottomArray );
                        AdsTopAutoSlider(view, top, AdsTopArray);
                        AdsBottomAutoSlider(view, bottom, AdsBottomArray);

                        break;
                    default:
                        //ShowMessage("Something went wrong");
                        Snackbar.make(view, R.string.SomethingWentWrong, Snackbar.LENGTH_SHORT).show();
                        break;

                }

            }

            @Override
            public void onError(String result) {

                Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show();
            }

        });


    }

    private void AdsTopAutoSlider(View view, int top_count, String[][] adsTopArray) {
        //setContentView(R.layout.ads_top_autoslider);
        SliderView sliderView = view.findViewById(R.id.imageSlider);

        SliderAdapterExampleTop adapter = new SliderAdapterExampleTop(getActivity(), top_count, adsTopArray);

        sliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM
        // or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        //set scroll delay in seconds :
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();
    }
    private void ShowNewsAutoSlider(View view, int news_item_count, String[][] newsArray) {
        //setContentView(R.layout.ads_top_autoslider);
        SliderView sliderView = view.findViewById(R.id.imageSlider_news);

        SliderAdapterExampleNews adapter = new SliderAdapterExampleNews(getActivity(), news_item_count, newsArray);

        sliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM
        // or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        //set scroll delay in seconds :
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();

    }
    private void AdsBottomAutoSlider(View view, int bottom_count, String[][] adsBottomArray) {
        SliderView sliderView = view.findViewById(R.id.imageSlider_bottom);

        SliderAdapterExampleBottom adapter = new SliderAdapterExampleBottom(getActivity(), bottom_count, adsBottomArray);

        sliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM
        // or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        //sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        //set scroll delay in seconds :
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();

    }


    private void ShowHorizontalScroll(View view, int speciality_item_count, String[][] specialityArray) {
        //horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerview);

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();


        for (int i = 0; i < speciality_item_count; i++) {
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(specialityArray[i][0], specialityArray[i][2], specialityArray[i][1]));

        }

        HorizonalProductScrollAdapter horizonalProductScrollAdapter = new HorizonalProductScrollAdapter(horizontalProductScrollModelList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);
        horizontalRecyclerView.setAdapter(horizonalProductScrollAdapter);
        horizonalProductScrollAdapter.notifyDataSetChanged();
    }







    /// Banner Slider


    private void FetchNewsUpdate(final View view) {
        String url = "http://thaipharmaindex.com/bio_mobile/android/news_details.php";

        makeRequest(url, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {


                String server_response_code = result.getString("status");

                switch (server_response_code) {

                    case "0":
                        int news_item_count = Integer.parseInt(result.getString("news_item_count"));
                        JSONArray news_list = result.getJSONArray("news_list");

                        String[][] NewsArray = new String[100][3];
                        for (int i = 0; i < news_item_count; i++) {
                            JSONObject news_item = news_list.getJSONObject(i);
                            NewsArray[i][0] = news_item.getString("news_id");
                            NewsArray[i][1] = news_item.getString("news_head_eng");
                            NewsArray[i][2] = news_item.getString("news_icon_img");


                        }

                        //ShowBannerSlider(view, news_item_count, NewsArray);
                        ShowNewsAutoSlider(view, news_item_count, NewsArray);

                        //Log.d("H", "test");
                        //ShowMessage("User not registered");
                        //Snackbar.make(view, "Registration Approval is still Pending", Snackbar.LENGTH_SHORT).show();
                        break;
                    default:
                        //ShowMessage("Something went wrong");
                        Snackbar.make(view, R.string.SomethingWentWrong, Snackbar.LENGTH_SHORT).show();
                        break;

                }

            }

            @Override
            public void onError(String result) {

                Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show();
            }

        });

    }



    private void FetchSpecialityUpdate(final View view) {
        String url = "http://thaipharmaindex.com/bio_mobile/android/speciality_mast.php";

        makeRequest(url, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {


                String server_response_code = result.getString("status");

                switch (server_response_code) {

                    case "0":
                        int speciality_item_count = Integer.parseInt(result.getString("speciality_item_count"));
                        JSONArray speciality_list = result.getJSONArray("speciality_list");

                        String[][] SpecialityArray = new String[30][3];
                        for (int i = 0; i < speciality_item_count; i++) {
                            JSONObject speciality_item = speciality_list.getJSONObject(i);
                            SpecialityArray[i][0] = speciality_item.getString("speciality_id");
                            SpecialityArray[i][1] = speciality_item.getString("speciality_name");
                            SpecialityArray[i][2] = speciality_item.getString("speciality_h_image");
                        }

                        ShowHorizontalScroll(view, speciality_item_count, SpecialityArray);
                       // Log.d("H", "test");
                        //ShowMessage("User not registered");
                        //Snackbar.make(view, "Registration Approval is still Pending", Snackbar.LENGTH_SHORT).show();
                        break;
                    default:
                        //ShowMessage("Something went wrong");
                        Snackbar.make(view, R.string.SomethingWentWrong, Snackbar.LENGTH_SHORT).show();
                        break;

                }

            }

            @Override
            public void onError(String result) {

                //Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show();
            }

        });

    }



    private void FetchRSSNewsUpdate(final View view) {
        String url = "http://thaipharmaindex.com/bio_mobile/android/rss_details.php";

        makeRequest(url, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException, IOException, XmlPullParserException {


                String server_response_code = result.getString("status");


                switch (server_response_code) {

                    case "0":
                        String rss_feed_url = result.getString("rss_link");
                        //rss_feed_url = "https://wp.staging.medscape.com/cx/rssfeeds/2667.xml";
                        //Fetch RSS Feeds
                        StringRequest request = new StringRequest(Request.Method.GET, rss_feed_url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        // convert the String response to XML
                                        // if you use Simple, something like following should do it
                                        //Serializer serializer = new Persister();
                                        //serializer.read(ObjectType.class, response);
                                        Log.d("response", response);
                                        InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
                                        RSSHelper.stream = stream;
                                        try {
                                            RSSHelper.RSSParser();
                                        } catch (IOException | XmlPullParserException e) {
                                            e.printStackTrace();
                                        }
                                        //ProcessInBackground ProcessInBackground = new ProcessInBackground();
                                        // ProcessInBackground.rss_feed_url = rss_feed_url;
                                        // ProcessInBackground.execute();
                                        //ProcessInBackground.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        //int news_item_count = Integer.parseInt(result.getString("news_item_count"));
                                        //

                                        String[][] NewsArray = new String[100][3];
                                        for (int i = 0; i < 10; i++) {
                                            //JSONObject news_item = news_list.getJSONObject(i);
                                            NewsArray[i][0] = RSSHelper.links.get(i);
                                            NewsArray[i][1] = RSSHelper.titles.get(i);
                                            NewsArray[i][2] = RSSHelper.image_urls.get(i);
                                            //Fill with default image if image is missing
                                            if(NewsArray[i][2].isEmpty()){
                                               // NewsArray[i][2] = "http://img.medscape.com/thumbnail_library/Medscape_News.jpg";
                                            }


                                        }

                                        ShowNewsAutoSlider(view, 10, NewsArray);
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // handle error response
                                    }
                                }
                        );
                        //request.setPriority(Request.Priority.HIGH);
                        VolleyController.getInstance(getContext()).addToRequestQueue(request);
                        ////



                        break;
                    default:
                        //ShowMessage("Something went wrong");
                        Snackbar.make(view, R.string.SomethingWentWrong, Snackbar.LENGTH_SHORT).show();
                        break;

                }

            }


            @Override
            public void onError(String result) {

                Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show();
            }

        });

    }



    private void makeRequest(final String url, final VolleyCallback callback) {
        final String current_language = LocaleHelper.getLanguage(getContext());

        CustomJSONObjectRequest request = new CustomJSONObjectRequest(
                //Request.Method.GET,
                Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.v("Response", response.toString());
                        try {
                            callback.onSuccess(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Log.v("Response", error.toString());
                        String err = null;
                        if (error instanceof com.android.volley.NoConnectionError) {
                            err = getResources().getString(R.string.net_not_available);
                        }
                        try {
                            callback.onError(err);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }) {

            /*            @Override
                        public Map<String, String> getHeaders() {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }*/
            /*
            Following method getBodyContentType specifies the content type by over ridding the
            default content type for the HTTP POST request.
            */
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("language", current_language);
                //hashMap.put("password", password);
                return hashMap;
                //return new HashMap<String, String>();
            }

        };
        request.setPriority(Request.Priority.HIGH);
        VolleyController.getInstance(getContext()).addToRequestQueue(request);

    }


}
