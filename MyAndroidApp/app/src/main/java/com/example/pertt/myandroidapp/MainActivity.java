package com.example.pertt.myandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        Button b =  findViewById(R.id.start_button);
        b.setEnabled(true);
    }
    public void onStartClick(View v) {
        new Connector().execute();
    }
    public class Connector extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return SocketConnection.connect();
        }
        @Override
        protected void onPostExecute(Boolean bool) {
            TextView textView = findViewById(R.id.connection_text);
            int visible = textView.getVisibility();
            if (visible == View.INVISIBLE) {
                textView.setVisibility(View.VISIBLE);
            }
            if (!bool) {
                textView.setText("connection failed, attempts done: " + SocketConnection.getConnectionAttempts());
            } else {
                textView.setText("connected, moving to next page");
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        }
    }
}
