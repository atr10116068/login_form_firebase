package com.example.wakhid.appfirebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reff;
    FirebaseDatabase database;

    EditText vusername,vpassword;
    TextInputLayout Tu,Tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vusername = (EditText) findViewById(R.id.ETusername);
        vpassword = (EditText) findViewById(R.id.ETpassword);
        Tu = (TextInputLayout) findViewById(R.id.Lusername);
        Tp = (TextInputLayout) findViewById(R.id.Lpassword);

        //firebase
        database = FirebaseDatabase.getInstance();
        reff = database.getReference("user");
    }

    public void msk(View view) {
        login(vusername.getText().toString().trim(),
                vpassword.getText().toString().trim());
    }

    private void login(final String u, final String p) {
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(u).exists()){
                    Log.d("fb","child username ada");
                    if(!u.isEmpty()){
                        Tu.setErrorEnabled(false);
                        Log.d("fb","child username tidak kosong");
                        Value login = dataSnapshot.child(u).getValue(Value.class);
                        Log.d("fb","add ke value");
                        if (login.getPassword().equals(p)){
                            Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_LONG).show();
                            Tp.setErrorEnabled(false);
                            Intent intent = new Intent(getApplicationContext(),Game.class);
                            intent.putExtra("username",u);
                            startActivity(intent);
                        }else{
                            Tp.setError("password salah");
                        }
                    }
                }else{
                    Tu.setError("username tidak terdaftar");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void regis(View view) {
        Intent intent = new Intent(MainActivity.this,Register.class);
        startActivity(intent);
    }
}
