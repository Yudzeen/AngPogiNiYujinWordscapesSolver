package ebj.awesome.angpoginiyujinwordscapessolver;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;

import static ebj.awesome.angpoginiyujinwordscapessolver.Constants.*;

public class AnswerActivity extends AppCompatActivity {

    private static final String TAG = AnswerActivity.class.getName();

    private FragmentManager fragmentManager;
    private AnswerFragment answerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String inputText = getIntent().getStringExtra(INPUT_KEY);

        answerFragment = AnswerFragment.newInstance(inputText);
        fragmentManager = getSupportFragmentManager();

        showAnswersFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showAnswersFragment() {
        fragmentManager.beginTransaction().replace(
                R.id.layout_answer_fragment_container,
                answerFragment,
                answerFragment.getTag()
        ).commit();
    }

}
