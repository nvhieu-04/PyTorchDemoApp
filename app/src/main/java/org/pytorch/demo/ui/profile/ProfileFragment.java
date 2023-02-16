package org.pytorch.demo.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.pytorch.demo.ui.login.Login;
import org.pytorch.demo.R;
import org.pytorch.demo.ui.register.Register;


public class ProfileFragment extends Fragment {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    Button btn_Login, btn_Register;
    TextView txtName;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle  savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        btn_Login = view.findViewById(R.id.btn_Login);
        btn_Register = view.findViewById(R.id.btnRegister);
        txtName = view.findViewById(R.id.textViewUserNameProfile);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "Login", Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "Register", Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(getActivity(), Register.class);
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        if(!name.isEmpty())
        {
            txtName.setText("Hi "+name);
            btn_Login.setVisibility(View.GONE);
            btn_Register.setVisibility(View.GONE);
        }
    }

}