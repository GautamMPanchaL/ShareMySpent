package com.example.sharemyspent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.sharemyspent.Adapter.ParticipantAdapter;
import com.example.sharemyspent.Model.Users;
import com.example.sharemyspent.databinding.ActivityMainBinding;
import com.example.sharemyspent.databinding.ActivityMakeAgroupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MakeAGroup extends AppCompatActivity {
    ActivityMakeAgroupBinding binding;
    ArrayList<Users> list=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMakeAgroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();



        binding.Gname.setText(data);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        //may be error
        ParticipantAdapter adapter=new ParticipantAdapter(list,getBaseContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseContext());
        binding.chatRecylerView.setLayoutManager(layoutManager);
        binding.chatRecylerView.setAdapter(adapter);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);

                    users.setUserid(dataSnapshot.getKey());
//                    if(!dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())) {
//                        list.add(users);
//                    }
                    list.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String GROUPNAME=getIntent().getStringExtra("GROUPNAME");
        binding.Gname.setText(GROUPNAME);
//        FirebaseDatabase.getInstance().getReference().child("Groups").child(FirebaseAuth.getInstance().getUid())
//                .limitToLast(1)
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                               if(snapshot.hasChildren()){
//                                   for(DataSnapshot snapshot1:snapshot.getChildren()){
//                                       binding.Gname.setText(snapshot.child("userName").getValue().toString());
//                                   }
//                               }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });

        binding.backArrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MakeAGroup.this,GroupName.class);
                startActivity(intent);
            }
        });
      binding.clickbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent=new Intent(MakeAGroup.this,MainActivity.class);

              startActivity(intent);
          }
      });

    }
}