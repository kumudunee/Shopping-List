package com.example.anu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPreferences preferences;
    JSONObject saved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recycler_view);

        preferences = getSharedPreferences("text", Context.MODE_PRIVATE);
        Log.d("Testing",preferences.getString("saved",""));
        try {
            saved = new JSONObject(preferences.getString("saved",""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Main2Activity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new Adapter());


    }


    public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
        @NonNull
        @Override
        public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(Main2Activity.this)
                    .inflate(R.layout.row_item,viewGroup,false);
            Holder holder = new Holder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.getChildPosition(v);
                    Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.Holder holder, int i) {
            try {
                holder.textView.setText(saved.getString("saved"+i));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return saved.length();
        }
        public class Holder extends RecyclerView.ViewHolder{
            TextView textView;
            public Holder(View itemView){
                super(itemView);
                textView = itemView.findViewById(R.id.text_view);
            }

        }

    }
}
