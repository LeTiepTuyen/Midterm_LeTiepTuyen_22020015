package com.midterm.letieptuyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commidtermletieptuyen.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private Button buttonTrue;
    private Button buttonFalse;
    ImageButton buttonPrevious;
    ImageButton buttonNext;

    public Map<String, Boolean> questionListWithAnswer;
    private Button buttonSubmit;
    private DatabaseHelper databaseHelper;
    private MyViewModel viewModel;
    private List<String> questionList;
    private int currentQuestionIndex = 0;
    private List<String> historyResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        textViewQuestion = findViewById(R.id.textView_question);
        buttonTrue = findViewById(R.id.button_true);
        buttonFalse = findViewById(R.id.button_false);
        buttonPrevious = findViewById(R.id.button_previous);
        buttonNext = findViewById(R.id.button_next);
        buttonSubmit = findViewById(R.id.button_submit);

        databaseHelper = new DatabaseHelper(this, getApplicationInfo().dataDir + "/databases/database-midterm");


        // Create ViewModel
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // Initialize historyResult list
        historyResult = new ArrayList<>();

        // Generate random question list
        generateQuestionList();

        // Observe current question and update TextView
        viewModel.getCurrentQuestion().observe(this, question -> textViewQuestion.setText(viewModel.getCurrentQuestion().getValue()));

        // Set listener for True `button`
        buttonTrue.setOnClickListener(v -> {
            viewModel.setUserAnswer(true);
            viewModel.setIsTrueButtonSelected(true);
            viewModel.setIsFalseButtonSelected(false);
        });

        // Set listener for False button
        buttonFalse.setOnClickListener(v -> {
            viewModel.setUserAnswer(false);
            viewModel.setIsTrueButtonSelected(false);
            viewModel.setIsFalseButtonSelected(true);
        });

        // Observer to update button states and background colors
        viewModel.getIsTrueButtonSelected().observe(this, isTrueSelected -> {
            if (isTrueSelected != null && isTrueSelected) {
                buttonTrue.setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                buttonTrue.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        });

        viewModel.getIsFalseButtonSelected().observe(this, isFalseSelected -> {
            if (isFalseSelected != null && isFalseSelected) {
                buttonFalse.setBackgroundColor(getResources().getColor(R.color.green));
            } else {
                buttonFalse.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        });


        // Set listener for Previous button
        buttonPrevious.setOnClickListener(v -> {
            String currentQuestionValue = textViewQuestion.getText().toString();
            String[] questions = viewModel.getQuestionListWithAnswer().keySet().toArray(new String[0]);
            int currentIndex = -1;
            // Tìm vị trí của câu hỏi hiện tại trong danh sách câu hỏi
            for (int i = 0; i < questions.length; i++) {
                if (questions[i].equals(currentQuestionValue)) {
                    currentIndex = i;
                    break;
                }
            }
            // Di chuyển tới câu hỏi trước đó
            int newIndex = (currentIndex - 1 + questions.length) % questions.length;
            String newQuestion = questions[newIndex];
            textViewQuestion.setText(newQuestion);
            viewModel.getCurrentQuestion().setValue(newQuestion);
        });

        // Set listener for Next button
        buttonNext.setOnClickListener(v -> {
            String currentQuestionValue = textViewQuestion.getText().toString();
            String[] questions = viewModel.getQuestionListWithAnswer().keySet().toArray(new String[0]);
            int currentIndex = -1;
            // Tìm vị trí của câu hỏi hiện tại trong danh sách câu hỏi
            for (int i = 0; i < questions.length; i++) {
                if (questions[i].equals(currentQuestionValue)) {
                    currentIndex = i;
                    break;
                }
            }
            // Di chuyển tới câu hỏi kế tiếp
            int newIndex = (currentIndex + 1) % questions.length;
            String newQuestion = questions[newIndex];
            textViewQuestion.setText(newQuestion);
            viewModel.getCurrentQuestion().setValue(newQuestion);
        });





        // Set listener for Submit button
        buttonSubmit.setOnClickListener(v -> {
            Boolean userAnswer = viewModel.getUserAnswer().getValue();
            if (userAnswer == null) {
                Toast.makeText(MainActivity.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
                return;
            }

            String question = viewModel.getCurrentQuestion().getValue();
            Boolean correctAnswer = viewModel.getCorrectAnswer(question);
            String result = userAnswer.equals(correctAnswer) ? "Correct" : "Wrong";

            String historyItem = question + "/" + userAnswer + "/" + result;
            historyResult.add(historyItem);
            Toast.makeText(MainActivity.this, "Result: " + result, Toast.LENGTH_SHORT).show();


            // Add question and answer to SQLite database
            boolean isInserted = databaseHelper.addQuestion(question, userAnswer.toString());
            if (isInserted) {
                Toast.makeText(MainActivity.this, "Question saved to SQLite", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to save question", Toast.LENGTH_SHORT).show();
            }


            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putStringArrayListExtra("historyList", new ArrayList<>(historyResult));
//            generateQuestionList();
            intent.putExtra("totalQuestions", questionListWithAnswer.size());

            startActivity(intent);
        });
    }

    private void generateQuestionList() {
        questionListWithAnswer = new HashMap<>();
        questionListWithAnswer.put("1.Trai dat la hinh cau?", true);
        questionListWithAnswer.put("2.Donal Trump la ty phu giau nhat the gioi?", false);
        questionListWithAnswer.put("3.Facebook la mang xa hoi lon nhat the gioi", true);

        viewModel.setQuestionListWithAnswer(questionListWithAnswer);
        viewModel.setCurrentQuestion(questionListWithAnswer.keySet().iterator().next());
    }



}