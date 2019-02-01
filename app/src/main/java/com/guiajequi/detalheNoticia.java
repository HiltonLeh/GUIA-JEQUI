package com.guiajequi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class detalheNoticia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_noticia);
        TextView titulo = (TextView) findViewById(R.id.textDetalheNoticia);
        TextView resumo = (TextView) findViewById(R.id.textResumoNoticia);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            titulo.setText(b.getString("TIT"));
            resumo.setText(b.getString("DET"));
        }
    }
}
