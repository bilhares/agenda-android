package br.com.alura.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.ListaAlunosActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by USER on 05/09/2017.
 */

public class AlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.alunos = alunos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alunos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Aluno aluno = alunos.get(i);

        LayoutInflater inflater  = LayoutInflater.from(context);
        View layoutView = view;

        if(layoutView == null) {
            layoutView = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        TextView campoNome = (TextView) layoutView.findViewById(R.id.item_nome);
        TextView campoTelefone = (TextView) layoutView.findViewById(R.id.item_telefone);
        ImageView campoFoto = (ImageView) layoutView.findViewById(R.id.item_foto);
        TextView campoEndereco = (TextView) layoutView.findViewById(R.id.item_endereco);
        TextView campoSite = (TextView) layoutView.findViewById(R.id.item_site);


        String caminhoFoto = aluno.getCaminhoFoto();

        if(caminhoFoto!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        if(campoSite!=null||campoEndereco!=null) {
            campoEndereco.setText(aluno.getEndereco());
            campoSite.setText(aluno.getSite());
        }
        return layoutView;
    }
}
