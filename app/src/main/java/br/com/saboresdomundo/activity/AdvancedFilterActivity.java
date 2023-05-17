package br.com.saboresdomundo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.SelectCountryActivity;
import br.com.saboresdomundo.adapter.CategoryRecycleViewAdapter;
import br.com.saboresdomundo.model.Category;
import br.com.saboresdomundo.model.builder.CategoryBuilder;

public class AdvancedFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_filter);

        buildBackButton();
        buildCategories();
        buildCountries();
        buildOptionMaps();
        buildSeekMinutes();
        buildSeekprice();
    }

    private void buildBackButton(){
        ImageView imageView = findViewById(R.id.list_filter_back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void buildSeekprice(){
        SeekBar seekBar = findViewById(R.id.seekBarPrice);
        TextView textView = findViewById(R.id.price);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText("R$ " +progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void buildSeekMinutes(){
        SeekBar seekBar = findViewById(R.id.seekBarMinutes);
        TextView textView = findViewById(R.id.minutesText);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(progress + " minutos");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    private void buildOptionMaps(){
        CardView cardView = findViewById(R.id.open_map);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps();
            }
        });
    }

    private void openMaps(){
        startActivity(new Intent(this, SelectCountryActivity.class));
    }

    private void buildCategories(){
        RecyclerView rv = findViewById(R.id.category_advanced_horizontals);

        List<Category> categories = CategoryBuilder.buildDefultCategories();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        CategoryRecycleViewAdapter categoryRecycleViewAdapter = new CategoryRecycleViewAdapter(categories, this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(categoryRecycleViewAdapter);

    }

    private void buildCountries(){
        RecyclerView rv = findViewById(R.id.countries_horizontals);

        List<Category> categories = CategoryBuilder.buildCountryCategories();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        CategoryRecycleViewAdapter categoryRecycleViewAdapter = new CategoryRecycleViewAdapter(categories, this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(categoryRecycleViewAdapter);

    }
}