package com.example.aplicativo_endereco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

// Implement OnMapReadyCallback.
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
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
    private ImageView foto;
    private ProgressDialog load;

    public Double latitude;
    public Double longitude;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.mapView, mapFragment).commit();

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
        foto = (ImageView)findViewById(R.id.imageView);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        Log.i("Lat2", String.valueOf(latitude));
        Log.i("Long2", String.valueOf(longitude));
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
            nome.setText(pessoa.getNome().substring(0,1).toUpperCase()+pessoa.getNome().substring(1));
            sobrenome.setText(pessoa.getSobrenome().substring(0,1).toUpperCase()+pessoa.getSobrenome().substring(1));
            email.setText(pessoa.getEmail());
            endereco.setText(pessoa.getEndereco());
            cidade.setText(pessoa.getCidade().substring(0,1).toUpperCase()+pessoa.getCidade().substring(1));
            estado.setText(pessoa.getEstado().substring(0,1).toUpperCase()+pessoa.getEstado().substring(1));
            username.setText(pessoa.getUsername());
            senha.setText(pessoa.getSenha());
            nascimento.setText(pessoa.getNascimento().substring(0,10));
            telefone.setText(pessoa.getTelefone());
            foto.setImageBitmap(pessoa.getFoto());
            load.dismiss();

            latitude = Double.parseDouble(pessoa.getLatitude());
            longitude = Double.parseDouble(pessoa.getLongitude());

            Log.i("Lat", String.valueOf(latitude));
            Log.i("Long", String.valueOf(longitude));
        }
    }


   


}
