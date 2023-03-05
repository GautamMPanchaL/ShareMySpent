package com.example.sharemyspent.Adapter;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemyspent.GroupName;
import com.example.sharemyspent.MakeAGroup;
import com.example.sharemyspent.Model.Users;
import com.example.sharemyspent.Model.groupIdModel;
import com.example.sharemyspent.Model.participantModel;
import com.example.sharemyspent.R;
import com.example.sharemyspent.SettingActivity;
import com.example.sharemyspent.databinding.SampleParticipantBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder>{


    ArrayList<Users> list;
    Context context;
    String groupname;
    String gid = "";
    String groupstatus;
    public ParticipantAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_participant,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Users user=list.get(position);
        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.john).into(holder.binding.profileImage);
        holder.binding.username.setText(user.getUserName());

        //use to working for button
        holder.binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                FirebaseDatabase.getInstance().getReference().child("Groups")

                        .orderByChild("timestamp")
                        .limitToLast(1)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChildren()){
                                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                        groupname=dataSnapshot.child("userName").getValue().toString();
                                        gid = dataSnapshot.getKey();
                                        Toast.makeText(context, gid, Toast.LENGTH_SHORT).show();

                                        Users users=new Users(groupname);
                                        users.setGroupId(gid);
                                        //groupIdModel idModel=new groupIdModel(groupname,gid);
                                        FirebaseDatabase database=FirebaseDatabase.getInstance();

                                        database.getReference().child("Users").child(user.getUserid())
                                                .child("Group").child(gid)

                                                .setValue(users);

                                        //this is for the testing purpose//
                                        participantModel user1;
                                        user1 = new participantModel(user.getUserName(),user.getUserid());

                                        database.getReference().child("Groups").child(gid).child("Partic")
                                                //.push().setValue(user1);
                                                .child(user.getUserid()).setValue(user1);



                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                //to disable click
//                holder.binding.addbtn.setEnabled(false);
//                holder.binding.addbtn.setClickable((false));





            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

         SampleParticipantBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding=SampleParticipantBinding.bind(itemView);
        }
    }
}
