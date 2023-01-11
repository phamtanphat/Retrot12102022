package com.example.retrot12102022;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    Button btnRandom;
    CatService catService;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.image_view_cat);
        btnRandom = findViewById(R.id.button_random_cat);

        retrofit = createRetrofit();
        catService = retrofit.create(CatService.class);

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catService.randomCatApi().enqueue(new Callback<CatDTO>() {
                    @Override
                    public void onResponse(Call<CatDTO> call, Response<CatDTO> response) {
                        CatDTO catDTO = response.body();
                        Glide.with(MainActivity.this)
                            .load(catDTO.getMessage())
                            .centerCrop()
                            .into(img);
                    }

                    @Override
                    public void onFailure(Call<CatDTO> call, Throwable t) {

                    }
                });
            }
        });
    }

    private Retrofit createRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .protocols(Arrays.asList(Protocol.HTTP_1_1))
                .build();

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        return new Retrofit.Builder()
                .baseUrl("https://dog.ceo/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }
}
