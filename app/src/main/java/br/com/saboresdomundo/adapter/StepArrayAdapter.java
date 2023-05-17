package br.com.saboresdomundo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.model.Steps;

public class StepArrayAdapter extends ArrayAdapter<Steps> {

    private Context mContext;
    private int mResource;

    public StepArrayAdapter(Context context, int resource, List<Steps> steps) {
        super(context, resource, steps);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        Steps step = getItem(position);

        TextView descricao = convertView.findViewById(R.id.etapaInseridaDescricao);
        descricao.setText(step.getDescription());

        TextView posicao = convertView.findViewById(R.id.etapaInseridaPosicao);
        posicao.setText("Etapa " + step.getStep());

        return convertView;
    }
}
