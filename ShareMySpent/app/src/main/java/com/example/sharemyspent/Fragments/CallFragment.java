package com.example.sharemyspent.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharemyspent.Adapter.GroupAdapter;
import com.example.sharemyspent.Model.GroupUsers;
import com.example.sharemyspent.Model.Users;
import com.example.sharemyspent.Model.groupIdModel;
import com.example.sharemyspent.R;
import com.example.sharemyspent.databinding.FragmentCallBinding;
import com.example.sharemyspent.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CallFragment extends Fragment {


    public CallFragment() {
        // Required empty public constructor
    }

    ArrayList<Users> list=new ArrayList<>();

    FirebaseDatabase database;
    FirebaseAuth auth;
    FragmentCallBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCallBinding.inflate(inflater, container, false);
         database=FirebaseDatabase.getInstance();
        GroupAdapter adapter=new GroupAdapter(list,getContext());
        binding.groupRecycler.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.groupRecycler.setLayoutManager(layoutManager);
        database.getReference().child("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("Group")

         .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                  for(DataSnapshot snapshot1:snapshot.getChildren()){
                     Users users=snapshot1.getValue(Users.class);
                      users.setUserid(snapshot1.getKey());
                      list.add(users);

                  }
                  adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}