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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.adapter.RecyclerViewClickListener;
import tk.svsq.githubusersearching.adapter.UsersAdapter;
import tk.svsq.githubusersearching.model.GitHubSearchResult;
import tk.svsq.githubusersearching.model.GitHubUser;
import tk.svsq.githubusersearching.rest.GitHubApiClient;
import tk.svsq.githubusersearching.rest.GitHubCall;

import static tk.svsq.githubusersearching.util.CheckConnectivity.isInternetConnected;

public class SearchUserFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_CURRENT_LOGIN = "username";
    public static final int CODE_FORBIDDEN = 403;
    //public static final String KEY_NUMBER_REPO = "number_repo";

    private RecyclerView usersList;
    private UsersAdapter adapter;

    private List<GitHubUser> users;

    private ProgressBar progressBar;
    private EditText editText;

    private String userQuery;
    private String currentLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_search_user, container, false);

        users = new ArrayList<>();
        usersList = root.findViewById(R.id.fragment_users_list);
        usersList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new UsersAdapter();

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
        if (view.getId() == R.id.fragment_search_button) {
                userQuery = editText.getText().toString();
                if (getContext() != null) {
                    if (isInternetConnected(getContext())) {
                        loadUsers();
                    } else {
                        Toast.makeText(getContext(), R.string.internet_connection_error,
                                Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    public void loadUsers() {
        progressBar.setVisibility(View.VISIBLE);
        users.clear();
        adapter.clearAll();
        final GitHubCall apiService = GitHubApiClient.getClient().create(GitHubCall.class);
        Call<GitHubSearchResult> call = apiService.getUsers(userQuery);
        call.enqueue(new Callback<GitHubSearchResult>() {
            @Override
            public void onResponse(@NonNull Call<GitHubSearchResult> call,
                                   @NonNull final Response<GitHubSearchResult> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        for (int i = 0; i < response.body().getItems().size(); i++) {
                            Call<GitHubUser> callUser = apiService.getUser(response.body()
                                    .getItems().get(i).getLogin());
                            callUser.enqueue(new Callback<GitHubUser>() {
                                @Override
                                public void onResponse(@NonNull Call<GitHubUser> call2,
                                                       @NonNull Response<GitHubUser> response2) {
                                    if (response2.code() != CODE_FORBIDDEN) {
                                        if (response.isSuccessful()) {
                                            if (response2.body() != null) {
                                                usersList.setVisibility(View.VISIBLE);
                                                users.add(response2.body());
                                                adapter.add(users);
                                                adapter.notifyDataSetChanged();
                                            } else {
                                                Toast.makeText(getContext(), String.valueOf(response.code()),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }


                                        adapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View currentView, String loginOut, String loginIn) {
                                                currentLogin = loginOut;
                                                ReposFragment reposFragment = new ReposFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putString(KEY_CURRENT_LOGIN, currentLogin);
                                                //bundle.putString(KEY_NUMBER_REPO, numberRepo);
                                                reposFragment.setArguments(bundle);
                                                if (getFragmentManager() != null) {
                                                    getFragmentManager().beginTransaction()
                                                            .replace(R.id.fragmentContainer, reposFragment)
                                                            .addToBackStack("searchuserfragment")
                                                            .commit();
                                                }
                                            }
                                        });

                                    } else {
                                        Toast.makeText(getContext(), "Error: " + String.valueOf(response2.code())
                                                        + " " + response2.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<GitHubUser> call, Throwable t) {

                                }
                            });
                        }


                    }
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), String.valueOf(response.code()),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GitHubSearchResult> call, Throwable t) {

            }
        });


    }
}
