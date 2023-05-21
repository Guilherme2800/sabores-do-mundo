package br.com.saboresdomundo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.model.Publication;

public class NewPublicationActivity extends AppCompatActivity {

    private List<String> ingredientesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_publication);

        buildInserirIngrediente();
        buildSeekprice();
        buildSeekMinutes();
        buildReceitaProximaEtapa();
        buildButtonBack();
    }

    private void buildButtonBack(){

        ImageView image = findViewById(R.id.list_filter_back);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void buildReceitaProximaEtapa(){
        Button button = findViewById(R.id.receitaProximaEtapa);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Publication publication = new Publication();
                if(ingredientesList.size() == 0){
                    Toast.makeText(getApplicationContext(), "Insira os ingredientes.", Toast.LENGTH_SHORT).show();
                    return;
                }
                publication.setIngredients(ingredientesList);

                EditText receitaTitulo = findViewById(R.id.receitaTitulo);
                if(receitaTitulo.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Insira o titulo.", Toast.LENGTH_SHORT).show();
                    return;
                }
                publication.setTitle(receitaTitulo.getText().toString());

                EditText receitaDescricao = findViewById(R.id.receitaDescricao);
                if(receitaDescricao.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Insira a descrição.", Toast.LENGTH_SHORT).show();
                    return;
                }
                publication.setDescription(receitaDescricao.getText().toString());

                SeekBar receitaTempo = findViewById(R.id.receitaTempo);
                publication.setTime("Preparo: " + receitaTempo.getProgress() + " min");

                SeekBar receitaPrice = findViewById(R.id.receitaPrice);
                Integer price = receitaPrice.getProgress();
                publication.setPrice(BigDecimal.valueOf(Long.parseLong(price.toString())));

                Intent intent = new Intent(NewPublicationActivity.this, NewPublicationStep2Activity.class);
                intent.putExtra("publication", publication);
                startActivity(intent);
            }
        });
    }

    private void buildInserirIngrediente(){
        Button button = findViewById(R.id.inserirIngrediente);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ingredienteInput = findViewById(R.id.ingredienteInput);
                String ingrediente = ingredienteInput.getText().toString();
                if(ingrediente.isEmpty()){
                    return;
                }
                ingredientesList.add(ingrediente);

                ingredienteInput.setText("");

                ListView ingredientes = findViewById(R.id.ingredientes);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewPublicationActivity.this, android.R.layout.simple_list_item_1, ingredientesList);
                ingredientes.setAdapter(adapter);
            }
        });
    }

    private void buildSeekprice(){
        SeekBar seekBar = findViewById(R.id.receitaPrice);
        TextView textView = findViewById(R.id.inputReceitaPrice);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText("Preço em R$ " +progress);
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
        SeekBar seekBar = findViewById(R.id.receitaTempo);
        TextView textView = findViewById(R.id.inputReceitaTempo);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText("Tempo de preparo (minutos): " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
}