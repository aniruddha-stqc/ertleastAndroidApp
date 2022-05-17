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
public class RegistrationIDFragment extends Fragment {


    public RegistrationIDFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAccount;
    private FrameLayout parentFrameLayout;
    private Button registration_next_button;
    private EditText registration_number;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_up_registration, container, false);
        context = getActivity();
        alreadyHaveAccount = view.findViewById(R.id.tv_already_have_account);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        registration_next_button = view.findViewById(R.id.registration_next_button);
        registration_number = view.findViewById(R.id.registration_number);
        ButtonEnableDisable();


        registration_number.addTextChangedListener(new TextValidator(registration_number) {
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


        return view;
    }

    private void ButtonEnableDisable() {
        if (registration_number.getText().toString().trim().isEmpty()) {
            registration_next_button.setEnabled(false);
            registration_next_button.setBackgroundColor(getResources().getColor(R.color.colorDisabled));
        } else {
            registration_next_button.setEnabled(true);
            registration_next_button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
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

        registration_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = context.getSharedPreferences("com.biogenics.data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("registration_num", registration_number.getText().toString().trim());
                editor.apply();

                setFragment(new SignUpFragment());
                //Toast.makeText(getActivity(), "Sign Up Successful. Login to continue.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

}
