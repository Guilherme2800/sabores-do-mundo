package br.com.saboresdomundo.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.activity.FilterByCategory;
import br.com.saboresdomundo.model.Category;

public class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryRecycleViewAdapter.MyHolder> {
    List<Category> data;

    FragmentActivity currentActivity;

    public CategoryRecycleViewAdapter(List<Category> data, FragmentActivity currentActivity) {
        this.data = data;
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(currentActivity).inflate(R.layout.category, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.category_name.setText(data.get( holder.getAdapterPosition()).getName());
        holder.category_img.setImageResource(data.get( holder.getAdapterPosition()).getImg());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick(data.get( holder.getAdapterPosition()), currentActivity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView category_name;

        ImageView category_img;

        CardView cardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            category_name = itemView.findViewById(R.id.category_name);
            category_img = itemView.findViewById(R.id.category_img);
            cardView = itemView.findViewById(R.id.main_category);
        }
    }

    public void onItemClick(Category category, FragmentActivity currentActivity){

        Intent intent = new Intent(currentActivity, FilterByCategory.class);
        intent.putExtra("category", category);

        currentActivity.startActivity(intent);
    }

}
