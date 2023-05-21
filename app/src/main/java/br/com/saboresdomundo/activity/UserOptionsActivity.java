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

import java.util.List;

import br.com.saboresdomundo.HomeActivity;
import br.com.saboresdomundo.R;
import br.com.saboresdomundo.adapter.PublicationRecycleViewAdapter;
import br.com.saboresdomundo.adapter.UserOptionsRecycleViewAdapter;
import br.com.saboresdomundo.model.Publication;
import br.com.saboresdomundo.model.UserOptions;
import br.com.saboresdomundo.model.builder.PublicationViewBuilder;
import br.com.saboresdomundo.model.builder.UserOptionsBuilder;

public class UserOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);

        buildUserOptions();
        buildHome();
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