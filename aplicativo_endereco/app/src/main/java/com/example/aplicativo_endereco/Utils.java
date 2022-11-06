package com.example.aplicativo_endereco;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public PessoaObj getInformacao(String end){
        String json;
        PessoaObj retorno;
        json = NetworkUtils.getJSONFromAPI(end);
        Log.i("Resultado", json);
        retorno = parseJson(json);

        return retorno;
    }

    private PessoaObj parseJson(String json){
        try {

            PessoaObj pessoa = new PessoaObj();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //Date data;

            JSONObject objArray = array.getJSONObject(0);

            JSONObject obj = objArray.getJSONObject("login");;
            pessoa.setUsername(obj.getString("username"));
            pessoa.setSenha(obj.getString("password"));

            //Nome da pessoa é um objeto, instancia um novo JSONObject
            JSONObject nome = objArray.getJSONObject("name");
            pessoa.setNome(nome.getString("first"));
            pessoa.setSobrenome(nome.getString("last"));

            //Endereco tambem é um Objeto
            JSONObject endereco = obj.getJSONObject("location");
            pessoa.setEndereco(endereco.getString("street"));
            pessoa.setEstado(endereco.getString("state"));
            pessoa.setCidade(endereco.getString("city"));

            //Coordenadas
            JSONObject coordenadas = obj.getJSONObject("coordinates");
            pessoa.setLatitude(coordenadas.getString("latitude"));
            pessoa.setLongitude(coordenadas.getString("longitude"));

            //Imagem eh um objeto
            JSONObject foto = obj.getJSONObject("picture");
            pessoa.setFoto(baixarImagem(foto.getString("large")));

            return pessoa;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap baixarImagem(String url) {
        try{
            URL endereco;
            InputStream inputStream;
            Bitmap imagem; endereco = new URL(url);
            inputStream = endereco.openStream();
            imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return imagem;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
