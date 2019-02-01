package com.guiajequi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AdaptadorListViewNoticias extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context contexto;
    String[][] dados;
    int[] dadosImg;

    public AdaptadorListViewNoticias(Context contexto, String[][] dados, int[] imagens) {
        this.contexto = contexto;
        this.dados = dados;
        this.dadosImg = imagens;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.noticias_lista, null);
        TextView titulo = (TextView) vista.findViewById(R.id.textTitulo);
        TextView resumo = (TextView) vista.findViewById(R.id.textResumo);
        TextView data = (TextView) vista.findViewById(R.id.textData);
        ImageView imagem = (ImageView) vista.findViewById(R.id.imageNoticia);
        titulo.setText(dados[i][0]);
        resumo.setText(dados[i][1]);
        data.setText((dados[i][2]));
        imagem.setImageResource(dadosImg[i]);

//        imagem.setTag(i);
//        imagem.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent visorImagem = new Intent(contexto, VisorImagem.class);
//                visorImagem.putExtra("IMG", dadosImg[(Integer)v.getTag()]);
//                contexto.startActivity(visorImagem);
//            }
//
//
//        });

        return vista;
    }

    @Override
    public int getCount() {
        return dadosImg.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
