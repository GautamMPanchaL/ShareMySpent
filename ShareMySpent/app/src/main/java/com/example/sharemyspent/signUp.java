package com.example.sharemyspent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sharemyspent.Model.Users;
import com.example.sharemyspent.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(signUp.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.epUsername.getText().toString().isEmpty()){
                    binding.epUsername.setError("Enter username first!");
                    return;
                }
                if(binding.epEmail.getText().toString().isEmpty()){
                    binding.epEmail.setError("Enter your email first!");
                    return;
                }
                if(binding.epPassword.getText().toString().isEmpty()) {
                    binding.epPassword.setError("Enter your password first!");
                    return;
                }


                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(binding.epEmail.getText().toString(), binding.epPassword.getText().toString())
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    //save in realtime database
                                    Users user =new Users(binding.epUsername.getText().toString(),binding.epEmail.getText().toString(),
                                            binding.epPassword.getText().toString());
                                    String id =task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(user);


                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(signUp.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(signUp.this,MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(signUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        binding.epHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(signUp.this,signinActivity.class);
                startActivity(intent);
            }
        });
    }

}