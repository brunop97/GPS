package com.example.aplicativo_endereco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Bitmap;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView nome;
    private TextView sobrenome;
    private TextView email;
    private TextView endereco;
    private TextView cidade;
    private TextView estado;
    private TextView username;
    private TextView senha;
    private TextView nascimento;
    private TextView telefone;
    private TextView latitude;
    private TextView longitude;
    private ImageView foto;
    private ProgressDialog load;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = (TextView)findViewById(R.id.textView9);
        sobrenome = (TextView)findViewById(R.id.textView10);
        email = (TextView)findViewById(R.id.textView11);
        endereco = (TextView)findViewById(R.id.textView12);
        cidade = (TextView)findViewById(R.id.textView13);
        estado = (TextView)findViewById(R.id.textView14);
        username = (TextView)findViewById(R.id.textView16);
        senha = (TextView)findViewById(R.id.textView17);
        nascimento = (TextView)findViewById(R.id.textView19);
        telefone = (TextView)findViewById(R.id.textView20);
        latitude = (TextView)findViewById(R.id.textView15);
        longitude = (TextView)findViewById(R.id.textView21);
        foto = (ImageView)findViewById(R.id.imageView);
    }

    public void acionaRandom(View view){
        GetJson download = new GetJson();
        //Chama Async Task
        download.execute();
    }


    private class GetJson extends AsyncTask<Void, Void, PessoaObj> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(MainActivity.this,
                    "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected PessoaObj doInBackground(Void... params) {
            Utils util = new Utils();

            return util.getInformacao("https://randomuser.me/api/1.4");
        }

        @Override
        protected void onPostExecute(PessoaObj pessoa){
            //nome.setText(pessoa.getNome().substring(0,1).toUpperCase()+pessoa.getNome().substring(1));
            //sobrenome.setText(pessoa.getSobrenome().substring(0,1).toUpperCase()+pessoa.getSobrenome().substring(1));
            //email.setText(pessoa.getEmail());
            //endereco.setText(pessoa.getEndereco());
            //cidade.setText(pessoa.getCidade().substring(0,1).toUpperCase()+pessoa.getCidade().substring(1));
            //estado.setText(pessoa.getEstado());
            username.setText(pessoa.getUsername());
            senha.setText(pessoa.getSenha());
            //nascimento.setText(pessoa.getNascimento());
            //telefone.setText(pessoa.getTelefone());
            //latitude.setText(pessoa.getLatitude());
            //longitude.setText(pessoa.getLongitude());
            //foto.setImageBitmap(pessoa.getFoto());
            load.dismiss();
        }

    }
}
