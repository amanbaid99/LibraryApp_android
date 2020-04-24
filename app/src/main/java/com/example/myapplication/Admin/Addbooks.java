package com.example.myapplication.Admin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.User.Bookdeets;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class Addbooks extends AppCompatActivity {
    Button Addbooks,refresh;
    EditText titles,authors,pubs,linkss,descss,bookid,Imglnk;
    Bookdeets bookdeets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbooks);
        Addbooks=(Button)findViewById(R.id.addbkbtn);
        refresh=(Button)findViewById(R.id.ClearFeild);
        titles = (EditText) findViewById(R.id.bknames);
        authors = (EditText) findViewById(R.id.anames);
        pubs = (EditText) findViewById(R.id.pnames);
        linkss = (EditText) findViewById(R.id.bklinks);
        descss = (EditText) findViewById(R.id.bkdescriptions);
        Imglnk=(EditText) findViewById(R.id.imglinks);
        bookid=(EditText)findViewById(R.id.uid);
        bookdeets=new Bookdeets();
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("books");

        Addbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = titles.getText().toString().trim();
                String Author = authors.getText().toString().trim();
                String Publisher = pubs.getText().toString().trim();
                String Link = linkss.getText().toString().trim();
                String Desc = descss.getText().toString().trim();
                String Uid=bookid.getText().toString().trim();
                String Img=Imglnk.getText().toString().trim();

                HashMap<String, Object> map = new HashMap<>();
                map.put("bookname", Title);
                map.put("author", Author);
                map.put("Publisher", Publisher);
                map.put("link", Link);
                map.put("Desc", Desc);
                map.put("id", Uid);
                map.put("image",Img);

                databaseReference.child(Uid).setValue(map);
                Toast.makeText(getApplicationContext(),"Book Added succesfully",Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(Title) ||TextUtils.isEmpty(Author)||TextUtils.isEmpty(Publisher)||TextUtils.isEmpty(Link)||TextUtils.isEmpty(Desc)){
                    titles.setError("Field cant be empty");
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titles.setText(null);
                authors.setText(null);
                bookid.setText(null);
                Imglnk.setText(null);
                descss.setText(null);
                linkss.setText(null);
                pubs.setText(null);
            }
        });
    }
}
