package com.example.smartfoods;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartfoods.ocr.OcrCaptureActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";
    private TextView statusMessage;
    private TextView textValue;
    private ImageView logo;
    private Button setPreferences;
    private Drawable logoDrawable;
    private String preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPreferences = (Button) findViewById(R.id.set_preferences);
        //setPreferences = (Button) findViewById(R.id.set_preferences);
/*        statusMessage = (TextView)findViewById(R.id.status_message);
        textValue = (TextView)findViewById(R.id.text_value);*/


        setPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Button is working",Toast.LENGTH_SHORT).show();
                // Launch After Capture Activity
                Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
                intent.putExtra("preferences", preferences);

                startActivity(intent);

            }
        });
        //logo = (ImageView)findViewById(R.id.Logo);

        //logoDrawable = getResources().getDrawable(R.drawable.final_logo);
        //logo.setImageDrawable(logoDrawable);

        preferences = getPreferencesFromActivity();


        findViewById(R.id.read_text).setOnClickListener(this);
    }

    private String getPreferencesFromActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("preferences") != null) {
                return extras.getString("preferences");
            }
        }
        return "0000000000";
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_text) {
            // launch Ocr capture activity.
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, true);
            intent.putExtra(OcrCaptureActivity.UseFlash, false);
            intent.putExtra("preferences", preferences);
            startActivityForResult(intent, RC_OCR_CAPTURE);
        }
    }
}