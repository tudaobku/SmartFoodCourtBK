package com.example.smartfoodcourt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartfoodcourt.Common.Common;
import com.example.smartfoodcourt.Model.Rating;
import com.example.smartfoodcourt.ViewHolder.ShowCommentViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowComment extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference ratingFood;
    FirebaseRecyclerAdapter<Rating, ShowCommentViewHolder> adapter;
    String foodRef = "";

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null)
            adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);

        database = FirebaseDatabase.getInstance();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerComment);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null)
            foodRef = getIntent().getStringExtra(Common.INTENT_FOOD_REF);
        if(foodRef != null && !foodRef.isEmpty())
        {
            ratingFood = database.getReference("Rating/" + foodRef);
            FirebaseRecyclerOptions<Rating> options = new FirebaseRecyclerOptions.Builder<Rating>().setQuery(ratingFood.child("List"), Rating.class).build();
            adapter = new FirebaseRecyclerAdapter<Rating, ShowCommentViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ShowCommentViewHolder showCommentViewHolder, final int position, @NonNull Rating rating) {
                    showCommentViewHolder.ratingBarDetail.setRating(Float.parseFloat(rating.getRateValue()));
                    showCommentViewHolder.txtUserName.setText(adapter.getRef(position).getKey());
                    showCommentViewHolder.txtComment.setText(rating.getComment());

                }

                @NonNull
                @Override
                public ShowCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_comment_layout, parent, false);
                    return new ShowCommentViewHolder(view);
                }
            };
            loadComment(foodRef);
        }
    }

    private void loadComment(String foodID) {
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}