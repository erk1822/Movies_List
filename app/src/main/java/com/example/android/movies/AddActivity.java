package com.example.android.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by android on 4/3/18.
 */

public class AddActivity extends AppCompatActivity {

    private EditText titleView;
    private EditText codeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleView=findViewById(R.id.titleView);
        codeView=findViewById(R.id.codeView);

    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("TITLE", titleView.getText().toString());
        data.putExtra("CODE", codeView.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}

