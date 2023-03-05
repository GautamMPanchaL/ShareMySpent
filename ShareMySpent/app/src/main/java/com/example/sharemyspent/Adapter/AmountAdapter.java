package com.example.sharemyspent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemyspent.Model.amountModel;
import com.example.sharemyspent.Model.messageModel;
import com.example.sharemyspent.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AmountAdapter extends RecyclerView.Adapter{

    ArrayList<amountModel> amountModels;
    Context context;
    String recId;
    int senderViewType=1;
    int receiverViewType=2;

    public AmountAdapter(ArrayList<amountModel> amountModels, Context context) {
        this.amountModels = amountModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==senderViewType){
            View view= LayoutInflater.from(context).inflate(R.layout.sender,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view= LayoutInflater.from(context).inflate(R.layout.reciever,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(amountModels.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
            return senderViewType;
        }
        else{
            return receiverViewType;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       amountModel model=amountModels.get(position);
        if(holder.getClass()== AmountAdapter.SenderViewHolder.class) {
            ((SenderViewHolder) holder).sdescription.setText(model.getDescription());
            ((SenderViewHolder) holder).sbuy.setText(model.getBuy());
            ((SenderViewHolder) holder).scontribute.setText(model.getContribute());
//            FirebaseDatabase.getInstance().getReference().child("Users").child(model.getUid()).
//                    addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });

        }
        else{
            ((ReceiverViewHolder)holder).rdescription.setText(model.getDescription());
            ((ReceiverViewHolder)holder).rbuy.setText(model.getBuy());
            ((ReceiverViewHolder)holder).rcontribute.setText(model.getContribute());

        }
    }

    @Override
    public int getItemCount() {return amountModels.size();}
    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView rdescription,rbuy,rcontribute;
        public ReceiverViewHolder(@NonNull View itemView) {

            super(itemView);
            rdescription=itemView.findViewById(R.id.textView2);
            rbuy=itemView.findViewById(R.id.textView4);
            rcontribute=itemView.findViewById(R.id.textView6);

        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView sdescription,sbuy,scontribute;

        public SenderViewHolder(@NonNull View itemView) {

            super(itemView);
            sdescription=itemView.findViewById(R.id.textView2);
            sbuy=itemView.findViewById(R.id.textView4);
            scontribute=itemView.findViewById(R.id.textView6);
        }
    }
}
