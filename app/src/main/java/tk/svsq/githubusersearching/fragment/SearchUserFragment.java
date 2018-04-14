package tk.svsq.githubusersearching.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.model.GitHubUser;
import tk.svsq.githubusersearching.rest.GitHubApiClient;
import tk.svsq.githubusersearching.rest.GitHubUserCall;

public class SearchUserFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_COMPANY_NAME = "username";
    public static final String KEY_NUMBER_REPO = "number_repo";

    private FrameLayout layout;
    private ImageView userAvatar;
    private TextView userNameText;
    private TextView userLocationText;
    private TextView userBlogText;
    private ProgressBar progressBar;
    private EditText editText;
    private Button searchButton;

    private String login;
    private String userName;
    private String companyName;
    private String numberRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_search_user, container, false);
        layout = root.findViewById(R.id.companyLayout);
        userAvatar = root.findViewById(R.id.user_avatar);
        userNameText = root.findViewById(R.id.user_name);
        userLocationText = root.findViewById(R.id.user_location);
        userBlogText = root.findViewById(R.id.user_blog);
        progressBar = root.findViewById(R.id.fragment_search_progressBar);
        editText = root.findViewById(R.id.fragment_search_edittext);
        searchButton = root.findViewById(R.id.fragment_search_button);
        searchButton.setOnClickListener(this);
        layout.setOnClickListener(this);

        layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        return root;
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (manager != null) {
            activeNetwork = manager.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.companyLayout:
                ReposFragment reposFragment = new ReposFragment();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_COMPANY_NAME, userName);
                bundle.putString(KEY_NUMBER_REPO, numberRepo);
                reposFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, reposFragment)
                            .addToBackStack("searchuserfragment")
                            .commit();
                }

                break;
            case R.id.fragment_search_button:
                userName = editText.getText().toString();
                if (getContext() != null) {
                    if (isInternetConnected(getContext())) {
                        loadData();
                    } else {
                        Toast.makeText(getContext(), R.string.internet_connection_error, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
        }

    }

    public void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        final GitHubUserCall apiService = GitHubApiClient.getClient().create(GitHubUserCall.class);
        Call<GitHubUser> call = apiService.getUser(userName);
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(@NonNull Call<GitHubUser> call, @NonNull Response<GitHubUser> response) {
                if (response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    if (response.body().getUserName() != null) {  // TODO (5): Try to fix this statement
                        userNameText.setText(response.body().getUserName());
                    }
                    userLocationText.setText(response.body().getUserLocation());
                    userBlogText.setText(response.body().getUserBlog());
                    Picasso.get()
                            .load(response.body().getUserAvatar())
                            .resize(150, 150)
                            .into(userAvatar);
                    companyName = response.body().getUserName();
                    numberRepo = response.body().getUserRepos();
                    login = response.body().getLogin();
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {

            }
        });

    }
}
