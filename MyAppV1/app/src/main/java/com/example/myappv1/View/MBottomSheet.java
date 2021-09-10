//package com.example.myappv1.View;
//
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.Switch;
//import android.widget.Toast;
//
//import com.example.myappv1.R;
//import com.example.myappv1.database.ItemDatabase;
//import com.example.myappv1.model.Item;
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//
//public class MBottomSheet {
//    Context context;
//    Item item;
//
//    public MBottomSheet(Context context, Item item) {
//        this.context = context;
//        this.item = item;
//    }
//
//    private void showButtonFollowingSwitch(Switch s, Button b){
//        if(s.isChecked()){
//            b.setVisibility(View.VISIBLE);
//        }
//        else{
//            b.setVisibility(View.GONE);
//        }
//
//    }
//    void bottomSheet(Item item) {
//        View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_item_layout, null);
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetDialogTheme);
//        EditText editText = view.findViewById(R.id.edt_item_title);
//        Button datePicker = view.findViewById(R.id.date_picker);
//        Button timePicker = view.findViewById(R.id.time_picker);
//        Switch mySwitch = view.findViewById(R.id.switch_notify);
//
//
//        Button button = view.findViewById(R.id.btn_item_save);
//        if (item != null) {
//
//            editText.setText(item.getTitle().toString().trim());
//            if(!item.getTimeNotify().equals("") || item.getTimeNotify()!=null){
//                mySwitch.setChecked(true);
//            }
//            else{
//                mySwitch.setChecked(false);
//            }
//
//        }
//        showButtonFollowingSwitch(mySwitch, datePicker);
//        showButtonFollowingSwitch(mySwitch, timePicker);
//
//        bottomSheetDialog.setContentView(view);
//        bottomSheetDialog.show();
//        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    datePicker.setVisibility(View.VISIBLE);
//                    timePicker.setVisibility(View.VISIBLE);
//                }
//                else{
//                    datePicker.setVisibility(View.GONE);
//                    timePicker.setVisibility(View.GONE);
//                }
//            }
//        });
//        datePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePicker();
//            }
//        });
//        timePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimePicker();
//            }
//        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Item temp;
//                if (item == null) {
//
//                    if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
//                        Toast.makeText(getActivity(), getResources().getString(R.string.msg_create_success), Toast.LENGTH_LONG).show();
//                        temp = new Item(editText.getText().toString().trim(), 0.0, null);
//
//                        insertItem(temp);
//                        editText.setText("");
//                    }
//
//                } else {
//                    item.setTitle(editText.getText().toString().trim());
//                    ItemDatabase.getInstance(context).itemDAO().updateItem(item);
//                    getData();
//
//
//                    Toast.makeText(context, context.getResources().getText(R.string.msg_update_success), Toast.LENGTH_LONG).show();
//                }
//                bottomSheetDialog.dismiss();
//            }
//        });
//    }
//
//    private void showDatePicker() {
//    }
//}
