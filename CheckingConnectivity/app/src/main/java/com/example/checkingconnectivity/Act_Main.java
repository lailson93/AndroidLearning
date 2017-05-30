package com.example.checkingconnectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Act_Main extends AppCompatActivity implements View.OnClickListener {

    private ConnectivityManager connManager;
    private  Button butao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        butao = (Button) findViewById(R.id.button);
        butao.setOnClickListener(this);
    }

    public void checkConnection(View v){
        final AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        connManager =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            //conectado
            dlg.setMessage("Conectado");
            dlg.setNeutralButton("OKA",null); //o null é pra n acionar nada da janela de dialogo.
            dlg.show();
        }else{
            //nao esta conectado
            dlg.setMessage("Não Conectado");
            dlg.setNeutralButton("OKA",null); //o null é pra n acionar nada da janela de dialogo.
            dlg.show();;
        }
    }

    @Override
    public void onClick(View v) {
        checkConnection(v);
    }
}
