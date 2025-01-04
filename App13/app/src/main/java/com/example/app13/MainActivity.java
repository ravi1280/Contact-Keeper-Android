package com.example.app13;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app13.model.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


       Button btn1 = findViewById(R.id.button01);
       btn1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               EditText mobile = findViewById(R.id.editTextText01);
               EditText firstName  = findViewById(R.id.editTextText02);
               EditText lastName = findViewById(R.id.editTextText03);
               EditText city = findViewById(R.id.editTextText04);

              Contact contact = new Contact(
                      mobile.getText().toString(),
                      firstName.getText().toString(),
                      lastName.getText().toString(),
                      city.getText().toString()
              );

               Gson gson = new Gson();

               SharedPreferences sharedPreferences = getSharedPreferences("com.example.app13.contactList", Context.MODE_PRIVATE);
               String contactJson = sharedPreferences.getString("contactsJson",null);
               ArrayList<Contact> contactArrayList;

               if(contactJson==null){
                    contactArrayList = new ArrayList<>();
               }else {
                   Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
                   contactArrayList = gson.fromJson(contactJson, type);
               }

               contactArrayList.add(contact);
               sharedPreferences.edit().putString("contactsJson",gson.toJson(contactArrayList)).apply();

               mobile.setText("");
               firstName.setText("");
               lastName.setText("");
               city.setText("");

               mobile.requestFocus();
               Toast.makeText(MainActivity.this,"New Contact Saved !",Toast.LENGTH_LONG).show();

           }
       });
       Button btn2 = findViewById(R.id.button02);
       btn2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
//               Intent i = new Intent(MainActivity.this,ViewActivity.class);
//               startActivity(i);
               finish();
           }
       });
    }
}