package com.example.myappv1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappv1.R;
import com.example.myappv1.helpers.MLongToDate;
import com.example.myappv1.helpers.MNumberFormatMoney;
import com.example.myappv1.model.Detail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    List<Detail> details;
    MDetailEditClick mDetailEditClick;


    public DetailAdapter(MDetailEditClick listener){
        this.mDetailEditClick = listener;
    }
    public void setDetails(List<Detail> detail){
        this.details = detail;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);

        return new DetailViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {

        Detail detail = details.get(position);
        String firstNote = MLongToDate.convert(detail.getFirstNote());
        String lastDate = MLongToDate.convert(detail.getLastUpdate());
        holder.tvTitle.setText(detail.getTitle());
        String moneys = MNumberFormatMoney.Money((long) detail.getMoney());

       // String moneys = String.format("%.02f", detail.getMoney());
        holder.tvMoney.setText(moneys);
        holder.firstDate.setText(firstNote);
        holder.lastDate.setText(lastDate);
        holder.btnEditDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDetailEditClick.onCLickEditing(detail);
            }
        });
        holder.btnDeleteDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDetailEditClick.onClickDelete(detail);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(details!=null){
            return details.size();
        }
        return 0;
    }






    public class  DetailViewHolder extends RecyclerView.ViewHolder{
        private Button btnEditDetail, btnDeleteDetail;
        private TextView tvTitle, tvMoney, firstDate, lastDate;


        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            btnEditDetail = itemView.findViewById(R.id.btn_edit_detail);
            tvTitle = itemView.findViewById(R.id.tv_detail_title);
            tvMoney = itemView.findViewById(R.id.tv_detail_money);
            firstDate = itemView.findViewById(R.id.firstNote);
            lastDate = itemView.findViewById(R.id.lastDate);
            btnDeleteDetail = itemView.findViewById(R.id.btn_delete_detail);


        }
    }
}

