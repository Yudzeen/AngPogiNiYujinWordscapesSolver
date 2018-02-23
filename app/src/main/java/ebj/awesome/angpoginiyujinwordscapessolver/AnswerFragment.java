package ebj.awesome.angpoginiyujinwordscapessolver;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static ebj.awesome.angpoginiyujinwordscapessolver.Constants.INPUT_KEY;

public class AnswerFragment extends Fragment {

    private RecyclerView recyclerView;
    private AnswerAdapter adapter;

    private List<String> answer;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AnswerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String input = getArguments().getString(INPUT_KEY);
        answer = getAnswers(input, getAllWords());
    }

    public static AnswerFragment newInstance(String input) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putString(INPUT_KEY, input);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_list, container, false);

        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            Context context = recyclerView.getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, getResources().getConfiguration().orientation));
            adapter = new AnswerAdapter(answer);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private List<String> getAllWords() {
        List<String> allWords = new ArrayList<>();
        try {
            InputStream is = getActivity().getAssets().open("words.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                allWords.add(line);
            }
        } catch (IOException e) {
            Log.i(getClass().getName(), "File reading error.");
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
                        break;
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
