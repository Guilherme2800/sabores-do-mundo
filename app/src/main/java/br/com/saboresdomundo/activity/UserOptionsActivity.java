package br.com.saboresdomundo.activity;

import static br.com.saboresdomundo.R.id.user_options_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import br.com.saboresdomundo.HomeActivity;
import br.com.saboresdomundo.R;
import br.com.saboresdomundo.adapter.PublicationRecycleViewAdapter;
import br.com.saboresdomundo.adapter.UserOptionsRecycleViewAdapter;
import br.com.saboresdomundo.config.FirebaseAuthListener;
import br.com.saboresdomundo.model.Publication;
import br.com.saboresdomundo.model.UserOptions;
import br.com.saboresdomundo.model.Usuario;
import br.com.saboresdomundo.model.builder.PublicationViewBuilder;
import br.com.saboresdomundo.model.builder.UserOptionsBuilder;

public class UserOptionsActivity extends AppCompatActivity {

    FirebaseAuth fbAuth;

    FirebaseAuthListener authListener;

    DatabaseReference drUser;

    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);

        buildUserOptions();
        buildHome();

        Button buttonLogout = findViewById(R.id.logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSignOutClick(view);
            }
        });

        this.fbAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser fbUser = fbAuth.getCurrentUser();
        drUser = database.getReference("users/" + fbUser.getUid());

        drUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario tempUser = dataSnapshot.getValue(Usuario.class);
                if (tempUser != null) {
                    UserOptionsActivity.this.user = tempUser;
                    TextView username = findViewById(R.id.userName);
                    username.setText("Olá " + tempUser.getName() + "!");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
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

    public void buttonSignOutClick(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mAuth.signOut();
            startActivity(new Intent(UserOptionsActivity.this, LoginActivity.class));
        } else {
            Toast.makeText(UserOptionsActivity.this, "Erro!", Toast.LENGTH_SHORT).show();
        }
    }

    private void buildHome(){
        ImageView imageView =findViewById(R.id.option_home_user_options);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserOptionsActivity.this, HomeActivity.class));
            }
        });
    }

    private void buildUserOptions(){

        RecyclerView rv = findViewById(R.id.user_options_list);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        rv.addItemDecoration(dividerItemDecoration);

        List<UserOptions> options = UserOptionsBuilder.getOptions();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        UserOptionsRecycleViewAdapter publicationRecycleViewAdapter = new UserOptionsRecycleViewAdapter(options, this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(publicationRecycleViewAdapter);

    }
}