package tk.svsq.githubusersearching.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.model.GitHubRepo;

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView repoName;
    private TextView repoDescription;
    private TextView repoLanguage;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        repoName = itemView.findViewById(R.id.item_repoName);
        repoDescription = itemView.findViewById(R.id.item_repoDesc);
        repoLanguage = itemView.findViewById(R.id.item_repoLanguage);
    }

    public void bind(GitHubRepo item) {
        repoName.setText(item.getRepoName());
        repoDescription.setText(item.getRepoDescription());
        repoLanguage.setText(item.getRepoLanguage());

    }
}
