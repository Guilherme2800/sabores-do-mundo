package br.com.saboresdomundo.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.activity.FilterByCategory;
import br.com.saboresdomundo.model.Category;
import br.com.saboresdomundo.model.UserOptions;

public class UserOptionsRecycleViewAdapter extends RecyclerView.Adapter<UserOptionsRecycleViewAdapter.MyHolder>{

    List<UserOptions> data;

    FragmentActivity currentActivity;

    public UserOptionsRecycleViewAdapter(List<UserOptions> data, FragmentActivity currentActivity) {
        this.data = data;
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public UserOptionsRecycleViewAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(currentActivity).inflate(R.layout.user_options, parent, false);
        return new UserOptionsRecycleViewAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOptionsRecycleViewAdapter.MyHolder holder, int position) {
        holder.option_title.setText(data.get( holder.getAdapterPosition()).getTitle());
        holder.option_description.setText(data.get( holder.getAdapterPosition()).getDescription());
        holder.option_img.setImageResource(data.get( holder.getAdapterPosition()).getImg());

        holder.layout.setOnClickListener(new View.OnClickListener() {
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
        TextView option_title;

        ImageView option_img;

        TextView option_description;

        ConstraintLayout layout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            option_title = itemView.findViewById(R.id.user_option_title);
            option_description = itemView.findViewById(R.id.user_option_description);
            option_img = itemView.findViewById(R.id.user_option_img);
            layout = itemView.findViewById(R.id.user_options_layout);
        }
    }

    public void onItemClick(UserOptions option, FragmentActivity currentActivity){
        Intent intent = new Intent(currentActivity, option.getClazz());
        currentActivity.startActivity(intent);
    }

}
