package br.com.saboresdomundo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.stream.Collectors;

import br.com.saboresdomundo.HomeActivity;
import br.com.saboresdomundo.R;
import br.com.saboresdomundo.adapter.PublicationRecycleViewAdapter;
import br.com.saboresdomundo.model.Publication;
import br.com.saboresdomundo.model.builder.PublicationViewBuilder;

public class ListByFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_by_filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        RecyclerView rv = findViewById(R.id.publication_by_filter);

        String filter = getIntent().getStringExtra("filter");

        List<Publication> publications = PublicationViewBuilder.buildDefultPublications().stream()
                .filter(pub -> pub.getTitle().toLowerCase().contains(filter.toLowerCase()) || pub.getDescription().toLowerCase().contains(filter.toLowerCase()))
                .collect(Collectors.toList());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        PublicationRecycleViewAdapter publicationRecycleViewAdapter = new PublicationRecycleViewAdapter(publications, this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(publicationRecycleViewAdapter);

    }

}