package com.guiajequi;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class Tab2Noticias extends Fragment {

    ListView lista;
    String[][] dados = {
            {"OFERTA DE EMPREGO - FAVINY MODAS", "A Loja Faviny Modas está buscando profissionais qualificados para contratar, traga seu currículo.", "01/09/2018"},
            {"CONCURSO EM CAPELINHA-MG", "A prefeitura de Capelinha-MG divulga resultado preliminar do concurso realizado no dia 26/08/2018", "31/08/2018"},
            {"EDITAL PARA CONCURSO EM CAPELINHA-MG", "A prefeitura de Capelinha-MG lança o edital para quem quer participar do concurso que vai ser realizado no dia 26/08/2018", "15/08/2018"},
            {"FESTA DE AGOSTO - MENDONÇA-MG", "Na primeira semana de agosto acontecerá a festa de agosto no distrito de Mendonça-MG.", "01/08/2018"}};
    int[] dadosImg = {R.drawable.noticias1,R.drawable.noticias2, R.drawable.noticias2,R.drawable.noticias2};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2noticias, container, false);
        lista = (ListView) rootView.findViewById(R.id.lvLista);
        lista.setAdapter(new AdaptadorListViewNoticias(rootView.getContext(),dados,dadosImg));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent visorDetalhes = new Intent(view.getContext(),detalheNoticia.class);
                visorDetalhes.putExtra("TIT", dados[position][0]);
                visorDetalhes.putExtra("DET",dados[position][1]);
                startActivity(visorDetalhes);
            }
        });
        return rootView;
    }
}
