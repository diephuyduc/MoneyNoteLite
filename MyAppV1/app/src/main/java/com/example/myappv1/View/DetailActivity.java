package com.example.myappv1.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myappv1.Adapter.DetailAdapter;
import com.example.myappv1.Adapter.MDetailEditClick;
import com.example.myappv1.MainActivity;
import com.example.myappv1.R;
import com.example.myappv1.database.DetailDatabase;
import com.example.myappv1.database.ItemDatabase;
import com.example.myappv1.helpers.MNumberFormatMoney;
import com.example.myappv1.helpers.MoneyTextWatcher;
import com.example.myappv1.model.Detail;
import com.example.myappv1.model.Item;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DetailAdapter detailAdapter;
    private FloatingActionButton floatingActionButton;
    List<Detail> details;
    private int itemId;
    Date date;
    Item item_bunbdel;
    EditText money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("my_bundle");
         item_bunbdel = (Item) bundle.getSerializable("my_item");
        itemId = item_bunbdel.getId();

        details = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(MainActivity.isLogin!=true){
            Intent intentInt = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intentInt);
            finish();

        }
        recyclerView = findViewById(R.id.rv_detail);
        floatingActionButton = findViewById(R.id.btn_new);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditing(null);
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getBaseContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
        detailAdapter = new DetailAdapter(new MDetailEditClick() {
            @Override
            public void onCLickEditing(Detail detail) {
                openEditing(detail);
            }

            @Override
            public void onClickDelete(Detail detail) {
                deleteDetail(detail);
            }
        });

        getData();
        recyclerView.setAdapter(detailAdapter);
    }

    private void deleteDetail(Detail detail) {
        DetailDatabase.getInstance(getApplicationContext()).detailDAO().deleteDetail(detail);
        uploadTotal(detail.getMoney(), 0);
        getData();
    }

    private void getData() {

        details = DetailDatabase.getInstance(this).detailDAO().getDetailList(itemId);
        detailAdapter.setDetails(details);
    }

    //show dialog to edit detail of item
    private void openEditing(Detail detail) {
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        EditText title = view.findViewById(R.id.edt_title);
        money = view.findViewById(R.id.edt_money);
        Button save = view.findViewById(R.id.btn_bottom_add);
        money.addTextChangedListener(new MoneyTextWatcher(money));
        //if detail is null, it means that create a new detail
        if (detail != null) {

            title.setText(detail.getTitle());
            money.setText( MNumberFormatMoney.Money((long)detail.getMoney()));


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    date = new Date();
                    Long presentDate = date.getTime();

                    detail.setTitle(title.getText().toString().trim());
                    uploadTotal(detail.getMoney(),Double.valueOf(money.getText().toString().trim().replace(",","")));
                    detail.setMoney(Double.valueOf(money.getText().toString().trim().replace(",", "")));
                    detail.setLastUpdate(presentDate);
                    DetailDatabase.getInstance(getApplicationContext()).detailDAO().updateDetail(detail);

                    getData();
                    bottomSheetDialog.dismiss();
                }
            });
        } else {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    date = new Date();
                    Long presentDate = date.getTime();

                    if (!TextUtils.isEmpty(title.getText().toString().trim()) && !TextUtils.isEmpty(money.getText().toString().trim())) {
                        Detail d = new Detail(itemId, title.getText().toString().trim(),
                                Double.valueOf(money.getText().toString().trim().replace(",","")), false, presentDate, presentDate);
                        DetailDatabase.getInstance(getApplicationContext()).detailDAO().insertDetail(d);
                        getData();
                        uploadTotal(0, Double.valueOf(money.getText().toString().trim().replace(",", "")));
                        bottomSheetDialog.dismiss();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_invalid_info), Toast.LENGTH_SHORT).show();
                    }
                }

            });


        }


    }
   private void uploadTotal( double now, double update){

        double tempMoney = item_bunbdel.getTotal();
        if(tempMoney-now+update <0){
            item_bunbdel.setTotal(0);
        }
        else{
            item_bunbdel.setTotal(tempMoney-now + update);
        }

       ItemDatabase.getInstance(getApplicationContext()).itemDAO().updateItem(item_bunbdel);
    }

}

