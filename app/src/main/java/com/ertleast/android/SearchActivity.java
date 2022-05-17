package com.ertleast.android;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private TextView textView;
    private RecyclerView recyclerView;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_search);



        searchView = findViewById(R.id.search_view);
        textView = findViewById(R.id.textView_search);
        recyclerView = findViewById(R.id.recyclerView_search);

        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        final List<DrugSearchModel> list = new ArrayList<>();
        final Adapter adapter = new Adapter(list);
        recyclerView.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                String search_keyword = query.trim();
                if(!search_keyword.isEmpty()){
                    FetchSearchResult(list, search_keyword);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                list.clear();
                String search_keyword = newText.trim();
                if(!search_keyword.isEmpty()){
                    FetchSearchResult(list, search_keyword);
                }
                else{
                    String[][] Empty = new String[0][];
                    ShowDrugSearchScroll(list, 0, Empty);
                }

                return false;
            }
        });
    }

    private void makeRequest(final String url, final String search_keyword, final VolleyCallback callback) {
        final String current_language = LocaleHelper.getLanguage(this);

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
                        //Log.v("Response", error.toString());
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
                hashMap.put("search_keyword", search_keyword);
                hashMap.put("language", current_language);
                return hashMap;

            }

        };
        request.setPriority(Request.Priority.HIGH);
        VolleyController.getInstance(this).addToRequestQueue(request);

    }

    private void ShowDrugSearchScroll(List<DrugSearchModel> list, int drug_item_count, String[][] DrugSearchArray) {
        list.clear();





        for (int i = 0; i < drug_item_count; i++) {
            list.add(new DrugSearchModel(DrugSearchArray[i][0], DrugSearchArray[i][2], DrugSearchArray[i][1]));

        }

        //Sort Array
        Collections.sort(list, new Comparator() {

            public int compare(Object o1, Object o2) {

                String title1 = ((DrugSearchModel) o1).getProductTitle();
                String title2 = ((DrugSearchModel) o2).getProductTitle();
                int sComp = title1.compareTo(title2);

                if (sComp != 0) {
                    return sComp;
                }

                String type1 = ((DrugSearchModel) o1).getProductType();
                String type2 = ((DrugSearchModel) o2).getProductType();
                return type1.compareTo(type2);
            }});


        DrugSearchAdapter drugSearchAdapter = new DrugSearchAdapter(list);
        if (list.size() > 0) {
            textView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            //adapter.getFilter().filter(query);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(drugSearchAdapter);
        drugSearchAdapter.notifyDataSetChanged();
    }

    private void FetchSearchResult(final List<DrugSearchModel> list, final String search_keyword) {
        //String url = "http://www.avoir.in/biogenics/bio_mobile/android/search_drugs.php";
        String url = "http://thaipharmaindex.com/bio_mobile/android/search_drugs.php";

        makeRequest(url, search_keyword, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {


                String server_response_code = result.getString("status");
                int drug_search_item_count = Integer.parseInt(result.getString("drugs_item_count"));
                String[][] DrugSearchArray = new String[300][3];

                switch (server_response_code) {

                    case "0":
                        JSONArray drug_search_list = result.getJSONArray("drugs_list");

                        for (int i = 0; i < drug_search_item_count; i++) {
                            JSONObject speciality_item = drug_search_list.getJSONObject(i);
                            DrugSearchArray[i][0] = speciality_item.getString("drugs_id");
                            DrugSearchArray[i][1] = speciality_item.getString("drug_name");
                            DrugSearchArray[i][2] = speciality_item.getString("drug_category");
                        }

                        ShowDrugSearchScroll(list, drug_search_item_count, DrugSearchArray);
                        //Log.d("H", "test");
                        //ShowMessage("User not registered");
                        //Snackbar.make(view, "Registration Approval is still Pending", Snackbar.LENGTH_SHORT).show();
                        break;
                    case "1":
                        ShowDrugSearchScroll(list, drug_search_item_count, DrugSearchArray);

                    default:
                        //ShowMessage("Something went wrong");
                        //Snackbar.make(view, R.string.SomethingWentWrong, Snackbar.LENGTH_SHORT).show();
                        break;

                }

            }

            @Override
            public void onError(String result) {
                //Log.d("Search", "Error");
                //Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show();
            }

        });

    }

    class Adapter extends DrugSearchAdapter implements Filterable {

        public Adapter(List<DrugSearchModel> DrugSearchModelList) {
            super(DrugSearchModelList);
        }

        @Override
        public Filter getFilter() {
            return null;
        }
    }

}
