package com.example.myappv1.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myappv1.Adapter.ItemAdapter;
import com.example.myappv1.Adapter.ItemClickViewDetail;
import com.example.myappv1.R;
import com.example.myappv1.View.DetailActivity;
import com.example.myappv1.database.DetailDatabase;
import com.example.myappv1.database.ItemDatabase;
import com.example.myappv1.helpers.MoneyTextWatcher;
import com.example.myappv1.model.Item;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class LoanFragment extends Fragment {
    List<Item> i = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

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

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
    void bottomSheetPay(Item item){
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
                if(!TextUtils.isEmpty(editText.getText().toString().trim())){
                    double totalNow= item.getTotal();
                    double payM = Double.valueOf(editText.getText().toString().trim().replace("," , ""));
                    if(totalNow-payM>=0){
                        new  AlertDialog.Builder(getActivity())
                                .setTitle(getResources().getString(R.string.title_temp_pay))
                                .setMessage(getResources().getString(R.string.w_msg_temp_pay))

                                .setPositiveButton(getResources().getString(R.string.op_yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                    item.setTotal(totalNow-payM);
                                    ItemDatabase.getInstance(getActivity()).itemDAO().updateItem(item);
                                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_pay_success), Toast.LENGTH_SHORT).show();
                                    getData();
                                    bottomSheetDialog.dismiss();
                                }



                        }).setNegativeButton(getResources().getString(R.string.op_no), null).create().show();}
                    else{
                        Toast.makeText(getActivity(), getResources().getString(R.string.msg_valid_amount), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
    void bottomSheet(Item item) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_item_layout, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.getContext(),R.style.BottomSheetDialogTheme);
        EditText editText = view.findViewById(R.id.edt_item_title);
        Button button = view.findViewById(R.id.btn_item_save);
        if (item != null) {

            editText.setText(item.getTitle().toString().trim());

        }


        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item temp;
                if (item == null) {

                    if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.msg_create_success), Toast.LENGTH_LONG).show();
                        temp = new Item(editText.getText().toString().trim(), 0.0, true);

                        insertItem(temp);
                        editText.setText("");
                    }

                } else {
                    item.setTitle(editText.getText().toString().trim());
                    ItemDatabase.getInstance(getActivity()).itemDAO().updateItem(item);
                    getData();


                    Toast.makeText(getActivity(), getResources().getText(R.string.msg_update_success), Toast.LENGTH_LONG).show();
                }
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void insertItem(Item temp) {
        ItemDatabase.getInstance(getActivity()).itemDAO().insertItem(temp);
        getData();
    }
    private void getData(){
        i = ItemDatabase.getInstance(getActivity()).itemDAO().getAllItem();
        itemAdapter.setData(i);
    }

}