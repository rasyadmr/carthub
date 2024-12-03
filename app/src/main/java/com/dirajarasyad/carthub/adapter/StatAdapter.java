package com.dirajarasyad.carthub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.holder.StatHolder;
import com.dirajarasyad.carthub.model.Stat;

import java.util.List;

public class StatAdapter extends RecyclerView.Adapter<StatHolder> {
    private List<Stat> statList;
    private Context context;

    public StatAdapter(List<Stat> statList) {
        this.statList = statList;
    }

    @NonNull
    @Override
    public StatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_stat, parent, false);

        return new StatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatHolder holder, int position) {
        String title = statList.get(position).getTitle();
        String value = statList.get(position).getValue();

        holder.statTitleTV.setText(title);
        holder.statValueTV.setText(value);
    }

    @Override
    public int getItemCount() {
        return statList.size();
    }
}
