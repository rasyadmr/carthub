package com.dirajarasyad.carthub.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dirajarasyad.carthub.R;
import com.dirajarasyad.carthub.fragment.ProfileEditFragment;
import com.dirajarasyad.carthub.holder.UserInfoHolder;
import com.dirajarasyad.carthub.model.User;

import java.util.List;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoHolder> {
    private final List<User> userList;
    private Context context;

    public UserInfoAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_user_info, parent, false);

        return new UserInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoHolder holder, int position) {
        holder.user_infoImageIV.setImageDrawable(userList.get(position).getImage());
        holder.user_infoUsernameTV.setText(userList.get(position).getUsername());
        holder.user_infoEmailTV.setText(userList.get(position).getEmail());
        holder.user_infoPhoneTV.setText(userList.get(position).getPhone());
        holder.user_infoRoleTV.setText(userList.get(position).getRole().value());

        holder.user_infoContainerCV.setOnClickListener(view -> {
            if (userList.get(position).getRole() != User.Role.ADMIN) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id", userList.get(position).getId());

                ProfileEditFragment profileEdit = new ProfileEditFragment();
                profileEdit.setArguments(bundle);

                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.homeContainerFL, profileEdit).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
