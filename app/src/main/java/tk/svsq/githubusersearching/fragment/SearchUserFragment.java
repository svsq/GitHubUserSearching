package tk.svsq.githubusersearching.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
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
import tk.svsq.githubusersearching.rest.GitHubCall;

import static tk.svsq.githubusersearching.util.CheckConnectivity.isInternetConnected;

public class SearchUserFragment extends Fragment implements View.OnClickListener {

    final GitHubCall apiService = GitHubApiClient.getClient().create(GitHubCall.class);

    public static final String KEY_CURRENT_LOGIN = "username";
    public static final String KEY_NUMBER_REPO = "number_repo";
    public static final int CODE_FORBIDDEN = 403;

    View root;

    Bundle args = new Bundle();

    RecyclerView usersList;
    UsersAdapter adapter;

    private LinearLayoutManager verticalLayoutManager;
    private LinearLayoutManager horizontalLayoutManager;
    private LinearLayoutManager currentLayoutManager;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    List<GitHubUser> users;
    List<GitHubUser> logins;

    ProgressBar progressBar;
    EditText editText;

    String userQuery;
    String currentLogin;

    TextView nothingFoundText;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            currentLayoutManager = horizontalLayoutManager;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            currentLayoutManager = verticalLayoutManager;
        }
        usersList.setLayoutManager(currentLayoutManager);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("key", (ArrayList<? extends Parcelable>) users);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        if(args != null) {
            users = args.getParcelableArrayList("key");
            adapter.clearAll();
            usersList.setAdapter(adapter);
            adapter.addAll(users);
            adapter.notifyDataSetChanged();
            usersList.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setRetainInstance(true);
        args = savedInstanceState;
        root = inflater.inflate(R.layout.fragment_search_user, container, false);
        usersList = root.findViewById(R.id.fragment_users_list);
        nothingFoundText = root.findViewById(R.id.fragment_search_nothing_found);
        nothingFoundText.setVisibility(View.GONE);

        if (savedInstanceState == null || !savedInstanceState.containsKey("key")) {
            users = new ArrayList<>();
            adapter = new UsersAdapter();
            usersList.setVisibility(View.GONE);

        } else {
            users = savedInstanceState.getParcelableArrayList("key");
            adapter.clearAll();
            usersList.setAdapter(adapter);
            adapter.addAll(users);
            adapter.notifyDataSetChanged();
            usersList.setVisibility(View.VISIBLE);
        }

        logins = new ArrayList<>();

        verticalLayoutManager = new LinearLayoutManager(getContext());
        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        usersList.setLayoutManager(verticalLayoutManager);

        usersList.setAdapter(adapter);

        progressBar = root.findViewById(R.id.fragment_search_progressBar);

        editText = root.findViewById(R.id.fragment_search_edittext);
        Button searchButton = root.findViewById(R.id.fragment_search_button);
        searchButton.setOnClickListener(this);

        progressBar.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fragment_search_button) {
            progressBar.setVisibility(View.VISIBLE);
            users.clear();
            logins.clear();
            adapter.clearAll();
            userQuery = editText.getText().toString();
            if (getContext() != null) {
                if (isInternetConnected(getContext())) {
                    loadSearchResults();
                } else {
                    Toast.makeText(getContext(), R.string.internet_connection_error,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void loadSearchResults() {

        Call<GitHubSearchResult> call = apiService.getUsersPaged(userQuery, null, "8");

        call.enqueue(new Callback<GitHubSearchResult>() {
            @Override
            public void onResponse(@NonNull Call<GitHubSearchResult> call,
                                   @NonNull Response<GitHubSearchResult> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getItems().isEmpty()) {
                            nothingFoundText.setVisibility(View.VISIBLE);
                        }
                        usersList.setVisibility(View.VISIBLE);
                        logins.addAll(response.body().getItems());
                        for (int i = 0; i < logins.size(); i++) {
                            loadUser(i);
                        }

                        adapter.setOnItemClickListener((view, login, position) -> {
                            currentLogin = login;
                            ReposFragment reposFragment = new ReposFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(KEY_CURRENT_LOGIN, currentLogin);
                            bundle.putString(KEY_NUMBER_REPO, users.get(position).getUserRepos());
                            reposFragment.setArguments(bundle);
                            //root.setVisibility(View.GONE);
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainer, reposFragment)
                                    .addToBackStack(null)
                                    .commit();
                        });

                        usersList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                                if(dy > 0) {
                                    visibleItemCount = currentLayoutManager.getChildCount();
                                    totalItemCount = currentLayoutManager.getItemCount();
                                    pastVisiblesItems = currentLayoutManager.findFirstVisibleItemPosition();

                                    if(loading) {
                                        if((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                            loading = false;
                                            // fetch new data
                                        }
                                    }
                                }

                                super.onScrolled(recyclerView, dx, dy);
                            }
                        });
                    }
                    progressBar.setVisibility(View.GONE);
                } else {
                    errorMessage(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GitHubSearchResult> call, Throwable t) {
                errorMessage();
            }
        });
    }

    public void loadUser(int position) {

        Call<GitHubUser> callUser = apiService.getUser(logins.get(position).getLogin());

        callUser.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(@NonNull Call<GitHubUser> call, @NonNull Response<GitHubUser> response) {

                if (response.code() != CODE_FORBIDDEN) {
                    if (response.body() != null) {
                        usersList.setVisibility(View.VISIBLE);
                        users.add(response.body());
                        adapter.add(users);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    errorMessage(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                errorMessage();
            }
        });
    }

    public void errorMessage(int code, String message) {
        if (code == CODE_FORBIDDEN) {
            Toast.makeText(getContext(), "Error code: " + code
                            + ". Attempts exceeded for non-autorized person. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error code: " + code + ". " + message,
                    Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.GONE);
    }

    public void errorMessage() {
        Toast.makeText(getContext(), "Failed!",
                Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }
}
