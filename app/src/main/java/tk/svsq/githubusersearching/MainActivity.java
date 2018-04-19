package tk.svsq.githubusersearching;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tk.svsq.githubusersearching.fragment.SearchUserFragment;

public class MainActivity extends AppCompatActivity {

    private SearchUserFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        fragment = (SearchUserFragment) fm.findFragmentByTag("searchUserFragment");

        if(fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new SearchUserFragment(), "searchUserFragment")
                    .commit();
        }
    }
}
