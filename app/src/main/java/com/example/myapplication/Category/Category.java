package com.example.myapplication.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.R;

public class Category extends AppCompatActivity {
    Button fiction,nonfcition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        fiction=(Button)findViewById(R.id.ficitionbtn);
        nonfcition=(Button)findViewById(R.id.nonficitionbtn);
    }
}
