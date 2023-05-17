package br.com.saboresdomundo.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.activity.FilterByCategory;
import br.com.saboresdomundo.model.Steps;

public class StepRecycleViewAdapter extends RecyclerView.Adapter<StepRecycleViewAdapter.MyHolder> {
    List<Steps> data;

    AppCompatActivity currentActivity;

    public StepRecycleViewAdapter(List<Steps> data, AppCompatActivity currentActivity) {
        this.data = data;
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public StepRecycleViewAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(currentActivity).inflate(R.layout.step, parent, false);
        return new StepRecycleViewAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepRecycleViewAdapter.MyHolder holder, int position) {
        holder.step_description.setText(data.get(holder.getAdapterPosition()).getDescription());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        EditText step_description;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            step_description = itemView.findViewById(R.id.etapaDescricao);
        }
    }

    public void onItemClick(String category, AppCompatActivity currentActivity) {

        Intent intent = new Intent(currentActivity, FilterByCategory.class);
        intent.putExtra("category", category);

        currentActivity.startActivity(intent);
    }

}
