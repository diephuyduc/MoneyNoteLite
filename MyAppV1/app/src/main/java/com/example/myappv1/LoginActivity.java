package com.example.myappv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.migration.Migration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myappv1.View.HomeActivity;
import com.example.myappv1.helpers.DialogRegister;
import com.example.myappv1.helpers.LoginClick;
import com.example.myappv1.helpers.PasswordDialog;
import com.example.myappv1.helpers.RegisterButton;

public class LoginActivity extends AppCompatActivity {
    PasswordDialog passwordDialog;
    DialogRegister newPassworDilog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }
    private void init(){
        if(MainActivity.presentPassword.equals("") == true){
            registerPassword();
        }
        else{
            loginCheck();
        }
    }

    private void loginCheck() {
        findViewById(R.id.layout_include_1).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_include_2).setVisibility(View.GONE);
        Button btnLogin = findViewById(R.id.layout_include_1).findViewById(R.id.btn_login);
        EditText editText = findViewById(R.id.layout_include_1).findViewById(R.id.edit_enterpasword);
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
                    loginCheck();
                }
            }
        });
    }

    private void registerPassword(){
        findViewById(R.id.layout_include_1).setVisibility(View.GONE);
        findViewById(R.id.layout_include_2).setVisibility(View.VISIBLE);
        Button btnAccept = findViewById(R.id.layout_include_2).findViewById(R.id.btn_accept);
        EditText editText1 = findViewById(R.id.layout_include_2).findViewById(R.id.edit_enterpasword);
        EditText editText2 = findViewById(R.id.layout_include_2).findViewById(R.id.edit_re_enterpasword);
        String pass1 = editText1.getText().toString().trim();
        String pass2 = editText2.getText().toString().trim();
        if(!pass1.equals(pass2)){
            registerPassword();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_not_match_password), Toast.LENGTH_LONG).show();
        }
        else{

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_create_success), Toast.LENGTH_LONG).show();
            MainActivity.isLogin = true;
            MainActivity.presentPassword = pass1;
            SharedPreferences.Editor editor = getSharedPreferences("my", MODE_PRIVATE).edit();
            editor.putString(MSetting.password,pass1).apply();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }





}