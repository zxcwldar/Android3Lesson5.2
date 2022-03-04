package com.example.android3lesson2.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android3lesson2.data.local.room.database.AppDatabase;
import com.example.android3lesson2.data.local.room.models.CategoryModel;
import com.example.android3lesson2.data.local.room.models.WordModel;
import com.example.android3lesson2.models.network_model.Hits;
import com.example.android3lesson2.models.network_model.PixabayResponse;
import com.example.android3lesson2.models.network_model.RapidHits;
import com.example.android3lesson2.network.PixabayApi;
import com.example.android3lesson2.network.RapidApi;
import com.example.android3lesson2.network.RapidResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PixabayRepository {
    public static PixabayRepository pixabayRepository;
    AppDatabase appDatabase;
    PixabayApi api;
    RapidApi rapidApi;


    @Inject
    public PixabayRepository(PixabayApi api, RapidApi rapidApi) {
        this.api = api;
        this.rapidApi = rapidApi;
    }

    public MutableLiveData<List<Hits>> getImages(String word) {
        MutableLiveData<List<Hits>> imagesList = new MutableLiveData<>();

        api.getImages(word).enqueue(new Callback<PixabayResponse>() {
            @Override
            public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
                if (response.isSuccessful()) {
                    imagesList.postValue(response.body().getHits());

                }

            }

            @Override
            public void onFailure(Call<PixabayResponse> call, Throwable t) {

            }
        });
        return imagesList;


    }

    public LiveData<List<CategoryModel>> getCategories() {
        MutableLiveData<List<CategoryModel>> categoryList = new MutableLiveData<>();
        categoryList.setValue((List<CategoryModel>) appDatabase.getDatabase().categoryDao().getAll());
        return categoryList;
    }

    public LiveData<List<WordModel>> getWords(String userCategory) {
        MutableLiveData<List<WordModel>> wordsList = new MutableLiveData<>();
        wordsList.setValue((List<WordModel>) appDatabase.getDatabase().wordDao().getAll(userCategory));
        return wordsList;
    }

    public MutableLiveData<List<RapidHits>> getTranslation(String word) {
        MutableLiveData<List<RapidHits>> translationsList = new MutableLiveData<>();

        rapidApi.getTranslation(word, 1, 0, "translated-mymemory---translation-memory.p.rapidapi.com", "7fdf0c3215msh6a008d2bac47dfep1d5367jsne7b94b60412b").enqueue(new Callback<RapidResponse>() {
            @Override
            public void onResponse(Call<RapidResponse> call, Response<RapidResponse> response) {
                if (response.isSuccessful()) {
                    translationsList.postValue(response.body().getMatch());


                }
            }

            @Override
            public void onFailure(Call<RapidResponse> call, Throwable t) {

            }
        });
        return translationsList;
    }

}
