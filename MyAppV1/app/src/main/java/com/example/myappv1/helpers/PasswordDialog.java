package com.example.myappv1.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myappv1.MainActivity;
import com.example.myappv1.R;

public class PasswordDialog {
    Context context;
    LoginClick loginClick;
    public boolean result = false;
    public PasswordDialog(Context t, LoginClick listener){
        context = t;
        loginClick = listener;
//        activity1 = a1;
//        activity2 = a2;
    }
    public boolean showDialog(Boolean cancel){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.enterpassword_layout);
        Window window = dialog.getWindow();
        if(window ==null){
            return false;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();

        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);
        dialog.setCancelable(cancel);
        EditText password = dialog.findViewById(R.id.edit_enterpasword);
        Button button = dialog.findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClick.login(password.getText().toString().trim());
                dialog.dismiss();
            }
        });
        dialog.show();
        return result;
    }
}
