package com.example.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class myProjects extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<cardView>cardViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardViewList=new ArrayList<>();
        for(int i=0;i<=10;i++){
            cardView cardView=new cardView(
                    "Project"+ i+1,"Disc"
            );
            cardViewList.add(cardView);

        }
        adapter=new myAdapter(cardViewList,this);
        recyclerView.setAdapter(adapter);

    }
}