package org.pytorch.demo.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.pytorch.demo.MainActivity;
import org.pytorch.demo.models.ImageDeleteRequest;
import org.pytorch.demo.models.RoomResponse;
import org.pytorch.demo.ui.login.ApiClient;
import org.pytorch.demo.ui.login.Login;
import org.pytorch.demo.R;
import org.pytorch.demo.ui.register.Register;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


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
    ConstraintLayout logout, pytorch, about;
    CircleImageView imgAvatar;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle  savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        btn_Login = view.findViewById(R.id.btn_Login);
        btn_Register = view.findViewById(R.id.btnRegister);
        txtName = view.findViewById(R.id.textViewUserNameProfile);
        logout = view.findViewById(R.id.LogOut);
        imgAvatar = view.findViewById(R.id.imageAvatar);
        pytorch = view.findViewById(R.id.py_torch);
        about = view.findViewById(R.id.about_us);

        about.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.dialog_about_us);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();
                    }
                }
        );
        pytorch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_pytorch);
                WebView webView = dialog.findViewById(R.id.webViewPyTorch);
                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.setWebChromeClient(new WebChromeClient());
                webView.loadUrl("https://pytorch.org/mobile/home/");
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });
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
            txtName.setText("Chào mừng bạn "+name);
            btn_Login.setVisibility(View.GONE);
            btn_Register.setVisibility(View.GONE);
        }
        Glide.with(this).load("https://picsum.photos/200/300").into(imgAvatar);
        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                        builder.setTitle("ĐĂNG XUẤT");
                        builder.setMessage("Bạn muốn thoát chứ?");

                        builder.setPositiveButton("ĐĂNG XUẤT", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", getActivity().MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                SharedPreferences user = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
                                SharedPreferences.Editor edit = user.edit();
                                edit.clear();
                                edit.apply();
                                txtName.setText("Hi @username!");
                                btn_Login.setVisibility(View.VISIBLE);
                                btn_Register.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                                //startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                        });

                        builder.setNegativeButton("QUAY LẠI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                }
        );
    }

}