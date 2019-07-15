package com.example.application.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity2 extends AppCompatActivity {
    private Button button;
    private Button button4;
    private Button button5;
    private TextView tv;
    private EditText et;
    OutputStreamWriter outputStreamWriter;
    File path;
    File file;
    Date currentTime;
    SimpleDateFormat sdf;
    String currentDateandTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


         sdf = new SimpleDateFormat("dd.MM HH.mm.ss ");

        setTitle("Konverzija € <> KN");
        file = new File(path, "filename.txt");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        button =  findViewById(R.id.button2);
        button4 =  findViewById(R.id.button4);
        button5 =  findViewById(R.id.button5);

         tv = findViewById(R.id.textView2);
         et = findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    convert();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    convert2();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromFile((getApplicationContext()));
            }
        });
    }

    public void pokaziPodatke()
    {


    }



    public void convert() throws IOException {

        double euri=(Double.parseDouble(et.getText().toString())*7.5);
        double roundOff = Math.round(euri * 100.0) / 100.0;



        tv.setText( et.getText() + "€ iznosi " + roundOff + "KN ");


        currentDateandTime = sdf.format(new Date());
        String upis = currentDateandTime +"   "+  roundOff+ "€ > " + et.getText() +  "KN";
        writeToFile(upis, (getApplicationContext()));

    }


    public void convert2() throws IOException {

        double euri=(Double.parseDouble(et.getText().toString())/7.5);
        double roundOff = Math.round(euri * 100.0) / 100.0;

        tv.setText( et.getText() + "KN iznosi " + roundOff + "€ ");
        currentDateandTime = sdf.format(new Date());
        String upis = currentDateandTime +"   "+  roundOff+ "KN > " + et.getText() +  "€";
        writeToFile(upis, (getApplicationContext()));

    }


    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_APPEND));
            outputStreamWriter.write(data+"\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        Toast.makeText(getApplicationContext(),ret,Toast.LENGTH_LONG).show();

        return ret;
    }

    }



