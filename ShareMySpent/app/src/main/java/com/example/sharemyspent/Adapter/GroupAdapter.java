package com.example.sharemyspent.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemyspent.GroupDetailActivity;
import com.example.sharemyspent.Model.GroupUsers;
import com.example.sharemyspent.Model.Users;
import com.example.sharemyspent.Model.groupIdModel;
import com.example.sharemyspent.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder>{

    ArrayList<Users> list;

    Context context;

    public GroupAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_group_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     Users user=list.get(position);

        //Picasso.get().load(user.)
     holder.groupname.setText(user.getGroupName());

       Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.john).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, GroupDetailActivity.class);
                intent.putExtra("userId",user.getUserid());
                intent.putExtra("profilePic",user.getProfilePic());
                intent.putExtra("username",user.getGroupName());
                intent.putExtra("groupId",user.getGroupId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView groupname,status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.group_profile_image);
            groupname=itemView.findViewById(R.id.group_name);
            status=itemView.findViewById(R.id.group_status);

        }
    }
}
