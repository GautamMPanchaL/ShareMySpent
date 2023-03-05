package com.example.sharemyspent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sharemyspent.Adapter.ParticipantAdapter;
import com.example.sharemyspent.Model.Users;
import com.example.sharemyspent.databinding.ActivityGroupNameBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class GroupName extends AppCompatActivity {
    ActivityGroupNameBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GroupName.this,MainActivity.class);
                startActivity(intent);
            }
        });

        binding.savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
//            public void onClick(View view) {
//                String status=binding.etStatus.getText().toString();
//                String  groupname=binding.etGroupname.getText().toString();
//
//                Users user =new Users(binding.etGroupname.getText().toString(),binding.etStatus.getText().toString());
//
//                database.getReference().child("Groups").child(FirebaseAuth.getInstance().getUid()).setValue(user);
//                Intent intent=new Intent(GroupName.this,MakeAGroup.class);
//                startActivity(intent);
//
//
//            }

            public void onClick(View view) {
                if(binding.etGroupname.getText().toString().isEmpty()){
                    binding.etGroupname.setError("Enter GroupName first!");
                    return;
                }
                if(binding.etStatus.getText().toString().isEmpty()){
                    binding.etStatus.setError("Enter Status first!");
                    return;
                }
                String status=binding.etStatus.getText().toString();
                String  groupname=binding.etGroupname.getText().toString();


                DatabaseReference newreference = database.getReference().child("Groups").push();
                String newref = String.valueOf(newreference);
                Users user =new Users(binding.etGroupname.getText().toString(),binding.etStatus.getText().toString());
                user.setTimestamp(new Date().getTime());

                newreference.setValue(user);

                Intent intent=new Intent(GroupName.this,MakeAGroup.class);
                intent.putExtra("GROUPNAME",groupname);
                //intent.putExtra("NEWREF",newref);
               // Toast.makeText(GroupName.this, newref, Toast.LENGTH_SHORT).show();
                startActivity(intent);


            }
        });

    }
}