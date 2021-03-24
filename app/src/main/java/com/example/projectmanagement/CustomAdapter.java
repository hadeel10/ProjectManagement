package com.example.projectmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id, name, sdate, duration, cost, assign, num;
    private CustomAdapter  adapter;

    CustomAdapter(Context context, ArrayList id, ArrayList name,
                  ArrayList sdate, ArrayList duration, ArrayList cost, ArrayList assign, ArrayList num ){

        this.context = context;
        this.id = id;
        this.name = name;
        this.sdate = sdate;
        this.duration = duration;
        this.cost = cost;
        this.assign = assign;
        this.num = num;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.num.setText(num.get(position).toString());
        holder.name.setText(name.get(position).toString());
        holder.sdate.setText(sdate.get(position).toString());
        holder.time.setText(duration.get(position).toString());
      holder.cost.setText(cost.get(position).toString());
       holder.assign2.setText(assign.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return num.size();
    }

    public Task deleteItem(View itemView, int position){
        //reminder_id


       //getSnapshots().getSnapshot(position).getReference().delete();

        String key = id.get(position).toString();

        Task taskk = FirebaseFirestore.getInstance()
                .collection("Tasks").document(key).delete();
       return taskk ;

        //notifyItemRemoved(position);
        //notifyItemRangeChanged(position, reminder_id.size());


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, name, sdate, time, cost, assign2, num;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.num_txt);
            name = itemView.findViewById(R.id.name_txt);
            assign2 = itemView.findViewById(R.id.assign_txt);
            cost = itemView.findViewById(R.id.cost_txt);
            sdate = itemView.findViewById(R.id.sdate_txt);
            time = itemView.findViewById(R.id.time_txt);

         /*   itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //start new intent
                    Intent intent = new Intent(v.getContext(),ReminderInfo.class);
                    intent.putExtra("idN",(String) id.getText());
                    intent.putExtra("Title",(String) reminder_title_txt.getText());
                    intent.putExtra("Time",(String) reminder_time_txt.getText());
                    intent.putExtra("Date",(String) reminder_date_txt.getText());



                    v.getContext().startActivity(intent);

                }
            }); */
        }
    }
}
