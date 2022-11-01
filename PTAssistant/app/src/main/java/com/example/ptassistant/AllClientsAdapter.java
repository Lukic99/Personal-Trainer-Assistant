package com.example.ptassistant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptassistant.Data.Client;
import com.example.ptassistant.Data.EditTrainingLog;

import java.util.ArrayList;
import java.util.List;

public class AllClientsAdapter extends RecyclerView.Adapter<AllClientsAdapter.ClientViewHolder> {

    List<Client> clients;
    Context con;
    ClientViewHolder cvh;
//    Context t = AllClientsAdapter.get;
    public AllClientsAdapter(List<Client> clients, Context con) {
        this.clients = clients;
        this.con=con;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_list_item, parent, false);
        cvh = new ClientViewHolder(v,clients);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        String clientName = clients.get(position).getFirstName()+" "+clients.get(position).getLastName()+"   Age: "+clients.get(position).getAge();
        holder.name.setText(clientName);
        String num = "Training sessions: "+clients.get(position).getNumberOfTrainingSessions();
        holder.sessionsNumber.setText(num);

        cvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditTrainingLog.class);
                Client c= clients.get(position);
                intent.putExtra("log", c.getTrainingLog());
                String name = c.getFirstName()+" "+c.getLastName()+"("+c.getAge()+")";
                intent.putExtra("name",name);
                String id = ""+c.getId();
                intent.putExtra("id",id);
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() { return clients.size();  }


    public static class ClientViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView sessionsNumber;

        public ClientViewHolder(@NonNull View v, List<Client> clients) {
            super(v);
            this.name = (TextView)   v.findViewById(R.id.clientName);
            this.sessionsNumber = (TextView) v.findViewById(R.id.sessionsView);

        }
    }
}
