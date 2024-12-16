package com.dirajarasyad.carthub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.holder.AdminMenuHolder;
import com.dirajarasyad.carthub.model.Menu;

import java.util.List;

public class AdminMenuAdapter extends RecyclerView.Adapter<AdminMenuHolder> {
    private final List<Menu> menuList;
    private Context context;

    public AdminMenuAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public AdminMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_admin_menu, parent, false);

        return new AdminMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminMenuHolder holder, int position) {
        holder.admin_menuTitleTV.setText(menuList.get(position).getTitle());
        holder.admin_menuIconIV.setImageDrawable(menuList.get(position).getImage());
        holder.admin_menuContainerCV.setOnClickListener(view -> {
            Fragment fragment = menuList.get(position).getFragment();
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.homeContainerFL, fragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
