package com.example.anu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText etText;
    Button btSave;
    JSONObject saved = new JSONObject();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         etText = findViewById(R.id.etItem);
         btSave = findViewById(R.id.btSave);

         init();
        Intent intent = getIntent();
        if(intent.getIntExtra("position",-1)!= -1){
            try {
                String s = etText.getText().toString();
                if (!preferences.getString("saved","").equals(""))
                    saved = new JSONObject(preferences.getString("saved",""));
                etText.setText(saved.getString("saved"+intent.getIntExtra("position",0)));
                s = saved.getString("saved"+intent.getIntExtra("position",0));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etText.getText().toString();
                if(!s.equals("")){
                    try {
                        if (!preferences.getString("saved","").equals(""))
                            saved = new JSONObject(preferences.getString("saved",""));
                        saved.put("saved"+saved.length(),s);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("testing",saved+"");
                    editor.putString("saved",saved.toString());
                    editor.apply();
                    etText.setText("");
                    Intent intent1 = new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent1);
                }
            }
        });
    }
    private void init(){
        preferences = getSharedPreferences("text", Context.MODE_PRIVATE);
        editor = preferences.edit();
        etText = findViewById(R.id.etItem);
        btSave = findViewById(R.id.btSave);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.save){
            if (preferences.getString("saved","").equals("")){
                Toast.makeText(getApplicationContext(),"Nothing to Save",Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
