package com.example.projectmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class addTask extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        fStore = FirebaseFirestore.getInstance();


        Button save = findViewById(R.id.taskSave);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = findViewById(R.id.taskName);
                EditText date = findViewById(R.id.taskStartDate);
                EditText duration = findViewById(R.id.taskDuration);
                EditText cost = findViewById(R.id.taskCost);
                EditText assign = findViewById(R.id.taskResources);
               if(!checkEmpty()) {
                   String nameS = name.getText().toString();
                   String dateS = date.getText().toString();
                   String durationS = duration.getText().toString();
                   String costS = cost.getText().toString();
                   String assignS = assign.getText().toString();

                   // Create a new user with a first, middle, and last name
                   Map<String, Object> task = new HashMap<>();
                   task.put("name", nameS);
                   task.put("assignedResources", assignS);
                   task.put("cost", costS);
                   task.put("duration", durationS);
                   task.put("projectID", "1815");   //// need project id *********
                   task.put("startDate", dateS);

// Add a new document with a generated ID
                   FirebaseFirestore.getInstance().collection("Tasks")
                           .add(task)
                           .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                               @Override
                               public void onSuccess(DocumentReference documentReference) {
                                   Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                   Toast.makeText(getApplicationContext(),"Task added successfully",Toast.LENGTH_LONG).show();
                               }
                           })
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Log.w("TAG", "Error adding document", e);
                                   Toast.makeText(getApplicationContext(),"Error adding",Toast.LENGTH_LONG).show();

                               }
                           });

                   finish();
               }
            }
        });



    }

    public boolean checkEmpty(){
        EditText name = findViewById(R.id.taskName);
        EditText date = findViewById(R.id.taskStartDate);
        EditText duration = findViewById(R.id.taskDuration);
        EditText cost = findViewById(R.id.taskCost);
        EditText assign = findViewById(R.id.taskResources);

        boolean empty = false;

        if(name.getText().length()==0){
            name.setError("Name is required");
            empty = true ;
        }
        if(date.getText().length()==0){
            date.setError("Date is required");
            empty = true ;
        }
        if(duration.getText().length()==0){
            duration.setError("Duration is required");
            empty = true ;
        }
        if(cost.getText().length()==0){
            cost.setError("Cost is required");
            empty = true ;
        }
        if(assign.getText().length()==0){
            assign.setError("Assign resource is required");
            empty = true ;
        }
        return empty;

    }


    public void GoToProjectPage(View view) {
        Button GoToProjectBtn=(Button) findViewById(R.id.GoToPro);
    Intent intent=new Intent(getApplicationContext(),addProject.class);
    startActivity(intent);
    }
}