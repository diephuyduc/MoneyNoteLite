package com.example.myappv1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static String presentPassword = "";
    public static Boolean isLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogin = false;
        getSharedPreferences();


    }


    public void getSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("my", MODE_PRIVATE);
        String pass = prefs.getString(MSetting.password, "");

        if (pass.equals("") != true){

            presentPassword = pass;

    }

    Intent intent = new Intent(MainActivity.this, LoginActivity.class);

    startActivity(intent);

    finish();

}
}