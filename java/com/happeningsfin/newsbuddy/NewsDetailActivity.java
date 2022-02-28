package com.happeningsfin.newsbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    String title,desc,content,imageURL,url;
    private TextView titleTV,descTV,contentTV;
    private ImageView newsIV;
    private Button readNewsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageURL = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");
        titleTV = findViewById(R.id.TVTitle);
        descTV = findViewById(R.id.TVDesc);
        contentTV = findViewById(R.id.TVContent);
        newsIV = findViewById(R.id.IVNews2);
        readNewsBtn = findViewById(R.id.BtnReadNews);
        titleTV.setText(title);
        descTV.setText(desc);
        contentTV.setText(content);
        Picasso.get().load(imageURL).into(newsIV);
        readNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }
}