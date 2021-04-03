package com.example.projectmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class myProjects extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<cardView>cardViewList;
    ArrayList<String> id, name, sdate, edate, goals,total, des, num;
    static  String idd,costd;
    static int totcost=0;

    myAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects);
        recyclerView= findViewById(R.id.recyclerViewProject);
      //  recyclerView.setHasFixedSize(true);
      // recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton addProject = findViewById(R.id.addProject);
        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),addProject.class);
                startActivity(i);
            }
        });
      /*  cardViewList=new ArrayList<>();
        for(int i=0;i<=10;i++){
            cardView cardView=new cardView(
                    "Project"+ i+1,"Disc"
            );
            cardViewList.add(cardView);

        }
        adapter=new myAdapter(cardViewList,this);
        recyclerView.setAdapter(adapter);*/

        id = new ArrayList<>();
        name = new ArrayList<>();
        sdate = new ArrayList<>();
        edate = new ArrayList<>();
        des = new ArrayList<>();
        goals = new ArrayList<>();
        total = new ArrayList<>();
        num = new ArrayList<>();

        storeDataInArrays();

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
                .collection("Projects")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            int myListOfDocumentsLen = myListOfDocuments.size();
                            for (int i = 0; i < myListOfDocumentsLen; i++) {
                                String namep = myListOfDocuments.get(i).getString("name");
                                String sdatep = myListOfDocuments.get(i).getString("startDate");
                                String edatep = myListOfDocuments.get(i).getString("finishDate");
                                String desp = myListOfDocuments.get(i).getString("description");
                                String goalp = myListOfDocuments.get(i).getString("goals");

                            idd = myListOfDocuments.get(i).getId();

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

                                                    //Toast.makeText(getApplicationContext(),"hii"+totcost,Toast.LENGTH_SHORT).show();
                                                    for (int i = 0; i < myListOfDocumentsLen; i++) {

                                                            String idIntent = idd;
                                                        Toast.makeText(getApplicationContext(),idIntent,Toast.LENGTH_SHORT).show();
                                                            if(idIntent.equals(myListOfDocuments.get(i).getString("projectID"))) {

                                                                costd = myListOfDocuments.get(i).getString("cost");

                                                           totcost+=Integer.parseInt(costd);
                                                                Toast.makeText(getApplicationContext(),"hii"+totcost,Toast.LENGTH_SHORT).show();

                                                            }


                                                    } // for loop close


                                                }// if (task successful ) close



                                            }

                                        });


                                num.add(""+(i+1));
                                id.add(idd);
                                name.add( namep);
                                sdate.add("Start Date: "+ sdatep);
                                goals.add("Goals: "+goalp );
                                des.add("Description: "+ desp );
                                total.add("Total Cost: "+ totcost );
                                edate.add( "Finish Date: "+edatep );
                                totcost=0;


                            } // for loop close
                            customAdapter = new myAdapter(myProjects.this, id, name, sdate, edate, des,total ,goals, num);
                            recyclerView.setAdapter(customAdapter);
                           // Toast.makeText(getApplication()," "+sdate.get(0),Toast.LENGTH_LONG).show();
                            recyclerView.setLayoutManager(new LinearLayoutManager(myProjects.this));
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
        edate.clear();
        goals.clear();
        total.clear();
        des.clear();
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

                AlertDialog.Builder alert = new AlertDialog.Builder(myProjects.this);
                alert.setTitle("Delete Reminder");
                alert.setMessage("Are you sure you want to delete this reminder?");

                alert.setPositiveButton("Yes, delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Task taskk =  customAdapter.deleteItem(viewHolder.itemView , position);

                     // Toast.makeText(myProjects.this, ""+ customAdapter.tasks.get(0).toString(), Toast.LENGTH_SHORT).show();
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
                        .addBackgroundColor(ContextCompat.getColor(myProjects.this, R.color.red))
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        }).attachToRecyclerView(recyclerView);

    }
}