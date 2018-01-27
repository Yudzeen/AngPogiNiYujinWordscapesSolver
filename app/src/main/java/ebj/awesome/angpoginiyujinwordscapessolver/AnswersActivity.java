package ebj.awesome.angpoginiyujinwordscapessolver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswersActivity extends AppCompatActivity {

    private static final String TAG = AnswersActivity.class.getName();

    @BindView(R.id.tv_answers)
    TextView answersTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String inputText = getIntent().getStringExtra(Constants.INPUT_KEY);
        Log.i(TAG, "Input received: " + inputText);
        List<String> answers = getAnswers(inputText, getAllWords());
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < answers.size(); i++) {
            line.append(answers.get(i)).append("\n");
        }

        answersTextView.setText(line);
        answersTextView.setMovementMethod(new ScrollingMovementMethod());
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

    private List<String> getAllWords() {
        List<String> allWords = new ArrayList<>();
        try {
            InputStream is = getAssets().open("words.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                allWords.add(line);
            }
        } catch (IOException e) {
            Log.i(TAG, "File reading error.");
        }
        return allWords;
    }

    private List<String> getAnswers(String input, List<String> allWords) {
        List<String> answers = new ArrayList<>();
        for (int i = 0; i < allWords.size(); i++) {
            String possibleWord = allWords.get(i);
            if (possibleWord.length() > 2) {
                char[] possibleWordArray = possibleWord.toCharArray();
                boolean valid = true;
                boolean[] visited = new boolean[input.length()];
                for (int j = 0; j < possibleWordArray.length; j++) {
                    if (!contains(input, possibleWordArray[j], visited)) {
                        valid = false;
                    }
                }
                if (valid) {
                    answers.add(possibleWord);
                }
            }
        }
        return answers;
    }

    private boolean contains(String word, char letter, boolean[] visited) {
        char[] wordArray = word.toCharArray();
        for (int i = 0; i < wordArray.length; i++) {
            if (letter == wordArray[i] && !visited[i]) {
                visited[i] = true;
                return true;
            }
        }
        return false;
    }
}
