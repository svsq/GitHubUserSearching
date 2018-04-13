package tk.svsq.githubusersearching;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tk.svsq.githubusersearching.fragment.SearchUserFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SearchUserFragment searchUserFragment = new SearchUserFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, searchUserFragment, "searchUserFragment");
        fragmentTransaction.commit();
    }
}
