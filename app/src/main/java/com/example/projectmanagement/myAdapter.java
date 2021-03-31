package com.example.projectmanagement;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    public myAdapter(List<cardView> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    private List<cardView> listItem;
    private Context context;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_card,parent,false);
   return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cardView cardView=listItem.get(position);

        holder.textViewPname.setText(cardView.getProject_name());
        holder.textViewDis.setText(cardView.getDis());

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewPname,textViewDis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewPname=(TextView) itemView.findViewById(R.id.ProjectName);
            textViewPname=(TextView) itemView.findViewById(R.id.ProjectDis);

        }
    }
}
