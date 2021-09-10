package com.example.myappv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.room.migration.Migration;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myappv1.View.HomeActivity;
import com.example.myappv1.helpers.DialogRegister;

import com.example.myappv1.helpers.PasswordDialog;

import java.util.concurrent.Executor;
import java.util.zip.Inflater;


public class LoginActivity extends AppCompatActivity {
    PasswordDialog passwordDialog;
    DialogRegister newPassworDilog;
    BiometricManager biometricManager;
    TextView tvBiometricLogin;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvBiometricLogin = findViewById(R.id.tv_login_biometric);
        linearLayout = findViewById(R.id.replace_layout);
        tvBiometricLogin.setVisibility(View.GONE);
        addView();
       // init();

    }
    void addView(){
        View view;
        if(MainActivity.presentPassword.equals("")){
            view = getLayoutInflater().inflate(R.layout.newpassword_layout, null, false);
            tvBiometricLogin.setVisibility(View.GONE);
        }
        else{
            view = getLayoutInflater().inflate(R.layout.enterpassword_layout, null, false);

        }
        init(view);
        linearLayout.addView(view);

    }
    private void init(View view){

        if(MainActivity.presentPassword.equals("") == true){
            registerPassword(view);
        }
        else{
            loginCheck(view);
            biometricManager = BiometricManager.from(this);
            if(biometricManager.canAuthenticate()==BiometricManager.BIOMETRIC_SUCCESS ){
                Executor executor = ContextCompat.getMainExecutor(this);
                BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {


                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        MainActivity.isLogin = true;
                        startActivity(intent);
                        finish();
                    }


                });

                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle(getResources().getString(R.string.title_login_biometric))
                        .setNegativeButtonText(getResources().getString(R.string.op_login_with_pin))
                        .build();
                biometricPrompt.authenticate(promptInfo);
                tvBiometricLogin.setVisibility(View.VISIBLE);
                tvBiometricLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        biometricPrompt.authenticate(promptInfo);
                    }
                });
            }

        }
    }

    private void loginCheck(View view) {

        Button btnLogin = view.findViewById(R.id.btn_login);
        EditText editText = view.findViewById(R.id.edit_enterpasword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.presentPassword.equals(editText.getText().toString().trim())) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    MainActivity.isLogin = true;
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_not_correct_password), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void registerPassword(View view){

        Button btnAccept = view.findViewById(R.id.btn_accept);
        EditText editText1 = view.findViewById(R.id.edit_enterpasword);
        EditText editText2 = view.findViewById(R.id.edit_re_enterpasword);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass1 = editText1.getText().toString().trim();
                String pass2 = editText2.getText().toString().trim();
                if(!pass1.equals(pass2) || (TextUtils.isEmpty(pass1)) &&TextUtils.isEmpty(pass2)){
                    //registerPassword(view);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_not_match_password), Toast.LENGTH_LONG).show();
                }
                else{


                    MainActivity.isLogin = true;
                    MainActivity.presentPassword = pass1;
                    SharedPreferences.Editor editor = getSharedPreferences("my", MODE_PRIVATE).edit();
                    editor.putString(MSetting.password,pass1).apply();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_create_success), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }





}