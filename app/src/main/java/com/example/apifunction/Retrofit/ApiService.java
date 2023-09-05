package com.example.apifunction.Retrofit;

import com.example.apifunction.CategoryList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    // Define your API endpoints here

    @GET("categories.php")
    Call<CategoryList> getCategories();
}
