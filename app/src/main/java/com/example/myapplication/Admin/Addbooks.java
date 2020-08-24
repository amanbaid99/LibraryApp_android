package com.example.myapplication.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.User.Bookdeets;
import com.example.myapplication.User.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class Addbooks extends AppCompatActivity {
    Button Addbooks,refresh;
    EditText titles,authors,ISBN,Imglnk;
    Bookdeets bookdeets;
    RadioGroup radiobtn;
    RadioButton Fiction,Nonficiton;
    String Category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbooks);


        radiobtn=(RadioGroup) findViewById(R.id.radiobtn);
        Fiction=(RadioButton) findViewById(R.id.ficiton);
        Nonficiton=(RadioButton) findViewById(R.id.nonficiton);
        Addbooks=(Button)findViewById(R.id.addbkbtn);
        refresh=(Button)findViewById(R.id.ClearFeild);
        titles = (EditText) findViewById(R.id.bknames);
        authors = (EditText) findViewById(R.id.anames);
        Imglnk=(EditText) findViewById(R.id.imglinks);
        ISBN=(EditText)findViewById(R.id.uid);
        bookdeets=new Bookdeets();
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        final String uidcheck=getIntent().getStringExtra("UID");
        radiobtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(Fiction.isChecked()){
                    Category="Fiction";


                }
                else if(Nonficiton.isChecked()){
                   Category="Non-Fiction";
                }
            }
        });
        Addbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Title = titles.getText().toString().trim();
                String Author = authors.getText().toString().trim();
                String isbn = ISBN.getText().toString().trim();
                String Img = Imglnk.getText().toString().trim();

                HashMap<String, Object> map = new HashMap<>();
                map.put("bookname", Title);
                map.put("author", Author);
                map.put("ISBN", isbn);
                map.put("image", Img);
                map.put("Category", Category);
                databaseReference.child("BookDB").child(isbn).setValue(map);
                Toast.makeText(getApplicationContext(), "Book Added succesfully", Toast.LENGTH_SHORT).show();
                Intent gb = new Intent(getApplicationContext(), AdminHome.class);
                startActivity(gb);
            }
        });
//

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titles.setText(null);
                authors.setText(null);
                ISBN.setText(null);
                Imglnk.setText(null);

            }
        });
    }


}
