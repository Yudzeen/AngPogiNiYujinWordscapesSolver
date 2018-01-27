package ebj.awesome.angpoginiyujinwordscapessolver;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = MainFragment.newInstance();
        fragmentManager = getSupportFragmentManager();

        showMainFragment();
    }

    public void showMainFragment() {
        fragmentManager.beginTransaction().replace(
                R.id.layout_main_fragment_container,
                mainFragment,
                mainFragment.getTag()
        ).commit();
    }
}
