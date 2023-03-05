package com.example.sharemyspent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemyspent.Model.amountModel;
import com.example.sharemyspent.R;

import java.util.ArrayList;

public class RecieptAdapter extends RecyclerView.Adapter<RecieptAdapter.ViewHolder> {
    ArrayList<amountModel> list;
    Context context;

    public ArrayList<amountModel> getList() {
        return list;
    }

    public void setList(ArrayList<amountModel> list) {
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecieptAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.receipt,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecieptAdapter.ViewHolder holder, int position) {
     amountModel model=list.get(position);
     holder.gName.setText(model.getDescription());
     holder.tBuy.setText(model.getBuy());
     holder.tContri.setText(model.getContribute());
     holder.text.setText("pay 100");
     holder.time.setText("12:00 pm");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView gName,tBuy,tContri,text,time;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gName=itemView.findViewById(R.id.GN);
            tBuy=itemView.findViewById(R.id.TB);
            tContri=itemView.findViewById(R.id.TP);
            text=itemView.findViewById(R.id.pay);
            time=itemView.findViewById(R.id.timeShower);
        }
    }
}
