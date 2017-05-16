package com.example.root.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.speech.RecognizerIntent.*;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    private ListView listaDePalavras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recog);

        Button botaoDeFalar = (Button) findViewById(R.id.speakButton);
        listaDePalavras = (ListView) findViewById(R.id.list);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            botaoDeFalar.setEnabled(false);
            botaoDeFalar.setText("Recognizer not present");
        }
    }

    public void prepararReconhecimento(View v){
        iniciarReconhecimento();
    }

    private void iniciarReconhecimento() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Reconhecendo ...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> compativeisComAVoz = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            listaDePalavras.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,compativeisComAVoz));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
