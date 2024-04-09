package com.midterm.letieptuyen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commidtermletieptuyen.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<String> historyList;

    // Constructor để khởi tạo adapter với danh sách lịch sử
    public HistoryAdapter(ArrayList<String> historyList) {
        this.historyList = historyList;
    }

    // Phương thức này được gọi khi RecyclerView cần tạo một ViewHolder mới
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout của một mục trong RecyclerView từ tệp items_list.xml
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent, false);
        // Trả về một ViewHolder mới với layout được inflate
        return new HistoryViewHolder(itemView);
    }

    // Phương thức này được gọi khi RecyclerView cần cập nhật nội dung của một ViewHolder đã tồn tại
    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        // Lấy dữ liệu của mục lịch sử tại vị trí position
        String currentItem = historyList.get(position);

        // Phân chia chuỗi để lấy question và result
        String[] parts = currentItem.split("/");
        String question = parts[0];
        String result = parts[2];

        // Gán dữ liệu vào các TextView trong ViewHolder
        holder.textQuestion.setText(question);
        holder.textResult.setText(result);
    }

    // Phương thức này trả về số lượng phần tử trong danh sách lịch sử
    @Override
    public int getItemCount() {
        return historyList.size();
    }

    // ViewHolder cho mỗi mục trong RecyclerView
    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView textQuestion;
        public TextView textResult;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các TextView từ layout
            textQuestion = itemView.findViewById(R.id.questions);
            textResult = itemView.findViewById(R.id.results);
        }
    }

    // Phương thức này được sử dụng để cập nhật danh sách lịch sử trong adapter
    public void updateHistory(ArrayList<String> newHistoryList) {
        // Xóa toàn bộ dữ liệu cũ
        historyList.clear();
        // Thêm tất cả dữ liệu mới vào danh sách lịch sử
        historyList.addAll(newHistoryList);
        // Thông báo cho RecyclerView biết rằng dữ liệu đã thay đổi
        notifyDataSetChanged();
    }
}
