package com.example.sharemyspent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sharemyspent.Adapter.AmountAdapter;
import com.example.sharemyspent.Adapter.ChatAdapter;
import com.example.sharemyspent.Model.Users;
import com.example.sharemyspent.Model.amountModel;
import com.example.sharemyspent.Model.groupIdModel;
import com.example.sharemyspent.Model.messageModel;
import com.example.sharemyspent.databinding.ActivityChatDetailBinding;
import com.example.sharemyspent.databinding.ActivityGroupDetailBinding;
import com.example.sharemyspent.databinding.FragmentCallBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class GroupDetailActivity extends AppCompatActivity {

    ActivityGroupDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding=ActivityGroupDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database= FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        final String senderId=FirebaseAuth.getInstance().getUid();
        String recieverId=getIntent().getStringExtra("userId");
       final String userName=getIntent().getStringExtra("username");
        String profilePic=getIntent().getStringExtra("profilePic");
        String groupId=getIntent().getStringExtra("groupId");

        binding.GroupName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.john).into(binding.gprofileImage);
        binding.gbackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GroupDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        final ArrayList<amountModel> Models=new ArrayList<>();
        final AmountAdapter adapter=new AmountAdapter(Models,this);
        binding.gchatRecylerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.gchatRecylerView.setLayoutManager(layoutManager);

        database.getReference().child("amount data").child(groupId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Models.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            amountModel model=dataSnapshot.getValue(amountModel.class);
                            Models.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


       binding.receipt.setOnClickListener(new View.OnClickListener() {
           int buy=0;
           int contrtibute=0;
           @Override
           public void onClick(View view) {
               database.getReference().child("amount data").child(groupId).addValueEventListener(new ValueEventListener() {

                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       int buy=0;
                       int contrtibute=0;
                      for(DataSnapshot snapshot1: snapshot.getChildren()){
                          amountModel model=snapshot1.getValue(amountModel.class);
                          int num1=Integer.parseInt(model.getBuy());
                          int num2=Integer.parseInt(model.getContribute());
                          buy+=num1;
                          contrtibute+=num2;
                      }
                      amountModel amountmodel=new amountModel(userName,senderId,Integer.toString(buy),Integer.toString(contrtibute));
                      amountmodel.setTimestamp(new Date().getTime());

                      database.getReference().child("receipt").child(groupId).push().setValue(amountmodel);
                       Toast.makeText(GroupDetailActivity.this, buy +" "+contrtibute, Toast.LENGTH_SHORT).show();


                      binding.receipt.setEnabled(false);
                      binding.receipt.setClickable(false);
                       binding.payAndBuy.setEnabled(false);
                       binding.payAndBuy.setClickable(false);

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });



           }
       });
        binding.payAndBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etdescribe.getText().toString().isEmpty()){
                    binding.etdescribe.setError("Enter description first!");
                    return;
                }
                if(binding.etbuy.getText().toString().isEmpty()){
                    binding.etbuy.setError("Enter buy cost first!");
                    return;
                }
                if(binding.etContri.getText().toString().isEmpty()){
                    binding.etContri.setError("Enter your contribution first!");
                    return;
                }


                amountModel model=new amountModel(binding.etdescribe.getText().toString(),FirebaseAuth.getInstance().getUid(), binding.etbuy.getText().toString(),binding.etContri.getText().toString());
                model.setTimestamp(new Date().getTime());
                //use to write the name
//                Users users=new Users(FirebaseAuth.getInstance().getUid());
//                model.setName(users.getUserName());


                database.getReference().child("amount data").child(groupId).push().setValue(model);

                binding.etContri.setText("");
                binding.etbuy.setText("");
                binding.etdescribe.setText("");
                Toast.makeText(GroupDetailActivity.this, "uploaded", Toast.LENGTH_SHORT).show();
            }
        });

    }
}