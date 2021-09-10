package com.example.myappv1.fragment;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myappv1.Adapter.ItemAdapter;
import com.example.myappv1.Adapter.ItemClickViewDetail;
import com.example.myappv1.R;
import com.example.myappv1.View.DetailActivity;
import com.example.myappv1.broadcast_receiver.MyBroadcastReceiver;
import com.example.myappv1.database.DetailDatabase;
import com.example.myappv1.database.ItemDatabase;
import com.example.myappv1.helpers.MoneyTextWatcher;
import com.example.myappv1.model.Item;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class LoanFragment extends Fragment {
    public static final String MY_TITLE = "msg_notification";
    String[] time = new String[2];
    List<Item> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    Calendar calendar = Calendar.getInstance();
    Switch mySwitch;

    public LoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_loan, container, false);
        recyclerView = view.findViewById(R.id.rv_loan);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet(null);
            }
        });
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animtion);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        itemAdapter = new ItemAdapter(new ItemClickViewDetail() {
            @Override
            public void onClick(Item item) {
                gotoDetail(item);
            }

            @Override
            public void edit(Item item) {
                bottomSheet(item);
            }

            @Override
            public void delete(Item item) {
                deleteItem(item);
            }

            @Override
            public void pay(Item item) {
                payItem(item);
            }


        });
        getData();
        recyclerView.setAdapter(itemAdapter);
        return view;
    }

    private void payItem(Item item) {
        bottomSheetPay(item);
    }

    private void deleteItem(Item item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Xóa mục này?");
        alertDialog.setMessage("Bạn sẽ không thể khôi phục mục đã xóa");
        alertDialog.setPositiveButton("Không", null);
        alertDialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelAlarm(item);
                ItemDatabase.getInstance(getActivity()).itemDAO().deleteItem(item);
                getData();
                dialogInterface.cancel();
                DetailDatabase.getInstance(getActivity()).detailDAO().deletedItemFather(item.getId());
                Toast.makeText(getContext(), "Đã xóa thành công", Toast.LENGTH_LONG).show();

            }
        });
        alertDialog.create().show();


    }

    private void gotoDetail(Item item) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("my_item", item);
        intent.putExtra("my_bundle", bundle);
        startActivity(intent);
    }



    void bottomSheetPay(Item item) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_item_layout, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.getContext(), R.style.BottomSheetDialogTheme);
        EditText editText = view.findViewById(R.id.edt_item_title);
        Button button = view.findViewById(R.id.btn_item_save);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.addTextChangedListener(new MoneyTextWatcher(editText));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                    double totalNow = item.getTotal();
                    double payM = Double.valueOf(editText.getText().toString().trim().replace(",", ""));
                    if (totalNow - payM >= 0) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle(getResources().getString(R.string.title_temp_pay))
                                .setMessage(getResources().getString(R.string.w_msg_temp_pay))

                                .setPositiveButton(getResources().getString(R.string.op_yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                        item.setTotal(totalNow - payM);
                                        ItemDatabase.getInstance(getActivity()).itemDAO().updateItem(item);
                                        Toast.makeText(getActivity(), getResources().getString(R.string.msg_pay_success), Toast.LENGTH_SHORT).show();
                                        getData();
                                        bottomSheetDialog.dismiss();
                                    }


                                }).setNegativeButton(getResources().getString(R.string.op_no), null).create().show();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.msg_valid_amount), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    private void showButtonFollowingSwitch(Switch s, Button b) {
        if (s.isChecked()) {
            b.setVisibility(View.VISIBLE);
        } else {
            b.setVisibility(View.GONE);
        }

    }

    void bottomSheet(Item item) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_item_layout, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.getContext(), R.style.BottomSheetDialogTheme);
        EditText editText = view.findViewById(R.id.edt_item_title);
        Button datePicker = view.findViewById(R.id.date_picker);
        Button timePicker = view.findViewById(R.id.time_picker);
        mySwitch = view.findViewById(R.id.switch_notify);


        Button button = view.findViewById(R.id.btn_item_save);
        if (item != null) {

            editText.setText(item.getTitle().toString().trim());
            if (item.getTimeNotify() != null) {
                mySwitch.setChecked(true);
                time = item.getTimeNotify().split(" ");
                datePicker.setText(time[1].toString());
                timePicker.setText(time[0].toString());
            } else {
                mySwitch.setChecked(false);
            }

        }
        showButtonFollowingSwitch(mySwitch, datePicker);
        showButtonFollowingSwitch(mySwitch, timePicker);

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    datePicker.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(View.VISIBLE);
                } else {
                    datePicker.setVisibility(View.GONE);
                    timePicker.setVisibility(View.GONE);
                }
            }
        });
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                month++;
                datePicker.setText(dayOfMonth + "/" + month + "/" + year);
                time[1] = dayOfMonth + "/" + month + "/" + year;
            }
        };
        datePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener, LocalDateTime.now().getYear(), (int) LocalDateTime.now().getMonth().getValue() - 1,
                        LocalDateTime.now().getDayOfMonth());
                datePickerDialog.show();
            }
        });
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                timePicker.setText(hourOfDay + ":" + minute);
                time[0] = hourOfDay + ":" + minute;
            }
        };
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener, LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), true);
                timePickerDialog.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item temp;
                if (item == null) {

                    if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.msg_create_success), Toast.LENGTH_LONG).show();
                        if (mySwitch.isChecked()) {
                            temp = new Item(editText.getText().toString().trim(), 0.0, time[0] + " " + time[1]);
                            //create new alarm for new item
                            setAlarm(temp);

                        } else {
                            temp = new Item(editText.getText().toString().trim(), 0.0, null);
                        }


                        insertItem(temp);
                        editText.setText("");
                    }

                } else {
                    item.setTitle(editText.getText().toString().trim());
                    cancelAlarm(item);
                    if (mySwitch.isChecked()) {
                        item.setTimeNotify(time[0] + " " + time[1]);
                        setAlarm(item);
                    } else {
                        item.setTimeNotify(null);
                    }

                    ItemDatabase.getInstance(getActivity()).itemDAO().updateItem(item);
                    ItemDatabase.getInstance(getActivity()).itemDAO().updateItem(item);


                    Toast.makeText(getActivity(), getResources().getText(R.string.msg_update_success), Toast.LENGTH_LONG).show();
                }
                getData();
                bottomSheetDialog.dismiss();
            }
        });
    }


    private void setAlarm(Item i) {
        Intent intent = new Intent(getActivity(), MyBroadcastReceiver.class);
        intent.putExtra(MY_TITLE, i.getTitle());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), i.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private boolean isSetUpAlarm(Item i) {
        boolean result = false;
        Intent intent = new Intent(getActivity(), MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), i.getId(), intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent == null) {
            return false;
        }
        return true;
    }

    private void cancelAlarm(Item i) {
        Intent intent = new Intent(getActivity(), MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), i.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    private void insertItem(Item temp) {
        ItemDatabase.getInstance(getActivity()).itemDAO().insertItem(temp);
        getData();
    }

    private void getData() {
        data = ItemDatabase.getInstance(getActivity()).itemDAO().getAllItem();
        Log.e("duc", data.toString() );
        itemAdapter.setData(data);
    }

}