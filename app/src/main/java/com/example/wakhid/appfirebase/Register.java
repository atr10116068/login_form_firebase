package com.example.wakhid.appfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    EditText u, n, p, pp;
    TextInputLayout conf;
    DatabaseReference reff;
    Value value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        u = (EditText) findViewById(R.id.editTextUsername);
        n = (EditText) findViewById(R.id.editTextNama);
        p = (EditText) findViewById(R.id.editTextPassword);
        pp = (EditText) findViewById(R.id.editTextPassword2);
        conf = (TextInputLayout) findViewById(R.id.conf);

        value = new Value();
        reff = FirebaseDatabase.getInstance().getReference().child("user");
    }

    public void regis(View view) {
        String nama, password,password2, username;
        nama = n.getText().toString().trim();
        password = p.getText().toString().trim();
        password2 = pp.getText().toString().trim();
        username = u.getText().toString().trim();

        if(password.equals(password2)) {
            //upload firebase
            value.setNama(nama);
            value.setPassword(password);
            value.setUsername(username);

            //push
            reff.child(username).setValue(value);
            Toast.makeText(Register.this, "Success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            conf.setErrorEnabled(false);
        }else{
            conf.setError("Password tidak sama");
        }
    }
}
