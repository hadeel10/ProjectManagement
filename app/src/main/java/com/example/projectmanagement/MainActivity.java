package com.example.projectmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.CalendarContract;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;
    ArrayList<String> id, name, sdate, duration, cost, assign, num;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    TextView title = findViewById(R.id.titleTask);
    title.setText(getIntent().getStringExtra("title"));

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new reminder page
                //System.out.print("new reminder page");
               Intent intent = new Intent(MainActivity.this, addTask.class);
               String id = getIntent().getStringExtra("id");
               intent.putExtra("id",id);
                startActivity(intent);
            }
        });


        id = new ArrayList<>();
        name = new ArrayList<>();
        sdate = new ArrayList<>();
        duration = new ArrayList<>();
        cost = new ArrayList<>();
        assign = new ArrayList<>();
        num = new ArrayList<>();

     /*    id.add("1");
        name.add("1");
        sdate.add("1");
        duration.add("1");
        cost.add("1");
        assign.add("1"); */

        //dbManager.insertNewReminder("new reminder","27/06/1442","12:00","Low");
        storeDataInArrays();

       /* customAdapter = new CustomAdapter(MainActivity.this, id, name, sdate, duration, cost, assign);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
      */

        setUp();
    }

    protected void onResume() {
        super.onResume();
       if(customAdapter != null) {
            clearList();
            storeDataInArrays();
            customAdapter.notifyDataSetChanged();
        }
    }
    void storeDataInArrays(){
        Task<QuerySnapshot> querySnapshotTask2 = FirebaseFirestore.getInstance()
                .collection("Tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            int myListOfDocumentsLen = myListOfDocuments.size();
                            int n = 1 ;
                            for (int i = 0; i < myListOfDocumentsLen; i++) {
                                if(getIntent().hasExtra("id")) {
                                    String idIntent = getIntent().getStringExtra("id");
                                    if(idIntent.equals(myListOfDocuments.get(i).getString("projectID"))) {
                                        String named = myListOfDocuments.get(i).getString("name");
                                        String durationd = myListOfDocuments.get(i).getString("duration");
                                        String assignd = myListOfDocuments.get(i).getString("assignedResources");
                                        String sdated = myListOfDocuments.get(i).getString("startDate");
                                        String costd = myListOfDocuments.get(i).getString("cost");
                                        String idd = myListOfDocuments.get(i).getId();

                                        num.add("" + (n++));
                                        id.add(idd);
                                        name.add(named);
                                        sdate.add("Start Date: " + sdated);
                                        cost.add("Cost: " + costd);
                                        duration.add("Duration: " + durationd);
                                        assign.add("Assigned Resources: " + assignd);
                                    }
                                }

                            } // for loop close
                            customAdapter = new CustomAdapter(MainActivity.this, id, name, sdate, duration, cost, assign, num);
                            recyclerView.setAdapter(customAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        }// if (task successful ) close
                        else{        Toast.makeText(getApplication(), "empty", Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

    public void clearList(){
        id.clear();
        name.clear();
        sdate.clear();
        duration.clear();
        cost.clear();
        assign.clear();
        num.clear();
    }

    public void setUp(){

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                android.app.AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete Reminder");
                alert.setMessage("Are you sure you want to delete this reminder?");

                alert.setPositiveButton("Yes, delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       Task taskk =  customAdapter.deleteItem(viewHolder.itemView , position);
                       taskk.addOnCompleteListener(new OnCompleteListener() {
                           @Override
                           public void onComplete(@NonNull Task task) {
                               if(task.isSuccessful()){
                                   Toast.makeText(getApplicationContext(), "deleted successfully", Toast.LENGTH_SHORT).show();

                               }else{
                                   Toast.makeText(getApplicationContext(), "Failed to delete"+taskk.getException(), Toast.LENGTH_SHORT).show();

                               }
                           }
                       });

                        //customAdapter.notifyItemRemoved(position);
                        if(customAdapter != null) {
                            clearList();
                            storeDataInArrays();
                            customAdapter.notifyDataSetChanged();
                        }
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        customAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                }).show();

            }

            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        }).attachToRecyclerView(recyclerView);

    }
}
