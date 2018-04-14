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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.adapter.UsersAdapter;
import tk.svsq.githubusersearching.model.GitHubSearchResult;
import tk.svsq.githubusersearching.model.GitHubUser;
import tk.svsq.githubusersearching.rest.GitHubApiClient;
import tk.svsq.githubusersearching.rest.GitHubUserCall;

import static tk.svsq.githubusersearching.Util.CheckConnectivity.isInternetConnected;

public class SearchUserFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_COMPANY_NAME = "username";
    //public static final String KEY_NUMBER_REPO = "number_repo";

    private RecyclerView usersList;
    private UsersAdapter adapter;

    private List<GitHubUser> users;

    private ProgressBar progressBar;
    private EditText editText;

    private String userQuery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_search_user, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        users = new ArrayList<>();
        usersList = root.findViewById(R.id.fragment_users_list);
        adapter = new UsersAdapter();
        usersList.setLayoutManager(linearLayoutManager);
        usersList.setAdapter(adapter);

        progressBar = root.findViewById(R.id.fragment_search_progressBar);

        editText = root.findViewById(R.id.fragment_search_edittext);
        Button searchButton = root.findViewById(R.id.fragment_search_button);
        searchButton.setOnClickListener(this);
        //usersList.setOnClickListener(this);

        usersList.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_users_list:
                ReposFragment reposFragment = new ReposFragment();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_COMPANY_NAME, userQuery);
                //bundle.putString(KEY_NUMBER_REPO, numberRepo);
                reposFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, reposFragment)
                            .addToBackStack("searchuserfragment")
                            .commit();
                }

                break;
            case R.id.fragment_search_button:
                userQuery = editText.getText().toString();
                if (getContext() != null) {
                    if (isInternetConnected(getContext())) {
                        loadUsers();
                    } else {
                        Toast.makeText(getContext(), R.string.internet_connection_error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public void loadUsers() {
        progressBar.setVisibility(View.VISIBLE);
        final GitHubUserCall apiService = GitHubApiClient.getClient().create(GitHubUserCall.class);
        Call<GitHubSearchResult> call = apiService.getUsers(userQuery);
        call.enqueue(new Callback<GitHubSearchResult>() {
            @Override
            public void onResponse(@NonNull Call<GitHubSearchResult> call, @NonNull Response<GitHubSearchResult> response) {
                if (response.body() != null) {
                    usersList.setVisibility(View.VISIBLE);
                    users.clear();
                    users.addAll(response.body().getItems());
                    adapter.addAll(users);
                    adapter.notifyDataSetChanged();
                    // TODO (8) --> IMPORTANT! Investigate why almost all fields coming with null (instead of login and avatar)
                    Toast.makeText(getContext(), getString(R.string.toast_show_results) + users.size(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GitHubSearchResult> call, Throwable t) {

            }
        });

    }
}
