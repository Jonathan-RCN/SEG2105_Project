package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.security.AccessController.getContext;

public class SignUp extends AppCompatActivity {
    //Firebase Stuff
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.account_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accountType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    //Helper method for reaching welcome page
    private void openWelcomePage(){
        Intent openWelcome = new Intent(getApplicationContext(), WelcomeScreen.class);
        startActivity(openWelcome);
    }

    public void onClickConfirm(View v){
        //Retrieves the email and password
        EditText et1 = (EditText)findViewById(R.id.Firstname);
        EditText et2 = (EditText)findViewById(R.id.Lastname);
        String firstName = et1.getText().toString();
        String lastName = et2.getText().toString();

        if(firstName.isEmpty()){
            et1.setError("Enter your first name");
            et1.requestFocus();
            return;
        }

        if(lastName.isEmpty()){
            et2.setError("Enter your last name");
            et2.requestFocus();
            return;
        }
        mRef.child("Users").child(accountType).child(firstName).child(lastName).setValue(userID);
        Toast.makeText(SignUp.this, "Account added", Toast.LENGTH_SHORT).show();
        openWelcomePage();                                                                             //Goes to welcome screen after registering
    }
}

