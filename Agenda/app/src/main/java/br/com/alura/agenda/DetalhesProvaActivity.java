package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.modelo.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        Intent intent = getIntent();

        Prova prova = (Prova) intent.getSerializableExtra("prova");

        TextView campoMateria = (TextView) findViewById(R.id.detalhes_prova_materia);
        TextView campoData = (TextView) findViewById(R.id.detalhes_prova_data);
        ListView topicos= (ListView) findViewById(R.id.detalhes_prova_topicos);


        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, prova.getTopicos());

        topicos.setAdapter(adapter);
    }
}
