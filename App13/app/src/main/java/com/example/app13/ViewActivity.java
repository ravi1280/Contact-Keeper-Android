package com.example.app13;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app13.model.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Find the ImageView by its ID
        ImageView myImageView = findViewById(R.id.addView);

        // Set an OnClickListener
        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your desired action here
//                Toast.makeText(ViewActivity.this, "Image clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContact();
    }
    private void loadContact() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.app13.contactList", Context.MODE_PRIVATE);
        String contactJson = sharedPreferences.getString("contactsJson",null);

        if(contactJson==null){
//            Toast.makeText(this,"Contact List Not Founded",Toast.LENGTH_LONG).show();
            Intent i = new Intent(ViewActivity.this,MainActivity.class);
            startActivity(i);

        }else {
            Gson gson= new Gson();
            Type type = new TypeToken<ArrayList<Contact>>(){}.getType();
            ArrayList<Contact> contactArrayList = gson.fromJson(contactJson,type);

            RecyclerView recyclerView = findViewById(R.id.recycleView01);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.setAdapter(new contactAdapter(contactArrayList));

        }
    }
}


class contactAdapter extends RecyclerView.Adapter<contactAdapter.contactViewHolder>{

    static class contactViewHolder extends RecyclerView.ViewHolder{

        TextView textViewLetter;
        TextView textViewName;
        TextView textViewMobileCity;
        Button call;

        public contactViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLetter = itemView.findViewById(R.id.textView01);
            textViewName = itemView.findViewById(R.id.textView02);
            textViewMobileCity = itemView.findViewById(R.id.textView03);
            call = itemView.findViewById(R.id.callButton);
        }
    }

    //instance Variable
    ArrayList<Contact> contactArrayList ;

    public contactAdapter(ArrayList<Contact> contactArrayList) {
        this.contactArrayList = contactArrayList;
    }

    @NonNull
    @Override
    public contactAdapter.contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
       View contactView =layoutInflater.inflate(R.layout.contact_item,parent,false);
      contactViewHolder contactViewHolder = new contactViewHolder(contactView);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull contactAdapter.contactViewHolder holder, int position) {
        Contact contact = contactArrayList.get(position);
        holder.textViewLetter.setText(String.valueOf(contact.getFirstName().charAt(0)));
        holder.textViewName.setText(contact.getFirstName()+" "+contact.getLastName());
        holder.textViewMobileCity.setText(contact.getMobile()+" ("+contact.getCity()+") ");

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+contact.getMobile()));
                view.getContext().startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }
}

