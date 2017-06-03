package com.example.assynctasktest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_download;
    private ImageView myImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImageView = (ImageView) findViewById(R.id.myImageView);

        btn_download = (Button) findViewById(R.id.btn_download);
        btn_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        DownloadImage dImage = new DownloadImage(myImageView);
        String linkImage = "http://inacio.com.br/wp-content/uploads/2013/02/logo_ufc-virtual.jpg";
        dImage.execute(linkImage);
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap>{

        ImageView imageView;
        public DownloadImage(ImageView imageView){
            this.imageView=imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String url = params[0];
            try {
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {
            myImageView.setImageBitmap(result);
        }

    }

}
