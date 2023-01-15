package org.pytorch.demo.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.pytorch.demo.R;
import org.pytorch.demo.models.HistoryDisease;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    List<HistoryDisease> historyList;
    Context context;

    public HistoryAdapter(List<HistoryDisease> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_disease, parent, false);
        return new MyViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
        HistoryDisease disease = historyList.get(position);
        holder.nameDisease.setText(disease.getName());
        holder.imageDisease.setImageResource(disease.getImageDisease());
        holder.nameIdentificationPlant.setText(disease.getTextIdentificationPlant());
        holder.namePathogensPlant.setText(disease.getTextPathogensPlant());
        holder.timeDetect.setText(disease.getDate());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView nameDisease, namePathogensPlant, nameIdentificationPlant, timeDetect;
        public ImageView imageDisease;


        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameDisease = itemView.findViewById(R.id.textDiseasePlant);
            namePathogensPlant = itemView.findViewById(R.id.textPathogensPlant);
            nameIdentificationPlant = itemView.findViewById(R.id.textIdentificationPlant);
            timeDetect = itemView.findViewById(R.id.textTimeDetect);
            imageDisease = itemView.findViewById(R.id.imageDisease);

        }
    }
}
