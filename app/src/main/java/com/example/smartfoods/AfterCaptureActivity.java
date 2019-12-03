package com.example.smartfoods;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartfoods.ocr.OcrCaptureActivity;

import java.util.ArrayList;
import java.util.Locale;

public class AfterCaptureActivity extends AppCompatActivity {

    ArrayList<String> itemList;
    Button anotherPicture;
    Button textToSpeechButton;
    ImageView icon;
    TextView titleText;
    TextParser parser = new TextParser();
    LinearLayout badIngredientsBox;
    Drawable check;
    Drawable negative;
    String preferences;
    TextToSpeech ts;
    StringBuilder speechText = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_capture);

        anotherPicture = (Button) findViewById(R.id.AnotherPicture);
        preferences = getIntent().getExtras().getString("preferences");
        Log.i("Prefs:", "In the after capture act " + preferences);

        itemList = (ArrayList<String>) getIntent().getSerializableExtra("ING-LIST");
        icon = (ImageView) findViewById(R.id.icon);
        titleText = (TextView) findViewById(R.id.TitleText);
        badIngredientsBox = (LinearLayout) findViewById(R.id.BadIngredientsBox);
        textToSpeechButton = (Button) findViewById(R.id.TextToSpeech);

        parser.setUserPreferences(preferences);

        check = getResources().getDrawable(R.drawable.check);
        negative = getResources().getDrawable(R.drawable.negative);

        ts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    ts.setLanguage(Locale.US);
                }
            }
        });

        ts.setSpeechRate(0.9f);

        for (int i = 0; i < itemList.size(); i++) {
            Log.i("ITEM " + i, itemList.get(i));
        }

        ArrayList<ArrayList<String>> allergenItems = parser.checkAllergens(itemList);
        ArrayList<String> lactoseItems = parser.checkLactose(itemList);
        ArrayList<String> veganItems = parser.checkVegan(itemList);
        ArrayList<String> vegetarianItems = parser.checkVegaterian(itemList);
        ArrayList<String> glutenItems = parser.checkGluten(itemList);

        Log.i("size allergerns", "" + allergenItems.size());
        Log.i("size lactoseItems", "" + lactoseItems.size());
        Log.i("size veganItems", "" + veganItems.size());
        Log.i("size vegetarianItems", "" + vegetarianItems.size());
        Log.i("size glutenItems", "" + glutenItems.size());


        if (noBadIngredients(allergenItems, lactoseItems, veganItems, vegetarianItems, glutenItems)) {
            Log.i("OK", "its a");
            speechText.append("The ingredients are okay.");
            icon.setImageDrawable(check);
        } else {
            Log.i("OK", "its n");
            speechText.append("The ingredients are not okay, ");
            icon.setImageDrawable(check);
            titleText.setText("Ingredients are not OK. ");
            titleText.setTextColor(Color.rgb(209, 89, 98));
            icon.setImageDrawable(negative);

            if (allergenItems.size() > 0) {
                displayNegativeNested(allergenItems);
            }

            if (lactoseItems.size() > 0) {
                displayNegative(lactoseItems);
            }

            if (veganItems.size() > 0) {
                displayNegative(veganItems);
            }

            if (vegetarianItems.size() > 0) {
                displayNegative(vegetarianItems);
            }

            if (glutenItems.size() > 0) {
                displayNegative(glutenItems);
            }

        }

        anotherPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Launch After Capture Activity
                Intent intent = new Intent(AfterCaptureActivity.this, OcrCaptureActivity.class);
                intent.putExtra("preferences", preferences);
                startActivity(intent);
            }
        });

        textToSpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ts.speak(speechText.toString(), TextToSpeech.QUEUE_FLUSH, null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ts.speak(speechText,TextToSpeech.QUEUE_FLUSH,null,null);
                } else {
                    //ts.speak(speechText, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

    }

    private boolean noBadIngredients(ArrayList<ArrayList<String>> a,
                                     ArrayList<String> b,
                                     ArrayList<String> c,
                                     ArrayList<String> d,
                                     ArrayList<String> e) {

        return (a.size() == 0) && (b.size() == 0) && (c.size() == 0) && (d.size() == 0) && (e.size() == 0);
    }

    public void onBackPressed() {
        Intent i = new Intent(AfterCaptureActivity.this, MainActivity.class);
        i.putExtra("preferences", preferences);
        startActivity(i);
        finish();
    }

    private void displayNegativeNested(ArrayList<ArrayList<String>> result) {
        Log.i("Cheese", "displayNegNested");

        for (int i = 0; i < result.size() - 1; i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                Log.i("Cheese", result.get(i).get(j));
            }
        }

        Log.i("Cheese", "Weaning stringL " + result.get(result.size() - 1));

        speechText.append(result.get(result.size() - 1));
        speechText.append(" ");

        for (int i = 0; i < result.size() - 1; i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                Log.i("OK", result.get(i).get(j));
                TextView text = new TextView(this);
                text.setText(result.get(i).get(j));
                text.setTextColor(Color.rgb(209, 89, 98));
                text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                text.setGravity(Gravity.CENTER_HORIZONTAL);
                badIngredientsBox.addView(text);
            }
        }


    }

    private void displayNegative(ArrayList<String> result) {
        Log.i("Cheese", "in" + result.size());
        for (int i = 0; i < result.size() - 1; i++) {
            Log.i("Cheese", result.get(i));
        }

        for (int i = 0; i < result.size() - 1; i++) {
            Log.i("OK", result.get(i));
            TextView text = new TextView(this);
            text.setText(result.get(i));
            text.setTextColor(Color.rgb(209, 89, 98));
            text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            text.setGravity(Gravity.CENTER_HORIZONTAL);
            badIngredientsBox.addView(text);
        }
        speechText.append(result.get(result.size() - 1));
        speechText.append(" ");
    }


}
