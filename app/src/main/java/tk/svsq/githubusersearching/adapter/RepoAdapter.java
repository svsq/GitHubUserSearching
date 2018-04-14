package tk.svsq.githubusersearching.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.model.GitHubRepo;

public class RepoAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<GitHubRepo> repoList = new ArrayList<>();

    /*public RepoAdapter(Context context, List<GitHubRepo> repoList) {
        this.repoList = repoList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }*/

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repos_cardview, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
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
