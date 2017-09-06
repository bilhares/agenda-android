package br.com.alura.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.modelo.Prova;

/**
 * Created by USER on 06/09/2017.
 */

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);


        List<String> topicosPort = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        Prova provaPortugues = new Prova("Portugues", "25/05/2016", topicosPort);
        List<String> topicosMat = Arrays.asList("Equações", "Trigonometria", "Objeto indireto");
        Prova provaMat = new Prova("Matematica", "25/05/2016", topicosMat);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMat);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);

        final ListView lista = (ListView) view.findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prova prova = (Prova) lista.getItemAtPosition(i);
                Toast.makeText(getContext(), "Clicado: "+prova.getMateria(), Toast.LENGTH_SHORT).show();
//                Intent vaiParaDetalhes = new Intent(getContext(), DetalhesProvaActivity.class );
//                vaiParaDetalhes.putExtra("prova", prova);
//                startActivity(vaiParaDetalhes);

                ProvasActivity activity = (ProvasActivity) getActivity();
                activity.selecionaProva(prova);
            }
        });


        return view;
    }
}
