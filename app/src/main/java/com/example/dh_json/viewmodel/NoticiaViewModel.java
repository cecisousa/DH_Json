package com.example.dh_json.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dh_json.model.Noticia;
import com.example.dh_json.repository.NoticiaRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NoticiaViewModel extends AndroidViewModel {
    private MutableLiveData<List<Noticia>> listaNoticia = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private NoticiaRepository repository = new NoticiaRepository();
    private CompositeDisposable disposable = new CompositeDisposable();

    public NoticiaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Noticia>> retornaNoticias() {
        return this.listaNoticia;
    }

    public LiveData<Boolean> retornaValorLoading() {
        return this.loading;
    }

    public void buscaNoticias () {
        disposable.add(
                repository.obterListaNoticias(getApplication().getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> loading.setValue(true))
                .doAfterTerminate(() -> loading.setValue(false))
                .subscribe(noticiaResposta -> {
                    listaNoticia.setValue(noticiaResposta.getNoticias());
                }, throwable -> {
                    Log.i("LOG", "Busca notícias" + throwable.getMessage());
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
