package com.ertleast.android;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAccount;
    private FrameLayout parentFrameLayout;
    private Button nextbutton;
    private EditText sign_up_mobile, sign_up_full_name, email_id, sign_up_password, account_info_confirm_password;
    private Context context;
    private EditText registration_number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_up_accountinfo, container, false);
        context = getActivity();
        alreadyHaveAccount = view.findViewById(R.id.tv_already_have_account);

        registration_number = view.findViewById(R.id.registration_number);
        sign_up_full_name = view.findViewById(R.id.sign_up_full_name);
        email_id = view.findViewById(R.id.email_id);
        sign_up_password = view.findViewById(R.id.sign_up_password);
        sign_up_mobile = view.findViewById(R.id.sign_up_mobile);
        account_info_confirm_password = view.findViewById(R.id.account_info_confirm_password);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        nextbutton = view.findViewById(R.id.account_info_next_button);

        ButtonEnableDisable();

        sign_up_full_name.addTextChangedListener(new TextValidator(sign_up_full_name) {
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

        sign_up_mobile.addTextChangedListener(new TextValidator(sign_up_mobile) {
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

        email_id.addTextChangedListener(new TextValidator(email_id) {
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

        sign_up_password.addTextChangedListener(new TextValidator(sign_up_password) {
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

        account_info_confirm_password.addTextChangedListener(new TextValidator(account_info_confirm_password) {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = context.getSharedPreferences("com.biogenics.data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("registration_num", registration_number.getText().toString().trim());

                editor.putString("email_id", email_id.getText().toString().trim());
                editor.putString("sign_up_full_name", sign_up_full_name.getText().toString().trim());
                editor.putString("sign_up_password", sign_up_password.getText().toString());
                editor.putString("sign_up_mobile", sign_up_mobile.getText().toString().trim());

                editor.apply();

                setFragment(new ProfessionFragment());
                //Toast.makeText(getActivity(), "Sign Up Successful. Login to continue.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ButtonEnableDisable() {
        String full_name = sign_up_full_name.getText().toString().trim();
        String email = email_id.getText().toString().trim();
        String password = sign_up_password.getText().toString();
        String confirm_password = account_info_confirm_password.getText().toString();
        String mobile = sign_up_mobile.getText().toString().trim();


        if (mobile.isEmpty() || full_name.isEmpty() || confirm_password.isEmpty() || email.isEmpty() || password.isEmpty()) {
            nextbutton.setEnabled(false);
            nextbutton.setBackgroundColor(getResources().getColor(R.color.colorDisabled));
        } else {
            nextbutton.setEnabled(true);
            nextbutton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

}
