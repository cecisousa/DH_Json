package com.example.dh_json.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dh_json.R;
import com.example.dh_json.model.Noticia;
import com.example.dh_json.view.adapters.NoticiasRecyclerViewAdapter;
import com.example.dh_json.viewmodel.NoticiaViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoticiaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NoticiaViewModel viewModel;
    private NoticiasRecyclerViewAdapter adapter;
    private List<Noticia> noticias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        initViews();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.buscaNoticias();

        viewModel.retornaNoticias().observe(this, noticiasRetornada -> {
            adapter.update(noticiasRetornada);
        });

        viewModel.retornaValorLoading().observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewNoticias);
        progressBar = findViewById(R.id.progressBar);
        viewModel = ViewModelProviders.of(this).get(NoticiaViewModel.class);
        adapter = new NoticiasRecyclerViewAdapter(noticias);
    }
}
