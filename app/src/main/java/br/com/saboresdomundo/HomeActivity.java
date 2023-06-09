package br.com.saboresdomundo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.saboresdomundo.activity.AdvancedFilterActivity;
import br.com.saboresdomundo.activity.ListByFilterActivity;
import br.com.saboresdomundo.activity.NewPublicationActivity;
import br.com.saboresdomundo.activity.UserOptionsActivity;
import br.com.saboresdomundo.adapter.CategoryRecycleViewAdapter;
import br.com.saboresdomundo.adapter.PublicationRecycleViewAdapter;
import br.com.saboresdomundo.config.FirebaseAuthListener;
import br.com.saboresdomundo.model.Category;
import br.com.saboresdomundo.model.Publication;
import br.com.saboresdomundo.model.Usuario;
import br.com.saboresdomundo.model.builder.CategoryBuilder;
import br.com.saboresdomundo.model.builder.PublicationViewBuilder;

public class HomeActivity extends AppCompatActivity{

    FirebaseAuth fbAuth;

    FirebaseAuthListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buildCategories();
        buildSearchFilter();
        buildOptionAdvancedFilter();

        ImageView imageView = findViewById(R.id.option_profile);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, UserOptionsActivity.class));
            }
        });

        this.fbAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("publications");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Publication> publications = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Publication publication = snapshot.getValue(Publication.class);
                    publications.add(publication);
                }

                buildTopWeek(publications);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Trate o erro, se necessário
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        fbAuth.addAuthStateListener(authListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        fbAuth.removeAuthStateListener(authListener);
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

    private void buildTopWeek(List<Publication> publications){
        RecyclerView rv = findViewById(R.id.top_week);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        PublicationRecycleViewAdapter publicationRecycleViewAdapter = new PublicationRecycleViewAdapter(publications, this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(publicationRecycleViewAdapter);

    }

}