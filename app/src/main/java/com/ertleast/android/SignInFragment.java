package com.ertleast.android;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }


    private TextView dontHaveAnAccount, sign_in_forgot_password, sign_in_or;
    private FrameLayout parentFrameLayout;
    private Button signInButton;
    private EditText sign_in_password, sign_in_email;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.tv_dont_have_account);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        signInButton = view.findViewById(R.id.sign_in_button);
        sign_in_password = view.findViewById(R.id.sign_in_password);
        sign_in_email = view.findViewById(R.id.sign_in_email);


        ButtonEnableDisable();


        sign_in_password.addTextChangedListener(new TextValidator(sign_in_password) {
            @Override
            public void validate(TextView textView, String text) {
                /* Validation code here */
/*                if ( text.isEmpty()){
                    Log.d("Error", "Password is blank");
                    sign_in_password.setError("Mandatory");
                }*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /* Don't care */
                ButtonEnableDisable();

            }
        });

        sign_in_email.addTextChangedListener(new TextValidator(sign_in_email) {
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


        return view;
    }

    private void ButtonEnableDisable() {
        String email = sign_in_email.getText().toString().trim();
        String password = sign_in_password.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            signInButton.setEnabled(false);
            signInButton.setBackgroundColor(getResources().getColor(R.color.colorDisabled));
        } else {
            signInButton.setEnabled(true);
            signInButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocaleHelper.setLocale(getActivity(), LocaleHelper.getLanguage(getActivity()));
        context = getActivity();


        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setFragment(new RegistrationIDFragment());
                setFragment(new SignUpFragment());
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //String url = "http://thaipharmaindex.com/bio_mobile/android/index.php";
                String url = "http://192.168.1.112/stock/signin.php";

                //Log.d("aniruddha","onClick");
                makeRequest(url, sign_in_email.getText().toString().trim(), sign_in_password.getText().toString(), new VolleyCallback() {

                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {


                        String server_response_code = result.getString("status");
                        //always pass
                        //server_response_code = "0";
                        Log.d("aniruddha", server_response_code);
                        switch (server_response_code) {
                            case "0":
                                Log.d("aniruddha", "SignInSuccessful");
                                SharedPreferences prefs = context.getSharedPreferences("com.biogenics.data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();

                                editor.putString("mem_name", result.getString("mem_name"));
                                editor.putString("email", result.getString("email"));
                                editor.apply();

                                Toast.makeText(context, R.string.SignInSuccessful, Toast.LENGTH_LONG).show();

                                HandleSharedPreferences.persist(getActivity(), "logged_in", "true");

                                startActivity(new Intent(context, MainActivity.class));
                                break;
                            case "2":
                                ShowMessage("Incorrect Credentials");
                                //Snackbar.make(view, R.string.IncorrectCredentials, Snackbar.LENGTH_SHORT).show();
                                break;
                            case "4":
                                ShowMessage("Registration Approval is still Pending");
                                //Snackbar.make(view, "Registration Approval is still Pending", Snackbar.LENGTH_SHORT).show();
                                break;
                            case "5":
                                ShowMessage("User not registered");
                                //Snackbar.make(view, "Registration Approval is still Pending", Snackbar.LENGTH_SHORT).show();
                                break;
                            default:
                                ShowMessage("Something went wrong");
                                //Snackbar.make(view, R.string.SomethingWentWrong, Snackbar.LENGTH_SHORT).show();
                                break;

                        }

                    }

                    @Override
                    public void onError(String result) {
                        Log.d("aniruddha",result);
                        Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show();
                    }

                });

                //Log.d("aniruddha","test");
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
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

    private void makeRequest(final String url, final String email, final String password, final VolleyCallback callback) {

        CustomJSONObjectRequest request = new CustomJSONObjectRequest(
                //Request.Method.GET,
                Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("aniruddha", response.toString());
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
                hashMap.put("email", email);
                hashMap.put("password", password);
                return hashMap;
                //return new HashMap<String, String>();
            }

        };
        request.setPriority(Request.Priority.HIGH);
        VolleyController.getInstance(context).addToRequestQueue(request);

        }

}
