package com.example.projectmanagement;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;


import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addProject extends AppCompatActivity {
    private static final String TAG = "addProject";
    private TextView StartDate,EndDate;
    FirebaseFirestore fStore;
    private DatePickerDialog.OnDateSetListener SDateSetListener,EDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        StartDate = (TextView) findViewById(R.id.SDate);
        EndDate = (TextView) findViewById(R.id.EDate);

        EditText name = findViewById(R.id.editN);
        EditText dis = findViewById(R.id.editD);
        EditText goals = findViewById(R.id.editG);

        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        addProject.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, SDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        SDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                StartDate.setText(date);
            }
        };

        EndDate.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    addProject.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth, EDateSetListener, year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        EDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                EndDate.setText(date);
            }
        };

        fStore = FirebaseFirestore.getInstance();
        Button save = findViewById(R.id.AddProjectBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartDate = (TextView) findViewById(R.id.SDate);
                EndDate = (TextView) findViewById(R.id.EDate);

                EditText name = findViewById(R.id.editN);
                EditText dis = findViewById(R.id.editD);
                EditText goals = findViewById(R.id.editG);

                if(!checkEmpty()) {
                    String nameS = name.getText().toString();
                    String disc = dis.getText().toString();
                    String goalss = goals.getText().toString();
                    String sd = StartDate.getText().toString();
                    String ed = EndDate.getText().toString();

                    // Create a new user with a first, middle, and last name
                    Map<String, Object> task = new HashMap<>();
                    task.put("name", nameS);
                    task.put("description", disc);
                    task.put("goals", goalss);
                    task.put("startDate", sd);
                    task.put("finishDate", ed);

// Add a new document with a generated ID
                    FirebaseFirestore.getInstance().collection("Projects")
                            .add(task)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(getApplicationContext(),"Project added successfully",Toast.LENGTH_LONG).show();
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
        StartDate = (TextView) findViewById(R.id.SDate);
        EndDate = (TextView) findViewById(R.id.EDate);

        EditText name = findViewById(R.id.editN);
        EditText dis = findViewById(R.id.editD);
        EditText goals = findViewById(R.id.editG);

        boolean empty = false;

        if(name.getText().length()==0){
            name.setError("Name is required");
            empty = true ;
        }
        if(dis.getText().length()==0){
            dis.setError("Description is required");
            empty = true ;
        }
        if(goals.getText().length()==0){
            goals.setError("Goals is required");
            empty = true ;
        }
        if(StartDate.getText().length()==0){
            StartDate.setError("Start date is required");
            empty = true ;
        }
        if(EndDate.getText().length()==0){
            EndDate.setError("End date is required");
            empty = true ;
        }
        return empty;

    }


}