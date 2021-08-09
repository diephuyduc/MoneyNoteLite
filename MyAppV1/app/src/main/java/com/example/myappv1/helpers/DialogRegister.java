package com.example.myappv1.helpers;

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

import com.example.myappv1.R;

public class DialogRegister {
    Context context;
    RegisterButton registerButton;

    public boolean result = false;
    public DialogRegister(Context t, RegisterButton listener){
        context = t;
        registerButton = listener;
    }
    public void showDialog(Boolean cancel){

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.newpassword_layout);
        Window window = dialog.getWindow();
        if(window ==null){
            return ;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();

        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);
        dialog.setCancelable(cancel);
        EditText password = dialog.findViewById(R.id.edit_enterpasword);
        EditText repassword = dialog.findViewById(R.id.edit_re_enterpasword);
        Button button = dialog.findViewById(R.id.btn_accept);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerButton.onRegisterClick(password.getText().toString().trim(), repassword.getText().toString().trim());
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
