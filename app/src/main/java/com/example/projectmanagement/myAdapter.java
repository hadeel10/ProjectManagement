package com.example.projectmanagement;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {
    private ArrayList id, nameP, sdateP, edateP,total, desP, goalP, numP;
    private List<cardView> listItem;
    private Context context;
    static String key;
    public myAdapter(Context context, ArrayList id, ArrayList name,
                     ArrayList sdate, ArrayList edate, ArrayList des,ArrayList total ,ArrayList goal, ArrayList num) {
        this.context = context;
        this.id = id;
        this.nameP = name;
        this.sdateP = sdate;
        this.edateP = edate;
        this.desP = des;
        this.goalP = goal;
        this.total = total;
        this.numP = num;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.projects_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.num.setText(numP.get(position).toString());
        holder.name.setText(nameP.get(position).toString());
        holder.sdate.setText(sdateP.get(position).toString());
        holder.goal.setText(goalP.get(position).toString());
        holder.des.setText(desP.get(position).toString());
        holder.cost.setText(total.get(position).toString());
        holder.edate.setText(edateP.get(position).toString());
        holder.id = id.get(position).toString();
    }

    @Override
    public int getItemCount() {
        return numP.size();
    }

    public Task deleteItem(View itemView, int position){
        //reminder_id


        //getSnapshots().getSnapshot(position).getReference().delete();

         key = id.get(position).toString();

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
                            for (int i = 0; i < myListOfDocumentsLen; i++) {
                                    String idd = key;
                                if(idd.equals(myListOfDocuments.get(i).getString("projectID"))) {
                                    FirebaseFirestore.getInstance()
                                            .collection("Tasks").document(myListOfDocuments.get(i).getId()).delete();

                                    }

                            } // for loop close

                        }// if (task successful ) close


                    }

                });
        Task taskk = FirebaseFirestore.getInstance()
                .collection("Projects").document(key).delete();
        return taskk ;

        //notifyItemRemoved(position);
        //notifyItemRangeChanged(position, reminder_id.size());


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  name, sdate, edate, des, goal,cost, num;
          String id ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.num_txtPro);
            name = itemView.findViewById(R.id.name_txtPro);
            edate = itemView.findViewById(R.id.finishdPro);
            des = itemView.findViewById(R.id.des_txtPro);
            sdate = itemView.findViewById(R.id.sdate_txtPro);
            goal = itemView.findViewById(R.id.goals_txt);
            cost = itemView.findViewById(R.id.cost_txtPro);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //start new intent
                    Intent intent = new Intent(v.getContext(),MainActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("title",(String) name.getText());
                    v.getContext().startActivity(intent);

                }
            });
        }
    }
}
