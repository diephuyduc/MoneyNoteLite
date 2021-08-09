package com.example.myappv1.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myappv1.MSetting;
import com.example.myappv1.MainActivity;
import com.example.myappv1.R;
import com.example.myappv1.database.DetailDatabase;
import com.example.myappv1.database.ItemDatabase;
import com.example.myappv1.helpers.DialogRegister;
import com.example.myappv1.helpers.LoginClick;
import com.example.myappv1.helpers.PasswordDialog;
import com.example.myappv1.helpers.RegisterButton;


public class HomeFragment extends Fragment {
    private CardView changePassword, exitApp, deleteData;


    public HomeFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);
       changePassword = view.findViewById(R.id.card_view_changepass);
       exitApp = view.findViewById(R.id.card_view_exit);
       deleteData = view.findViewById(R.id.card_view_delete_data);
       changePassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               changePassword();
           }
       });
       exitApp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               exitApp();
           }
       });
       deleteData.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               deleteData();
           }
       });
        return view;
    }

    private void deleteData() {
        PasswordDialog passwordDialog = new PasswordDialog(getContext(), new LoginClick() {
            @Override
            public void login(String pass) {
                if(MainActivity.presentPassword.equals(pass)){
                  AlertDialog.Builder alog =  new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getString(R.string.w_delete_data))
                            .setMessage(getResources().getString(R.string.w_msg_delete_data))
                            .setNegativeButton(getResources().getString(R.string.op_yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ItemDatabase.getInstance(getContext()).itemDAO().deleteAll();
                                    DetailDatabase.getInstance(getContext()).detailDAO().deleteAll();
                                    Toast.makeText(getContext(), getResources().getString(R.string.msg_delete_success), Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setPositiveButton(getResources().getString(R.string.op_no), null);
                  alog.create().show();

                }
                else {
                    deleteData();
                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_not_correct_password), Toast.LENGTH_LONG).show();
                }

            }
        });

        passwordDialog.showDialog(true);
    }

    private void exitApp() {
        MainActivity.isLogin= false;
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        getActivity().finish();
        System.exit(0);
    }

    private void changePassword() {
        PasswordDialog passwordDialog = new PasswordDialog(getContext(), new LoginClick() {
            @Override
            public void login(String pass) {
                if(MainActivity.presentPassword.equals(pass)){
                    changePasswordForm();

                }
                else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_not_correct_password), Toast.LENGTH_LONG).show();
                    changePassword();
                }
            }
        });
        passwordDialog.showDialog(true);
    }
    private void changePasswordForm(){
        DialogRegister dialogRegister = new DialogRegister(getActivity(), new RegisterButton() {
            @Override
            public void onRegisterClick(String pass1, String pass2) {
                if(!pass1.equals(pass2)){
                    changePasswordForm();
                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_not_match_password), Toast.LENGTH_LONG).show();
                }
                else{

                    MainActivity.presentPassword = pass1;
                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_update_success), Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("my", MODE_PRIVATE).edit();
                    editor.putString(MSetting.password,pass1).apply();

                }
            }
        });
        dialogRegister.showDialog(true);
    }

}