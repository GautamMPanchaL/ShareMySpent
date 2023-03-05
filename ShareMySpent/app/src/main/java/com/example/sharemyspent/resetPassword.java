package com.example.sharemyspent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import com.example.sharemyspent.databinding.ActivityResetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword extends AppCompatActivity {
    ActivityResetPasswordBinding binding;
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.gotoSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(resetPassword.this,signinActivity.class);
                startActivity(intent);
            }
        });
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail=binding.email.getText().toString();
                if(mail.isEmpty()){
                    binding.email.setError("Enter your email first!");
                    return;
                }
                else{
                    forgetPassword(mail);
                }
            }
        });
    }
    private void forgetPassword(String email){
        FirebaseAuth auth=FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(resetPassword.this, "Please check your email", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(resetPassword.this,signinActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(resetPassword.this, "Error "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}