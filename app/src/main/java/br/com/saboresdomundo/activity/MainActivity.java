package br.com.saboresdomundo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.saboresdomundo.HomeActivity;
import br.com.saboresdomundo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new HomeActivity())
                                .commit();
                        return true;
                    case R.id.menu_item2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new UserOptionsActivity())
                                .commit();
                        return true;
                    case R.id.menu_item3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new UserOptionsActivity())
                                .commit();
                        return true;
                    case R.id.menu_item4:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new UserOptionsActivity())
                                .commit();
                        return true;
                }
                return false;
            }
        });

    }
}