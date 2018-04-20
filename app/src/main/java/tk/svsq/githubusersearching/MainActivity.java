package tk.svsq.githubusersearching;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tk.svsq.githubusersearching.fragment.SearchUserFragment;

public class MainActivity extends AppCompatActivity {

    SearchUserFragment fragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        fragment = (SearchUserFragment) fm.findFragmentByTag("searchUserFragment");

        if (fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, new SearchUserFragment(), "searchUserFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
        //fragment.getView().setVisibility(View.VISIBLE);
    }
}
