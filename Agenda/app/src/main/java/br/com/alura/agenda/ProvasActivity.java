package br.com.alura.agenda;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        tx.replace(R.id.frame_principal, new ListaProvasFragment());

        if(estaNoModoPaisagem()){
            tx.replace(R.id.frame_secundaria, new DetalhesProvaFragment());
        }
        tx.commit();

//        List<String> topicosPort = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
//        Prova provaPortugues = new Prova("Portugues", "25/05/2016", topicosPort);
//        List<String> topicosMat = Arrays.asList("Equações", "Trigonometria", "Objeto indireto");
//        Prova provaMat = new Prova("Matematica", "25/05/2016", topicosMat);
//
//        List<Prova> provas = Arrays.asList(provaPortugues, provaMat);
//        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(this, android.R.layout.simple_list_item_1, provas);
//
//        final ListView lista = (ListView) findViewById(R.id.provas_lista);
//        lista.setAdapter(adapter);
//
//        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Prova prova = (Prova) lista.getItemAtPosition(i);
//                Toast.makeText(ProvasActivity.this, "Clicado: "+prova.getMateria(), Toast.LENGTH_SHORT).show();
//                Intent vaiParaDetalhes = new Intent(ProvasActivity.this, DetalhesProvaActivity.class );
//                vaiParaDetalhes.putExtra("prova", prova);
//                startActivity(vaiParaDetalhes);
//            }
//        });
    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        if(!estaNoModoPaisagem()){
            FragmentTransaction tx = manager.beginTransaction();
            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            Bundle parametros = new Bundle();
            parametros.putSerializable("prova", prova);
            detalhesProvaFragment.setArguments(parametros);
            tx.replace(R.id.frame_principal, detalhesProvaFragment);
            tx.addToBackStack(null);
            tx.commit();
        }else{
            DetalhesProvaFragment detalhesFrag = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundaria);
            detalhesFrag.populaCamposCom(prova);
        }
    }
}
