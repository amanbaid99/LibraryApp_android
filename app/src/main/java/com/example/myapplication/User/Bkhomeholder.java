package com.example.myapplication.User;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


public class Bkhomeholder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView bookimg;
    public CardView cardView;


    public Bkhomeholder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.bkdettitle);
        bookimg = itemView.findViewById(R.id.bkdetimg);
        cardView=itemView.findViewById(R.id.bookcardview);


    }
}





