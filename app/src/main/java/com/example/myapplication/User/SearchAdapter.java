package com.example.myapplication.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> BookNameList;
    ArrayList<String> AuthorNameList;
    ArrayList<String> PicList;
    ArrayList<String> PublisherList;
    ArrayList<String>DescriptionList;
    ArrayList<String>LinkList;
    LinearLayout booklayout;
    DatabaseReference reference;
    private FirebaseAnalytics mFirebaseAnalytics;

    class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView bookimage;
        TextView bookname, authorname,publisher;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            bookimage =  itemView.findViewById(R.id.Bookimg);
            bookname =  itemView.findViewById(R.id.BookName);
            authorname =  itemView.findViewById(R.id.AuthorName);
            publisher =  itemView.findViewById(R.id.Publications);
            booklayout=itemView.findViewById(R.id.LinLayout);
            reference = FirebaseDatabase.getInstance().getReference();
            reference.keepSynced(true);


        }
    }

    public SearchAdapter(Context context, ArrayList<String> bookNameList, ArrayList<String> authorNameList, ArrayList<String> picList,ArrayList<String> publisherList,ArrayList<String> descriptionList,ArrayList<String> linkList) {
        this.context = context;
        BookNameList = bookNameList;
        AuthorNameList = authorNameList;
        PicList = picList;
        PublisherList=publisherList;
        DescriptionList=descriptionList;
        LinkList=linkList;

    }


    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_search_layout,parent,false);
         return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, final int position) {
        holder.bookname.setText(BookNameList.get(position));
        holder.authorname.setText(AuthorNameList.get(position));
        holder.publisher.setText(PublisherList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {






                    Intent i = new Intent(context, Bookdetailslayouthome.class);
                       i.putExtra("booknames",BookNameList.get(position));
                       i.putExtra("Image",PicList.get(position));
                       i.putExtra("author_name",AuthorNameList.get(position));
                       i.putExtra("publisher_name",PublisherList.get(position));
                       i.putExtra("description",DescriptionList.get(position));
                       i.putExtra("link",LinkList.get(position));
                       context.startActivity(i);
                   }
               });

        Glide.with(context).asBitmap().load(PicList.get(position)).placeholder(R.mipmap.ic_launcher_round).into(holder.bookimage);

    }

    @Override
    public int getItemCount() {
        return BookNameList.size();
    }
}
