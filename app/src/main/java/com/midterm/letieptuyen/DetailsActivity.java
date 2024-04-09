package com.midterm.letieptuyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.commidtermletieptuyen.R;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textViewScore;
    private ArrayList<String> historyList;
    private HistoryAdapter historyAdapter;
    private MyViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Khởi tạo RecyclerView và TextView
        recyclerView = findViewById(R.id.recyclerView_result);
        textViewScore = findViewById(R.id.textView_score);

        // Khởi tạo ViewModelProvider để có thể lấy ViewModel
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // Lấy historyList từ Intent
        historyList = getIntent().getStringArrayListExtra("historyList");

        // Hiển thị historyList trong RecyclerView
        historyAdapter = new HistoryAdapter(historyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(historyAdapter);

        // Hiển thị điểm số
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        int correctAnswers = 0;
        for (String historyItem : historyList) {
            if (historyItem.endsWith("Correct")) {
                correctAnswers++;
            }
        }
        String score = "Score: " + correctAnswers + "/" + totalQuestions;
        textViewScore.setText(score);
    }
}