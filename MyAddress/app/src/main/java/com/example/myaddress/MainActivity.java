package com.example.myaddress;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_cep;
    private EditText editText_rua;
    private EditText editText_compl;
    private EditText editText_bairro;
    private EditText editText_cidade;
    private EditText editText_uf;
    private Button btn_buscar;

    private AlertDialog.Builder dlg;

    Endereco endereco1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_cep = (EditText) findViewById(R.id.editText_cep);
        editText_rua = (EditText) findViewById(R.id.editText_rua);
        editText_bairro = (EditText) findViewById(R.id.editText_bairro);
        editText_compl = (EditText) findViewById(R.id.editText_compl);
        editText_cidade = (EditText) findViewById(R.id.editText_cidade);
        editText_uf = (EditText) findViewById(R.id.editText_uf);
        btn_buscar = (Button) findViewById(R.id.btn_buscar);
        btn_buscar.setOnClickListener(this);

        endereco1 = new Endereco();
        dlg = new AlertDialog.Builder(this);

    }

    @Override
    public void onClick(View v) {

        String atualCep = editText_cep.getText().toString();
        SearchTask st=new SearchTask();

        if (atualCep.trim().isEmpty()){
            dlg.setMessage("Preencha o campo de CEP");
            dlg.setNeutralButton("OKA",null); //o null é pra n acionar nada da janela de dialogo.
            dlg.show();
        }else{
            st.execute(atualCep);
        }

    }

    public class SearchTask extends AsyncTask<String, String, Endereco> {

        protected Endereco doInBackground(String... params) {
            endereco1 = getAddress(params[0]);
            return endereco1;
        }

        protected void onProgressUpdate(String result) {}

        protected void onPostExecute(Endereco endereco1){
            if (endereco1!=null) {
                editText_rua.setText(endereco1.getRua().toString());
                editText_compl.setText(endereco1.getComplemento().toString());
                editText_bairro.setText(endereco1.getBairro().toString());
                editText_cidade.setText(endereco1.getCidade().toString());
                editText_uf.setText(endereco1.getUf().toString());
            }else{
                dlg.setMessage("CEP Inválido");
                dlg.setNeutralButton("OKA",null);
                dlg.show();
            }
        }

    }

    public Endereco getAddress(String cep){
        try {
//            String cep = params[0];
            String link = "http://viacep.com.br/ws/" + cep + "/json/";
            URL url = new URL(link);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String data = null;
            String content = "";

            while ((data = reader.readLine()) != null) {
                content += data + "\n";
                System.out.print(content);
            }

            JSONObject jsonObject = new JSONObject(content);
            endereco1.setRua(jsonObject.getString("logradouro"));
            endereco1.setComplemento(jsonObject.getString("complemento"));
            endereco1.setBairro(jsonObject.getString("bairro"));
            endereco1.setCidade(jsonObject.getString("localidade"));
            endereco1.setUf(jsonObject.getString("uf"));

            return endereco1;

        }catch (Exception e){}

        return null;
    }
}

