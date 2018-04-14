package tk.svsq.githubusersearching.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.model.GitHubRepo;

public class RepoAdapter extends RecyclerView.Adapter<RepoViewHolder> {

    private List<GitHubRepo> repoList = new ArrayList<>();

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repos_cardview, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bind(repoList.get(position));
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public void addAll(List<GitHubRepo> repoList) {
        int position = getItemCount();
        this.repoList.addAll(repoList);
        notifyItemRangeInserted(position, this.repoList.size());
    }

    public void clearAll(List<GitHubRepo> repoList) {
        int position = getItemCount();
        this.repoList.clear();
        notifyItemRangeRemoved(position, this.repoList.size());
    }

}
