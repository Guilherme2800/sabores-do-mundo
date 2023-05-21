package br.com.saboresdomundo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.adapter.CategoryRecycleViewAdapter;
import br.com.saboresdomundo.adapter.StepArrayAdapter;
import br.com.saboresdomundo.adapter.StepRecycleViewAdapter;
import br.com.saboresdomundo.model.Category;
import br.com.saboresdomundo.model.Publication;
import br.com.saboresdomundo.model.Steps;
import br.com.saboresdomundo.model.builder.CategoryBuilder;

public class NewPublicationStep2Activity extends AppCompatActivity {

    List<Steps> stepsList = new ArrayList<>();

    private Publication publication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_publication_step2);

        publication = (Publication) getIntent().getSerializableExtra("publication");
        buildSetps();
        buildInserirEtapa();
        buildProximaEtapa();
    }

    private void buildInserirEtapa(){
        Button button = findViewById(R.id.etapaInserir);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etapaDescricao = findViewById(R.id.etapaDescricao);

                Steps step = new Steps();
                step.setDescription(etapaDescricao.getText().toString());
                step.setStep(stepsList.size() + 1);

                stepsList.add(step);

                TextView currentStep = findViewById(R.id.current_step);
                currentStep.setText("Etapa " + (stepsList.size() + 1));

                etapaDescricao.setText("");

                ListView etapasInseridas = findViewById(R.id.etapasInseridas);
                StepArrayAdapter adapter = new StepArrayAdapter(NewPublicationStep2Activity.this, R.layout.step, stepsList);
                etapasInseridas.setAdapter(adapter);
            }
        });
    }

    private void buildSetps(){
        ListView etapasInseridas = findViewById(R.id.etapasInseridas);
        StepArrayAdapter adapter = new StepArrayAdapter(this, R.layout.step, stepsList);
        etapasInseridas.setAdapter(adapter);
    }

    private void buildProximaEtapa(){
        Button button = findViewById(R.id.receitaProximaEtapa2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStepsList();

                if(publication.getSteps().size() == 0){
                    Toast.makeText(getApplicationContext(), "Informe ao menos uma etapa.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(NewPublicationStep2Activity.this, NewPublicationStep3Activity.class);
                intent.putExtra("publication", publication);
                startActivity(intent);
            }
        });

    }

    private void setStepsList(){
        publication.setSteps(this.stepsList);
    }
}