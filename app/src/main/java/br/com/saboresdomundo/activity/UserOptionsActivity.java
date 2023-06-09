package br.com.saboresdomundo.activity;

import static br.com.saboresdomundo.R.id.user_options_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class UserOptionsActivity extends Fragment {

    FirebaseAuth fbAuth;

    FirebaseAuthListener authListener;

    DatabaseReference drUser;

    Usuario user;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout do fragmento
        rootView = inflater.inflate(R.layout.activity_user_options, container, false);

        buildUserOptions();
        buildHome();

        Button buttonLogout = rootView.findViewById(R.id.logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSignOutClick(view);
            }
        });

        this.fbAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(getActivity());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser fbUser = fbAuth.getCurrentUser();
        drUser = database.getReference("users/" + fbUser.getUid());

        drUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario tempUser = dataSnapshot.getValue(Usuario.class);
                if (tempUser != null) {
                    UserOptionsActivity.this.user = tempUser;
                    TextView username = rootView.findViewById(R.id.userName);
                    username.setText("Olá " + tempUser.getName() + "!");
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

    public void buttonSignOutClick(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            Toast.makeText(getActivity(), "Erro!", Toast.LENGTH_SHORT).show();
        }
    }

    private void buildHome(){
        ImageView imageView = rootView.findViewById(R.id.option_home_user_options);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
    }

    private void buildUserOptions(){

        RecyclerView rv = rootView.findViewById(R.id.user_options_list);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        rv.addItemDecoration(dividerItemDecoration);

        List<UserOptions> options = UserOptionsBuilder.getOptions();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        UserOptionsRecycleViewAdapter publicationRecycleViewAdapter = new UserOptionsRecycleViewAdapter(options, getActivity());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(publicationRecycleViewAdapter);

    }
}