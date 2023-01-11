package com.example.retrot12102022;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pphat on 1/11/2023.
 */
public interface CatService {
    @GET("api/breeds/image/random")
    Call<CatDTO> randomCatApi();
}
