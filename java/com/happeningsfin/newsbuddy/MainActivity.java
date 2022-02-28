package com.happeningsfin.newsbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    //175dd9e51e414bb8924d56db307c3011

    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private NewsRVAdapter newsRVAdapter;
    private CategoryRVAdapter categoryRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV = findViewById(R.id.RVNews);
        categoryRV = findViewById(R.id.RVCategories);
        loadingPB = findViewById(R.id.PBLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();

    }

    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All","https://media.istockphoto.com/photos/television-streaming-multimedia-wall-concept-picture-id1287677376?b=1&k=20&m=1287677376&s=170667a&w=0&h=wvu0lKn4WbfHtKId83KzrHvGmBP7zn7ZwGEWmU99HWE="));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8dGVjaG5vbG9neXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://media.istockphoto.com/photos/vaccine-in-laboratory-flu-shot-and-covid19-vaccination-picture-id1289345741?b=1&k=20&m=1289345741&s=170667a&w=0&h=oG8iaDNP4rOLSgXWfeSziU3Vyu6KJS9Hn2ORohzSsRg="));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://media.istockphoto.com/photos/various-sport-equipments-on-grass-picture-id949190736?b=1&k=20&m=949190736&s=170667a&w=0&h=f3ofVqhbmg2XSVOa3dqmvGtHc4VLA_rtbboRGaC8eNo="));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://images.unsplash.com/photo-1503428593586-e225b39bddfe?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8Z2VuZXJhbCUyMG5ld3N8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://images.unsplash.com/39/lIZrwvbeRuuzqOoWJUEn_Photoaday_CSD%20(1%20of%201)-5.jpg?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YnVzaW5lc3N8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://media.istockphoto.com/photos/the-best-fans-a-band-could-want-picture-id502131959?b=1&k=20&m=502131959&s=170667a&w=0&h=8efBzeqguArASWGYfWhK5EdgNIJvZm-nvZ42i9DsS1A="));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://images.unsplash.com/photo-1447452001602-7090c7ab2db3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjh8fGhlYWx0aHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=600&q=60"));

        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=us&category="+ category +"&apikey=175dd9e51e414bb8924d56db307c3011";
        String url = "https://newsapi.org/v2/top-headlines?country=us&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=175dd9e51e414bb8924d56db307c3011";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else{
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for(int i=0; i< articles.size(); i++){

                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get news", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onCategoryClick(int position) {
            String category = categoryRVModalArrayList.get(position).getCategory();
            getNews(category);
    }
}