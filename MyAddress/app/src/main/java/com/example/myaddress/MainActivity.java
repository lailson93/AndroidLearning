package com.example.myaddress;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_rua;
    private EditText editText_compl;
    private EditText editText_bairro;
    private EditText editText_cidade;
    private EditText editText_uf;
    private Button btn_buscar;

    Endereco endereco = new Endereco();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_rua = (EditText) findViewById(R.id.editText_rua);
        editText_bairro = (EditText) findViewById(R.id.editText_bairro);
        editText_compl = (EditText) findViewById(R.id.editText_compl);
        editText_cidade = (EditText) findViewById(R.id.editText_cidade);
        editText_uf = (EditText) findViewById(R.id.editText_uf);
        btn_buscar = (Button) findViewById(R.id.btn_buscar);
        btn_buscar.setOnClickListener(this);

        SearchTask st=new SearchTask(endereco);
        String link1="http://viacep.com.br/ws/60115222/json/";
        st.execute(link1);

    }

    @Override
    public void onClick(View v) {

        editText_rua.setText(endereco.getRua().toString());
        editText_compl.setText(endereco.getComplemento().toString());
        editText_bairro.setText(endereco.getBairro().toString());
        editText_cidade.setText(endereco.getCidade().toString());
        editText_uf.setText(endereco.getUf().toString());

    }


    public class SearchTask extends AsyncTask<String, String, Object> {

        Endereco endereco1 = new Endereco();

        public SearchTask(Object object){
            this.endereco1 = (Endereco) object;
        }

        protected Endereco doInBackground(String... params) {
            try {
                String link = (String) params[0];
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
                }
                Log.v("web",content);

                //MANIPULACAO DO JSON RECEBIDO PELO REST
                JSONObject jsonObject=new JSONObject(content);

                String rua=jsonObject.getString("logradouro");
                endereco1.setRua(rua);

                String complemento=jsonObject.getString("complemento");
                endereco1.setComplemento(complemento);

                String bairro = jsonObject.getString("bairro");
                endereco1.setBairro(bairro);

                String cidade = jsonObject.getString("localidade");
                endereco1.setCidade(cidade);

                String uf = jsonObject.getString("uf");
                endereco1.setUf(uf);


                String cep=jsonObject.getString("cep");
                Log.v("json",cep);

                return endereco1;


            } catch (Exception e) {return null;}
        }
        protected void onProgressUpdate(String result) {}

        protected Object onPostExecute(Endereco endereco1){
            this.endereco1=endereco1;
            return endereco1;
        }

    }

}


