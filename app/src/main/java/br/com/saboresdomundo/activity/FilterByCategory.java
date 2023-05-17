package br.com.saboresdomundo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.stream.Collectors;

import br.com.saboresdomundo.HomeActivity;
import br.com.saboresdomundo.R;
import br.com.saboresdomundo.adapter.PublicationRecycleViewAdapter;
import br.com.saboresdomundo.model.Category;
import br.com.saboresdomundo.model.Publication;
import br.com.saboresdomundo.model.builder.PublicationViewBuilder;

public class FilterByCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by_category);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Category category = (Category) getIntent().getSerializableExtra("category");

        ImageView imageView = findViewById(R.id.imageCategory);
        imageView.setImageResource(category.getImgFilter());

        TextView textView = findViewById(R.id.category_title);
        textView.setText(category.getName());

        ImageView optionHome = findViewById(R.id.option_home);
        optionHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buildList();
        buildButtonBack();
    }

    private void buildButtonBack(){
        ImageView back = findViewById(R.id.list_filter_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
    }

    public void backToHome(){
        startActivity(new Intent(this, HomeActivity.class));
    }

    private void buildList(){
        RecyclerView rv = findViewById(R.id.publication_by_filter_category);

        Category category = (Category) getIntent().getSerializableExtra("category");

        List<Publication> publications = PublicationViewBuilder.buildDefultPublications().stream()
                .filter(pub -> pub.getCategory().contains(category))
                .collect(Collectors.toList());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        PublicationRecycleViewAdapter publicationRecycleViewAdapter = new PublicationRecycleViewAdapter(publications, this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(publicationRecycleViewAdapter);

    }
}