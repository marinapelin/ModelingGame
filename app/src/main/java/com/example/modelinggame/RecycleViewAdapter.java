package com.example.modelinggame;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    List<GameItem> gameList;
    Context context;
    public RecycleViewAdapter(List<GameItem> gameList, Context context) {
        this.gameList = gameList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_game_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_GameCategory.setText(gameList.get(position).getCategory());
        holder.tv_GameHeading.setText(gameList.get(position).getHeading());
        holder.tv_GameLevel.setText(gameList.get(position).getLevel());
        holder.tv_GameTime.setText(gameList.get(position).getTime());
        Glide.with(this.context).load(gameList.get(position).getImageURL()).into(holder.iv_GameItemPicture);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send data
                Intent intent = new Intent(context, TheGame.class);
                intent.putExtra("id", gameList.get(position).getId());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return gameList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_GameItemPicture;
        TextView tv_GameCategory;
        TextView tv_GameHeading;
        TextView tv_GameLevel;
        TextView tv_GameTime;
        ConstraintLayout parentLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_GameItemPicture = itemView.findViewById(R.id.iv_GameItemPicture);
            tv_GameCategory = itemView.findViewById(R.id.tv_GameCategory);
            tv_GameHeading = itemView.findViewById(R.id.tv_GameHeading);
            tv_GameLevel = itemView.findViewById(R.id.tv_GameLevel);
            tv_GameTime = itemView.findViewById(R.id.tv_time);
            parentLayout = itemView.findViewById(R.id.OneGameLayout);
        }
    }
}
