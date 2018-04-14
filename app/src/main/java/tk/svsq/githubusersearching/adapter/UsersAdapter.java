package tk.svsq.githubusersearching.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.model.GitHubUser;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder>{

    private List<GitHubUser> usersList = new ArrayList<>();

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_cardview, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.bind(usersList.get(position));
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void addAll(List<GitHubUser> userList) {
        int position = getItemCount();
        this.usersList.addAll(userList);
        notifyItemRangeInserted(position, this.usersList.size());
    }
}
