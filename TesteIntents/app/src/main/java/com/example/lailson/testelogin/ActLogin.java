package com.example.lailson.testelogin;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ActLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText editLogin;
    private EditText editSenha;
    private Button btnlogin;
    private Button btnRecuperar;
    private CheckBox checkBox;

    private String username;
    private String senha;

    private String MyPREFERENCES = "prefLogin";
    private String shareLogin = "loginKey";
    private String shareSenha = "senhaKey";

    SharedPreferences sharepreferences;
    SharedPreferences.Editor editor;

    Intent intent;
    Intent webIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        editLogin = (EditText) findViewById(R.id.editLogin);
        editSenha = (EditText) findViewById(R.id.editSenha);

        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(this);

        btnRecuperar = (Button) findViewById(R.id.btnRecuperar);
        btnRecuperar.setOnClickListener(this);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(this);

        sharepreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        editor = sharepreferences.edit();

        //Para carregamento Implicito. Abrir um servico como pagina web
        intent = new Intent(Intent.ACTION_VIEW);

    }

    @Override
    public void onClick(View v) {

        webIntent = new Intent(this,ActWeb.class);

        username = editLogin.getText().toString();
        senha = editSenha.getText().toString();
        final AlertDialog.Builder dlg = new AlertDialog.Builder(this);

        if(checkBox.isChecked()){
            if(username.trim().isEmpty() || senha.trim().isEmpty()){
                dlg.setMessage("Há dados em branco!");
                dlg.setNeutralButton("OKA",null); //o null é pra n acionar nada da janela de dialogo.
                dlg.show();
                checkBox.setChecked(false);
            }else{
                editor.putString(shareLogin,username);
                editor.putString(shareSenha,senha);
                //editor.putString(username+senha+"data",username+"/n"+senha);
                editor.commit();
            }

        }

        if (v == btnlogin){

            if(username.trim().isEmpty() || senha.trim().isEmpty()) {
                dlg.setMessage("Há dados em branco!");
                dlg.setNeutralButton("OKA", null); //o null é pra n acionar nada da janela de dialogo.
                dlg.show();
            }else {

                if (username.equals("admin") && senha.equals("admin")) {
                    editLogin.setText("");
                    editSenha.setText("");

//                  Carregamento Explicito
                    startActivity(webIntent);

                    //ABRE O NAVEGADOR DIRETAMENTE Carregamento Implícito
//                    intent.setData(Uri.parse("http://www.google.com"));
//                    this.startActivity(intent);


                    checkBox.setChecked(false);
                } else {
                    dlg.setMessage("Login ou senha incorreto!");
                    dlg.setNeutralButton("OKA", null); //o null é pra n acionar nada da janela de dialogo.
                    dlg.show();
                }
            }

        }

        if (v==btnRecuperar){
            sharepreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
            String usernameBack = sharepreferences.getString(shareLogin,"");
            String senhaBack= sharepreferences.getString(shareSenha,"");
            editLogin.setText(usernameBack);
            editSenha.setText(senhaBack);

            dlg.setMessage("Recuperado");
            dlg.setNeutralButton("OKA",null); //o null é pra n acionar nada da janela de dialogo.
            dlg.show();

        }
    }
}
