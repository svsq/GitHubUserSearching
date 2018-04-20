package tk.svsq.githubusersearching.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.adapter.RepoAdapter;
import tk.svsq.githubusersearching.model.GitHubRepo;
import tk.svsq.githubusersearching.rest.GitHubApiClient;
import tk.svsq.githubusersearching.rest.GitHubCall;

public class ReposFragment extends Fragment {

    private ProgressBar progressBar;

    private List<GitHubRepo> repoList;
    private String userLogin;
    private RepoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_repos, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        TextView userNameText = root.findViewById(R.id.ReposFragment_UserName);
        RecyclerView listRepos = root.findViewById(R.id.ReposFragment_ListRepos);
        progressBar = root.findViewById(R.id.ReposFragment_ProgressBar);

        Bundle bundle = getArguments();
        userLogin = bundle.getString(SearchUserFragment.KEY_CURRENT_LOGIN);
        String numberRepos = bundle.getString(SearchUserFragment.KEY_NUMBER_REPO);
        String titleText = getString(R.string.fragment_repo_title, userLogin, numberRepos);
        userNameText.setText(titleText);
        repoList = new ArrayList<>();
        adapter = new RepoAdapter();
        listRepos.setLayoutManager(linearLayoutManager);
        listRepos.setAdapter(adapter);

        loadRepositories();

        return root;
    }

    private void loadRepositories() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        GitHubCall apiService = GitHubApiClient.getClient().create(GitHubCall.class);
        Call<List<GitHubRepo>> call = apiService.getRepo(userLogin);
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(@NonNull Call<List<GitHubRepo>> call, @NonNull Response<List<GitHubRepo>> response) {
                repoList.clear();
                if (response.body() != null) {
                    repoList.addAll(response.body());
                    adapter.addAll(repoList);
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<List<GitHubRepo>> call, Throwable t) {

            }
        });
    }


}
