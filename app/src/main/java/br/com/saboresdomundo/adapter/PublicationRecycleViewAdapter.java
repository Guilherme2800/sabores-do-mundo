package br.com.saboresdomundo.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.model.Publication;

public class PublicationRecycleViewAdapter extends RecyclerView.Adapter<PublicationRecycleViewAdapter.MyPublicationHolder> {
    List<Publication> data;

    AppCompatActivity currentActivity;

    public PublicationRecycleViewAdapter(List<Publication> data, AppCompatActivity currentActivity) {
        this.data = data;
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public MyPublicationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(currentActivity).inflate(R.layout.publicationview, parent, false);
        return new MyPublicationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPublicationHolder holder, int position) {
        holder.publication_title.setText(data.get( holder.getAdapterPosition()).getTitle());
        holder.publication_description.setText(data.get( holder.getAdapterPosition()).getDescription());
        holder.publication_time.setText(data.get( holder.getAdapterPosition()).getTime());
        if(data.get( holder.getAdapterPosition()).getImg() != 0){
            holder.publication_img.setImageResource(data.get( holder.getAdapterPosition()).getImg());
        }else{
            holder.publication_img.setImageBitmap(BitmapFactory.decodeFile(data.get( holder.getAdapterPosition()).getImgPath()));
        }

        holder.main_publication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick(data.get( holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyPublicationHolder extends RecyclerView.ViewHolder {
        TextView publication_title;
        TextView publication_description;
        TextView publication_time;
        ImageView publication_img;

        CardView main_publication;

        public MyPublicationHolder(@NonNull View itemView) {
            super(itemView);
            publication_title = itemView.findViewById(R.id.food_title_top_week);
            publication_description = itemView.findViewById(R.id.food_description_top_week);
            publication_time = itemView.findViewById(R.id.food_time_top_week);
            publication_img = itemView.findViewById(R.id.food_image_top_week);
            main_publication = itemView.findViewById(R.id.main_publications);
        }
    }

    public void onItemClick(Publication publication){
        Toast.makeText(currentActivity, publication.getTitle(), Toast.LENGTH_SHORT).show();
    }

}
