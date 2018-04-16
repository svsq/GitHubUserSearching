package tk.svsq.githubusersearching.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.model.GitHubUser;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private List<GitHubUser> users = new ArrayList<>();
    private RecyclerViewClickListener mListener;

    public UsersAdapter(RecyclerViewClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_cardview, parent, false);
        return new UsersViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addAll(List<GitHubUser> userList) {
        int position = getItemCount();
        this.users.addAll(userList);
        notifyItemRangeInserted(position, this.users.size());
    }

    public void clearAll() {
        int position = getItemCount();
        this.users.clear();
        notifyItemRangeRemoved(position, this.getItemCount());
    }

    public void add(List<GitHubUser> userList) {
        int position = getItemCount();
        this.users.add(userList.get(position));
        notifyItemInserted(position);
    }
}
