package com.example.sharemyspent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sharemyspent.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Logout Account");
        progressDialog.setMessage("Logging out your account");
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
//        binding.ViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
//        binding.tabLayout.setupWithViewPager(binding.ViewPager);
        viewPager=findViewById(R.id.ViewPager);
        tabLayout=findViewById(R.id.Tablayout);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.Settings:
               Intent intent2=new Intent(MainActivity.this,SettingActivity.class);
               startActivity(intent2);
                break;
            case R.id.logout:

                auth.signOut();

                Intent intent=new Intent(MainActivity.this,signinActivity.class);
                startActivity(intent);
                break;
            case R.id.groupChat:

                Intent intent1=new Intent(MainActivity.this,GroupChatActivity.class);
                startActivity(intent1);
                break;
            case R.id.makeGroup:
                Intent intent3=new Intent(MainActivity.this,GroupName.class);
                startActivity(intent3);
        }
        return  true;

    }
}