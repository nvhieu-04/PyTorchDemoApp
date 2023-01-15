package org.pytorch.demo.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.pytorch.demo.R;
import org.pytorch.demo.models.Disease;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    List<Disease> diseaseList;
    Context context;
    public SearchAdapter(List<Disease> diseaseList, Context context) {
        this.diseaseList = diseaseList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_disease, parent, false);
        return new SearchAdapter.MyViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Disease disease = diseaseList.get(position);
        holder.nameDisease.setText(disease.getName());
        holder.imageDisease.setImageResource(disease.getImageDisease());
        holder.nameIdentificationPlant.setText(disease.getTextIdentificationPlant());
        holder.namePathogensPlant.setText(disease.getTextPathogensPlant());
    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView nameDisease, namePathogensPlant, nameIdentificationPlant;
        public ImageView imageDisease;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameDisease = itemView.findViewById(R.id.textDiseasePlant);
            namePathogensPlant = itemView.findViewById(R.id.textPathogensPlant);
            nameIdentificationPlant = itemView.findViewById(R.id.textIdentificationPlant);
            imageDisease = itemView.findViewById(R.id.imageDisease);
        }
    }
}
