package com.ertleast.android;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfessionFragment extends Fragment {


    public ProfessionFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAccount;
    private FrameLayout parentFrameLayout;
    private Button profession_info_next_button;
    private Spinner profession_spinner;
    private EditText workplace;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_up_professionalinfo, container, false);

        alreadyHaveAccount = view.findViewById(R.id.tv_already_have_account);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        profession_info_next_button = view.findViewById(R.id.profession_info_next_button);
        workplace = view.findViewById(R.id.workplace);
        profession_spinner = view.findViewById(R.id.profession_spinner);


        //create a list of items for the spinner.
        final String[] items = new String[]{
                getActivity().getString(R.string.select_prof),
                getActivity().getString(R.string.AlliedHealthcare),
                getActivity().getString(R.string.dentist),
                getActivity().getString(R.string.DispensaryAssistant),
                getActivity().getString(R.string.Doctor)};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        profession_spinner.setAdapter(adapter);

        profession_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Here you change your value or do whatever you want

                ButtonEnableDisable();
                //Log.d("test", "test");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Here comes when you didnt choose anything from your spinner logic
            }

        });

        workplace.addTextChangedListener(new TextValidator(workplace) {
            @Override
            public void validate(TextView textView, String text) {
                /* Validation code here */
/*                if ( text.isEmpty()){
                    Log.d("Error", "Email is blank");
                    sign_in_email.setError("Mandatory");
                }*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /* Don't care */
                ButtonEnableDisable();

            }


        });
        //ButtonEnableDisable();

        return view;
    }

    private void ButtonEnableDisable() {
        String profession_text = profession_spinner.getSelectedItem().toString();
        String workplace_text = workplace.getText().toString().trim();

        if (profession_text.equals(getActivity().getString(R.string.select_prof)) || workplace_text.isEmpty()) {
            profession_info_next_button.setEnabled(false);
            profession_info_next_button.setBackgroundColor(getResources().getColor(R.color.colorDisabled));
        } else {
            profession_info_next_button.setEnabled(true);
            profession_info_next_button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });

        profession_info_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                SharedPreferences prefs = context.getSharedPreferences("com.biogenics.data", Context.MODE_PRIVATE);
                String registration_text = prefs.getString("registration_num", "0000000000");
                String email_id_text = prefs.getString("email_id", "0000000000");
                String sign_up_full_name_text = prefs.getString("sign_up_full_name", "0000000000");
                String sign_up_password_text = prefs.getString("sign_up_password", "0000000000");
                String mobile_text = prefs.getString("sign_up_mobile", "0000000000");
                String workplace_text = workplace.getText().toString().trim();
                String profession_text = profession_spinner.getSelectedItem().toString();

                String url = "http://thaipharmaindex.com/bio_mobile/android/signup.php";
                //String url = "http://www.avoir.in/biogenics/bio_mobile/android/signup.php";


                makeRequest(url, registration_text, email_id_text, sign_up_full_name_text, mobile_text, sign_up_password_text, profession_text, workplace_text, new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {



                        String server_response_code = result.getString("status");

                        switch (server_response_code) {
                            /*case "0":
                                Toast.makeText(context, "Registration Already Done", Toast.LENGTH_LONG).show();
                                //startActivity(new Intent(context, MainActivity.class));
                                setFragment(new SignInFragment());
                                break;*/
                            case "4":
                                ShowMessage("New Registration Request Accepted. Pending Approval");
                                //Snackbar.make(view, "New Registration Request Accepted. Pending Approval", Snackbar.LENGTH_SHORT).show();
                                break;
                            case "5":
                                ShowMessage("Registration Request Already Pending for Approval");
                                //Snackbar.make(view, "Registration Request Already Pending for Approval", Snackbar.LENGTH_SHORT).show();
                                break;
                            case "6":
                                ShowMessage("Auto Approved. Proceed to Sign In.");
                                //Snackbar.make(view, "Auto Approved. Proceed to Sign In.", Snackbar.LENGTH_SHORT).show();
                                setFragment(new SignInFragment());
                                break;
                            case "7":
                                ShowMessage("Email Already registered. Proceed to Sign In.");
                                //Snackbar.make(view, "Already registered. Proceed to Sign In.", Snackbar.LENGTH_SHORT).show();
                                setFragment(new SignInFragment());
                                break;
                            default:
                                ShowMessage("Something went wrong");
                                //Snackbar.make(view, R.string.SomethingWentWrong, Snackbar.LENGTH_SHORT).show();
                                break;

                        }

                    }

                    @Override
                    public void onError(String result) {

                        ShowMessage("Something went wrong");
                    }

                });

                //setFragment(new SignInFragment());
                //Toast.makeText(getActivity(), "Sign Up Successful!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Server Response");
        builder.setMessage( message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //scannerView.stopCamera();
                //finish();
                // scannerView.resumeCameraPreview(ScanActivity.this);
            }
        });

        AlertDialog alert1 = builder.create();
        alert1.show();
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void makeRequest(final String url, final String registration_text, final String email_text, final String name_text, final String mobile_text, final String password_text,final String profession_text, final String workplace_text, final VolleyCallback callback) {

        CustomJSONObjectRequest request = new CustomJSONObjectRequest(
                //Request.Method.GET,
                Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Response", response.toString());
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
                hashMap.put("registration_num", registration_text);
                hashMap.put("email", email_text);
                hashMap.put("mob_num", mobile_text);
                hashMap.put("name", name_text);
                hashMap.put("password", password_text);
                hashMap.put("profession", profession_text);
                hashMap.put("workplace", workplace_text);

                return hashMap;
                //return new HashMap<String, String>();
            }

        };
        request.setPriority(Request.Priority.HIGH);
        VolleyController.getInstance(context).addToRequestQueue(request);

    }

}
