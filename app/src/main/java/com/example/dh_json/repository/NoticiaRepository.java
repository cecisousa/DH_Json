package com.example.dh_json.repository;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.dh_json.model.NoticiaResposta;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.Observable;

public class NoticiaRepository {

    public Observable<NoticiaResposta> obterListaNoticias(Context context) {

        try {
            AssetManager manager = context.getAssets();
            InputStream inputStream = manager.open("noticias.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            Gson gson = new Gson();
            NoticiaResposta resposta = gson.fromJson(reader, NoticiaResposta.class);
            return Observable.just(resposta);

        } catch (Exception ex) {
            ex.printStackTrace();
            return Observable.error(ex.getCause());
        }
    }
}
