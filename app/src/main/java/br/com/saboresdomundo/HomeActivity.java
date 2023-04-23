package br.com.saboresdomundo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.saboresdomundo.model.Category;
import br.com.saboresdomundo.model.PublicationView;
import br.com.saboresdomundo.model.SelectItemCategory;
import br.com.saboresdomundo.model.builder.CategoryBuilder;
import br.com.saboresdomundo.model.builder.PublicationViewBuilder;

public class HomeActivity extends AppCompatActivity implements SelectItemCategory{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buildCategories();
        buildTopWeek();
        buildButtonAllCategories();

    }

    private void buildButtonAllCategories(){
        View viewAllCategories = findViewById(R.id.view_all_catagories);

        viewAllCategories.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openAllCategory();
            }

        });
    }

    private void openAllCategory() {
        startActivity(new Intent(this, AllCategoryActivity.class));
    }

    // Build Categories

    private void buildCategories(){
        RecyclerView rv = findViewById(R.id.horizontalRv);

        List<Category> categories = CategoryBuilder.buildDefultCategories();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        CategoryRecycleViewAdapter categoryRecycleViewAdapter = new CategoryRecycleViewAdapter(categories, this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(categoryRecycleViewAdapter);

    }
    @Override
    public void onItemClick(Category category){
        Toast.makeText(this, category.getName(), Toast.LENGTH_SHORT).show();
    }

    class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryRecycleViewAdapter.MyHolder> {
        List<Category> data;

        SelectItemCategory selectItemCategory;

        public CategoryRecycleViewAdapter(List<Category> data, SelectItemCategory selectItemCategory) {
            this.data = data;
            this.selectItemCategory = selectItemCategory;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.category, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.category_name.setText(data.get( holder.getAdapterPosition()).getName());
            holder.category_img.setImageResource(data.get( holder.getAdapterPosition()).getImg());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectItemCategory.onItemClick(data.get( holder.getAdapterPosition()));
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

    }

    // Build top week

    private void buildTopWeek(){
        RecyclerView rv = findViewById(R.id.top_week);

        List<PublicationView> publicationViews = PublicationViewBuilder.buildDefultPublications();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        PublicationRecycleViewAdapter publicationRecycleViewAdapter = new PublicationRecycleViewAdapter(publicationViews, this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(publicationRecycleViewAdapter);

    }

    @Override
    public void onItemClick(PublicationView publicationView){
        Toast.makeText(this, publicationView.getTitle(), Toast.LENGTH_SHORT).show();
    }

    class PublicationRecycleViewAdapter extends RecyclerView.Adapter<PublicationRecycleViewAdapter.MyPublicationHolder> {
        List<PublicationView> data;

        SelectItemCategory selectItemCategory;

        public PublicationRecycleViewAdapter(List<PublicationView> data, SelectItemCategory selectItemCategory) {
            this.data = data;
            this.selectItemCategory = selectItemCategory;
        }

        @NonNull
        @Override
        public MyPublicationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.top_week, parent, false);
            return new MyPublicationHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyPublicationHolder holder, int position) {
            holder.publication_title.setText(data.get( holder.getAdapterPosition()).getTitle());
            holder.publication_description.setText(data.get( holder.getAdapterPosition()).getDescription());
            holder.publication_time.setText(data.get( holder.getAdapterPosition()).getTime());
            holder.publication_img.setImageResource(data.get( holder.getAdapterPosition()).getImg());

            holder.main_publication.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectItemCategory.onItemClick(data.get( holder.getAdapterPosition()));
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

            ConstraintLayout main_publication;

            public MyPublicationHolder(@NonNull View itemView) {
                super(itemView);
                publication_title = itemView.findViewById(R.id.food_title_top_week);
                publication_description = itemView.findViewById(R.id.food_description_top_week);
                publication_time = itemView.findViewById(R.id.food_time_top_week);
                publication_img = itemView.findViewById(R.id.food_image_top_week);
                main_publication = itemView.findViewById(R.id.main_publication);
            }
        }

    }
}