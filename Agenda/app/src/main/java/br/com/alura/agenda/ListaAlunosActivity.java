package br.com.alura.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.adapter.AlunosAdapter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formularioALuno = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(formularioALuno);
            }
        });


        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent irFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                irFormulario.putExtra("aluno", aluno);
                startActivity(irFormulario);
                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome(), Toast.LENGTH_SHORT).show();
            }
        });

        registerForContextMenu(listaAlunos);
    }

    private void carregarLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunosAdapter alunosAdapter = new AlunosAdapter(this, alunos);
    //    ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(alunosAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem itemLigar = menu.add("Ligar");

        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent intentLigar = new Intent(Intent.ACTION_CALL);
                intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));

                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                }else {
                    startActivity(intentLigar);
                }
                return false;
            }
        });

        MenuItem itemSms = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:"+aluno.getTelefone()));
        itemSms.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q="+aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);


        String siteAluno = aluno.getSite();
        if(!siteAluno.startsWith("http://")){
            siteAluno = "http://"+siteAluno;
        }

        MenuItem site = menu.add("Visitar Site");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(siteAluno));
        site.setIntent(intent);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                Toast.makeText(ListaAlunosActivity.this, "deletar: "+aluno.getNome(), Toast.LENGTH_SHORT).show();

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();

                carregarLista();
                return false;
            }
        });
    }

}
