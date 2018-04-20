package tk.svsq.githubusersearching;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tk.svsq.githubusersearching.fragment.SearchUserFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        SearchUserFragment fragment = (SearchUserFragment) fm.findFragmentByTag("searchUserFragment");

        if (fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, new SearchUserFragment(), "searchUserFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }
}
