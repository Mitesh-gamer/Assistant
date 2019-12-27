package com.example.dell.assistant;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.speech.RecognizerIntent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int SPEECH_RECOGNITION_CODE=1;
    private TextView tv;
    private ImageButton button;
    String[] arr={"Google","Facebook","YouTube","Twitter"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.tv_output);
        button=(ImageButton)findViewById(R.id.btn_img);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()){
                    StartSpeechToText();
                }else{
                    Toast.makeText(MainActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void StartSpeechToText() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speech Something..");
        try{ startActivityForResult(intent,SPEECH_RECOGNITION_CODE); // 1
        } catch(ActivityNotFoundException e){
            Toast.makeText(this, "Sorry! Speech Recognition is not supported in this device.", Toast.LENGTH_SHORT).show(); } }//end of StartSpeechToText

    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case SPEECH_RECOGNITION_CODE:{
                if(resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //String
                    // text=result.get(0);

                    tv.setText(result.get(0));
                    for(int i=0;i<arr.length;i++) {
                        if (tv.getText().equals(getString(R.string.str) + " " + arr[i]) || tv.getText().equals(arr[i])) {
                            //String t=getString(R.string.curl,arr[i]);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.curl,arr[i]))));
                            break;
                        }
                        if (tv.getText().equals(getString(R.string.str1) + " " + arr[i]) || tv.getText().equals(arr[i])) {
                           // String t=getString(R.string.curl,arr[i]);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.curl,arr[i]))));
                            break;
                        }
                        if (tv.getText().equals(getString(R.string.str2) + " " + arr[i]) || tv.getText().equals(arr[i])) {
                            // String t=getString(R.string.curl,arr[i]);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.curl,arr[i]))));
                            break;
                        }
                        if(i==arr.length-1)
                        {Toast.makeText(this, "Not in data", Toast.LENGTH_SHORT).show();
                        tv.setText("Try again !");}
                    }
                } break;
            }
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
