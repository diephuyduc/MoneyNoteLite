package com.example.myappv1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappv1.R;
import com.example.myappv1.helpers.MNumberFormatMoney;
import com.example.myappv1.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> items;
    ItemClickViewDetail itemClickViewDetail;

    public ItemAdapter(ItemClickViewDetail listener) {
        this.itemClickViewDetail = listener;
    }


    public void setData(List<Item> itemList) {
        this.items = itemList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        if (item == null) {
            return;
        }

        holder.tvTitle.setText(item.getTitle());
        double money = item.getTotal();
        String moneys = MNumberFormatMoney.Money((long) money);
        holder.tvTotal.setText(moneys);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickViewDetail.onClick(item);
            }
        });
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickViewDetail.edit(item);
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickViewDetail.delete(item);
            }
        });
        holder.buttonTempPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickViewDetail.pay(item);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvTotal;
        private CardView cardView;
        private Button buttonEdit, buttonDelete, buttonTempPay;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_Title);
            tvTotal = itemView.findViewById(R.id.tv_total);
            cardView = itemView.findViewById(R.id.item);
            buttonEdit = itemView.findViewById(R.id.btn_edit);

            buttonDelete =itemView.findViewById(R.id.btn_item_delete);
            buttonTempPay = itemView.findViewById(R.id.temp_pay);
        }
    }

}
