package com.example.sharemyspent.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharemyspent.Adapter.AmountAdapter;
import com.example.sharemyspent.Model.GroupUsers;
import com.example.sharemyspent.Model.Users;
import com.example.sharemyspent.Model.amountModel;
import com.example.sharemyspent.databinding.FragmentStatusBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StatusFragment extends Fragment {
      FragmentStatusBinding binding;
    ArrayList<amountModel> list=new ArrayList<>();
    FirebaseDatabase database;

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentStatusBinding.inflate(inflater, container, false);
        database=FirebaseDatabase.getInstance();

        AmountAdapter adapter=new AmountAdapter(list,getContext());
        binding.groupRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.groupRecycler.setLayoutManager(layoutManager);
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("Group").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);
                    String id=users.getGroupId();
                     database.getReference().child("receipt")
                             .child(id).orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot2) {

                             for(DataSnapshot snapshot1:snapshot2.getChildren()){
                                 amountModel model=snapshot1.getValue(amountModel.class);
                                 model.setDescription(dataSnapshot.getKey());
                                 list.add(model);
                             }
                             adapter.notifyDataSetChanged();
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });
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