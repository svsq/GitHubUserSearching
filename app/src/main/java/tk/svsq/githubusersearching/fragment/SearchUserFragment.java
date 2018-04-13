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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tk.svsq.githubusersearching.R;

public class SearchUserFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_COMPANY_NAME = "username";
    public static final String KEY_NUMBER_REPO = "number_repo";

    private RelativeLayout layout;
    private ImageView userAvatar;
    private TextView userNameText;
    private TextView userLocationText;
    private TextView userBlogText;
    private ProgressBar progressBar;
    private EditText editText;
    private Button searchButton;

    private String userName;
    private String companyName;
    private String numberRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_search_user, container, false);
        layout = root.findViewById(R.id.fragment_search_layout);
        userAvatar = root.findViewById(R.id.user_avatar);
        userNameText = root.findViewById(R.id.user_name);
        userLocationText = root.findViewById(R.id.user_location);
        userBlogText = root.findViewById(R.id.user_blog);
        progressBar = root.findViewById(R.id.fragment_search_progressBar);
        editText = root.findViewById(R.id.fragment_search_edittext);
        searchButton = root.findViewById(R.id.fragment_search_button);
        searchButton.setOnClickListener(this);

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
        FragmentManager fragmentManager = getFragmentManager();
        ReposFragment reposFragment = new ReposFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_COMPANY_NAME, companyName);
        bundle.putString(KEY_NUMBER_REPO, numberRepo);
        reposFragment.setArguments(bundle);
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, reposFragment)
                    .addToBackStack("userfragment")
                    .commit();
        }
    }

    public void loadData() {
        //TODO (3): Add data loading method.

    }
}
