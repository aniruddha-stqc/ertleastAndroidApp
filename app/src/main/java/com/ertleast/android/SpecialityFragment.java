package com.ertleast.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ertleast.android.speciality.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SpecialityFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //String uri = "https://bit.ly/2tslluf";;
        View view = inflater.inflate(R.layout.activity_speciality, container, false);

        //FetchSpecialityUpdate(view);

        return view;
    }

    private void ShowSpecialityTabs(View view, int speciality_item_count, String[][] specialityArray) {
        //setContentView(R.layout.activity_speciality);
        //view.setTitle(R.string.speciality);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager(), speciality_item_count, specialityArray);
        final ViewPager viewPager = view.findViewById(R.id.view_pager);
        //setupViewPager(viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        /*FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Under Construction", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        tabs.setTabTextColors(ContextCompat.getColorStateList(getActivity(), R.color.colorWhite));
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        tabs.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryLight));
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
                            SpecialityArray[i][1] = speciality_item.getString("speciality_name_eng");
                            SpecialityArray[i][2] = speciality_item.getString("speciality_h_image");
                        }

                        //ShowHorizontalScroll(view, speciality_item_count, SpecialityArray);

                        ShowSpecialityTabs(view, speciality_item_count, SpecialityArray);
                       // Log.d("H", "test");
                        //ShowMessage("User not registered");
                        //Snackbar.make(view, "Registration Approval is still Pending", Snackbar.LENGTH_SHORT).show();
                        break;
                    default:
                        //ShowMessage("Something went wrong");
                        //Snackbar.make(view, R.string.SomethingWentWrong, Snackbar.LENGTH_SHORT).show();
                        break;

                }

            }

            @Override
            public void onError(String result) {

                //Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show();
            }

        });

    }

    private void makeRequest(final String url, final VolleyCallback callback) {

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
                //hashMap.put("email", email);
                //hashMap.put("password", password);
                return hashMap;
                //return new HashMap<String, String>();
            }

        };
        request.setPriority(Request.Priority.HIGH);
        VolleyController.getInstance(getContext()).addToRequestQueue(request);

    }
}
