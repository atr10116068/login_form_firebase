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
    TextInputLayout Tu, Tn, Tp, Tp2;

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
        Tu = (TextInputLayout) findViewById(R.id.Tu);
        Tn = (TextInputLayout) findViewById(R.id.Tn);
        Tp = (TextInputLayout) findViewById(R.id.Tp);
        Tp2 = (TextInputLayout) findViewById(R.id.Tp2);

        value = new Value();
        reff = FirebaseDatabase.getInstance().getReference().child("user");
    }

    public void regis(View view) {
        final String nama, password, password2, username;
        final int highscore = 0;

        nama = n.getText().toString().trim();
        password = p.getText().toString().trim();
        password2 = pp.getText().toString().trim();
        username = u.getText().toString().trim();

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(username).exists()) {
                    Tu.setError("Username Sudah terdaftar");
                } else {
                    if (username.length() != 0) {
                        Tu.setErrorEnabled(false);
                        if (nama.length() != 0) {
                            Tn.setErrorEnabled(false);
                            if (password.length() != 0) {
                                Tp.setErrorEnabled(false);
                                if (password2.length() != 0) {
                                    Tp2.setErrorEnabled(false);
                                    if (password.equals(password2)) {
                                        //upload firebase
                                        value.setNama(nama);
                                        value.setPassword(password);
                                        value.setUsername(username);
                                        value.setHighScore(highscore);

                                        //push
                                        reff.child(username).setValue(value);
                                        Toast.makeText(Register.this, "Success", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        Tp2.setErrorEnabled(false);
                                    } else {
                                        Tp2.setError("Password tidak sama");
                                    }
                                } else {
                                    Tp2.setError("Auth Kosong");
                                }
                            } else {
                                Tp.setError("Password Kosong");
                            }
                        } else {
                            Tn.setError("Nama Kosong");
                        }
                    } else {
                        Tu.setError("Username Kosong");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
