package br.com.saboresdomundo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class HomeActivity extends Fragment {

    FirebaseAuth fbAuth;

    FirebaseAuthListener authListener;

    View rootView;

    Usuario user;
    DatabaseReference drUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_home, container, false);

        buildCategories();
        buildSearchFilter();
        buildOptionAdvancedFilter();

        this.fbAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(getActivity());

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

        FirebaseUser fbUser = fbAuth.getCurrentUser();
        drUser = database.getReference("users/" + fbUser.getUid());

        drUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario tempUser = dataSnapshot.getValue(Usuario.class);
                if (tempUser != null) {
                    HomeActivity.this.user = tempUser;
                    TextView username = rootView.findViewById(R.id.userNameHome);
                    username.setText(tempUser.getName() + "!");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        return rootView;
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

        SearchView searchView = (SearchView) rootView.findViewById(R.id.search_filter);
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
        Intent intent = new Intent(getActivity(), ListByFilterActivity.class);
        intent.putExtra("filter", query);
        startActivity(intent);
    }

    private void buildOptionAdvancedFilter(){
        View viewAllCategories = rootView.findViewById(R.id.view_all_catagories);

        viewAllCategories.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdvancedFilterActivity.class));
            }

        });
    }

    // Build Categories

    private void buildCategories(){
        RecyclerView rv = rootView.findViewById(R.id.horizontalRv);

        List<Category> categories = CategoryBuilder.buildDefultCategories();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        CategoryRecycleViewAdapter categoryRecycleViewAdapter = new CategoryRecycleViewAdapter(categories, getActivity());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(categoryRecycleViewAdapter);

    }

    private void buildTopWeek(List<Publication> publications){
        RecyclerView rv = rootView.findViewById(R.id.top_week);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        PublicationRecycleViewAdapter publicationRecycleViewAdapter = new PublicationRecycleViewAdapter(publications, getActivity());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(publicationRecycleViewAdapter);

    }

}