package com.example.aplicativo_endereco;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Utils{
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

            JSONObject objArray = array.getJSONObject(0);

            JSONObject nome = objArray.getJSONObject("name");
            JSONObject endereco = objArray.getJSONObject("location");
            JSONObject login = objArray.getJSONObject("login");;
            JSONObject foto = objArray.getJSONObject("picture");
            JSONObject rua = endereco.getJSONObject("street");
            JSONObject date = objArray.getJSONObject("dob");
            JSONObject coord = endereco.getJSONObject("coordinates");

            pessoa.setNome(nome.getString("first"));
            pessoa.setSobrenome(nome.getString("last"));
            pessoa.setEmail(objArray.getString("email"));
            pessoa.setEndereco(rua.getString("name"));
            pessoa.setCidade(endereco.getString("city"));
            pessoa.setEstado(endereco.getString("state"));
            pessoa.setUsername(login.getString("username"));
            pessoa.setSenha(login.getString("password"));
            pessoa.setTelefone(objArray.getString("phone"));
            pessoa.setNascimento(date.getString("date"));
            pessoa.setFoto(baixarImagem(foto.getString("large")));
            pessoa.setLatitude(coord.getString("latitude"));
            pessoa.setLongitude(coord.getString("longitude"));

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
