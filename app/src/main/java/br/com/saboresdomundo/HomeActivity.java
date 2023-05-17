package br.com.saboresdomundo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.List;

import br.com.saboresdomundo.activity.AdvancedFilterActivity;
import br.com.saboresdomundo.activity.ListByFilterActivity;
import br.com.saboresdomundo.activity.NewPublicationActivity;
import br.com.saboresdomundo.adapter.CategoryRecycleViewAdapter;
import br.com.saboresdomundo.adapter.PublicationRecycleViewAdapter;
import br.com.saboresdomundo.model.Category;
import br.com.saboresdomundo.model.Publication;
import br.com.saboresdomundo.model.builder.CategoryBuilder;
import br.com.saboresdomundo.model.builder.PublicationViewBuilder;

public class HomeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buildCategories();
        buildTopWeek();
        buildSearchFilter();
        buildOptionAdvancedFilter();

        ImageView imageView = findViewById(R.id.option_profile);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NewPublicationActivity.class));
            }
        });

    }

    private void buildSearchFilter(){

        SearchView searchView = (SearchView) findViewById(R.id.search_filter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                openListByFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Aqui você pode lidar com a pesquisa à medida que o usuário digita o texto de pesquisa
                return false;
            }
        });
    }

    private void openListByFilter(String query){
        Intent intent = new Intent(this, ListByFilterActivity.class);
        intent.putExtra("filter", query);
        startActivity(intent);
    }

    private void buildOptionAdvancedFilter(){
        View viewAllCategories = findViewById(R.id.view_all_catagories);

        viewAllCategories.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AdvancedFilterActivity.class));
            }

        });
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

    // Build top week

    private void buildTopWeek(){
        RecyclerView rv = findViewById(R.id.top_week);

        List<Publication> publications = PublicationViewBuilder.buildDefultPublications();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        PublicationRecycleViewAdapter publicationRecycleViewAdapter = new PublicationRecycleViewAdapter(publications, this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(publicationRecycleViewAdapter);

    }

}