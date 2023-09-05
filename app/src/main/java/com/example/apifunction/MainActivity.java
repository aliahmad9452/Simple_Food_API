package com.example.apifunction;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apifunction.Retrofit.ApiService;
import com.example.apifunction.Retrofit.RetrofitClintInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private CategoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        // Set up RecyclerView with a GridLayoutManager
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns, you can adjust this as needed

        // Initialize Retrofit and the API service
        ApiService apiService = RetrofitClintInstance.getApiClient().create(ApiService.class);

        // Make the API call to retrieve categories
        Call<CategoryList> call = apiService.getCategories();

        // Show loading indicator
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                // Hide loading indicator
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    CategoryList categoryList = response.body();
                    if (categoryList != null) {
                        // Create and set the adapter for the RecyclerView
                        adapter = new CategoryListAdapter(categoryList.getCategories());
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    // Handle error (e.g., display an error message to the user)
                    String errorMessage = "API Error: " + response.message();
                    // Handle the error appropriately
                }
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                // Hide loading indicator
                progressBar.setVisibility(View.GONE);

                // Handle failure (e.g., display an error message to the user)
                String errorMessage = "API Request Failed: " + t.getMessage();
                // Handle the error appropriately
            }
        });
    }
}
