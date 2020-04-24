package com.example.myapplication.Admin;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.User.Bookdeets;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class AdminBookdetails extends AdminSearch {
    EditText titles,authors,pubs,linkss,descss,bookid,Imglnk;
    Bookdeets bookdeets;
    Button update,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminbooksdetails);
        titles = (EditText) findViewById(R.id.bknames);
        authors = (EditText) findViewById(R.id.anames);
        pubs = (EditText) findViewById(R.id.pnames);
        linkss = (EditText) findViewById(R.id.bklinks);
        descss = (EditText) findViewById(R.id.bkdescriptions);
        Imglnk=(EditText) findViewById(R.id.imglinks);
        update=(Button)findViewById(R.id.updatebtn);
        delete=(Button)findViewById(R.id.deltebtn);
        bookid=(EditText)findViewById(R.id.uid);
        bookdeets=new Bookdeets();

        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("books");
        String uid = getIntent().getStringExtra("uid");
        bookid.setText(uid,TextView.BufferType.EDITABLE);

        String Bname = getIntent().getStringExtra("booknamess");
            titles.setText(Bname,TextView.BufferType.EDITABLE);

        String imglink = getIntent().getStringExtra("Image");
        Imglnk.setText(imglink,TextView.BufferType.EDITABLE);

        String Author = getIntent().getStringExtra("author_names");
        authors.setText(Author,TextView.BufferType.EDITABLE);

        String publisher = getIntent().getStringExtra("publisher_names");
        pubs.setText(publisher,TextView.BufferType.EDITABLE);

        final String Link = getIntent().getStringExtra("links");
        linkss.setText(Link,TextView.BufferType.EDITABLE);

        String Desc = getIntent().getStringExtra("descriptions");
        descss.setText(Desc);
        descss.setMovementMethod(new ScrollingMovementMethod());
        update.setOnClickListener(new View.OnClickListener() {
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

                    databaseReference.child(Uid).updateChildren(map);

                    if(TextUtils.isEmpty(Title) ||TextUtils.isEmpty(Author)||TextUtils.isEmpty(Publisher)||TextUtils.isEmpty(Link)||TextUtils.isEmpty(Desc)){
                        titles.setError("Field cant be empty");
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Uid=bookid.getText().toString().trim();
                    databaseReference.child(Uid).setValue(null);
                    Toast.makeText(getApplicationContext(),"Book deleted succesfully",Toast.LENGTH_SHORT).show();
                }
            });
    }

    }

