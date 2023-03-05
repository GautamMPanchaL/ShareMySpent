package com.example.sharemyspent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.sharemyspent.Adapter.ChatAdapter;
import com.example.sharemyspent.Model.messageModel;
import com.example.sharemyspent.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database= FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

       final String senderId=auth.getUid();
        String recieverId=getIntent().getStringExtra("userId");
        String userName=getIntent().getStringExtra("username");
        String profilePic=getIntent().getStringExtra("profilePic");

        binding.UserName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.john).into(binding.profileImage);

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chatDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        final ArrayList<messageModel> messageModels=new ArrayList<>();

        final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this,recieverId);

        binding.chatRecylerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecylerView.setLayoutManager(layoutManager);

        final String senderRoom=senderId+recieverId;
        final String receiverRoom=recieverId+senderId;

        database.getReference().child("chats")
                        .child(senderRoom)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        messageModels.clear();
                                        for(DataSnapshot snapshot1:snapshot.getChildren()){

                                            messageModel model=snapshot1.getValue(messageModel.class);
                                            //this line is use to delete message
                                            model.setMessageId(snapshot1.getKey());
                                            messageModels.add(model);
                                        }
                                        chatAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
        binding.sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(binding.chatTyping.getText().toString().isEmpty()){
                    binding.chatTyping.setError("Enter message!");
                    return;
                }
                String message=binding.chatTyping.getText().toString();
                final messageModel model=new messageModel(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.chatTyping.setText("");

                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .push()
                                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });
            }
        });
    }
}