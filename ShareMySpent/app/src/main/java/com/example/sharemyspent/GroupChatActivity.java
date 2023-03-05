package com.example.sharemyspent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sharemyspent.Adapter.ChatAdapter;
import com.example.sharemyspent.Model.messageModel;
import com.example.sharemyspent.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(GroupChatActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final ArrayList<messageModel>messageModels=new ArrayList<>();

        final String senderId=FirebaseAuth.getInstance().getUid();
        binding.UserName.setText("Friends Group");

        final ChatAdapter adapter=new ChatAdapter(messageModels,this);
        binding.chatRecylerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecylerView.setLayoutManager(layoutManager);

        database.getReference().child("Group Chat")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                messageModels.clear();
                                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    messageModel model=dataSnapshot.getValue(messageModel.class);
                                    messageModels.add(model);
                                }
                                adapter.notifyDataSetChanged();
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
                final String message=binding.chatTyping.getText().toString();
                final messageModel model=new messageModel(senderId,message);
                model.setTimestamp(new Date().getTime());

                binding.chatTyping.setText("");

                database.getReference().child("Group Chat")
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
            }
        });
    }
}