package client.view;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.pertt.myandroidapp.R;

import client.net.ServerSender;
import client.net.SocketConnection;
import server.common.Constants;

/**
 * Created by pertt on 2017-12-12.
 */

public class GameActivity extends Activity {
    public static final String GAMEINFO_KEY = "PERTTU/CLIENT/GAMEINFO";
    private boolean playing = false;
    @Override
    public void onCreate(Bundle bundleInstance) {
        super.onCreate(bundleInstance);
        setContentView(R.layout.game_activity);
        new Starter().execute();
    }
    private void writeInfo(String[] messages) {
        playing = true;
        setContentView(R.layout.game_activity);
        TextView score = findViewById(R.id.score_info);
        TextView guesses = findViewById(R.id.guesses_info);
        TextView info = findViewById(R.id.server_message);
        TextView current = findViewById(R.id.current_word);
        info.setText(messages[0]);
        score.setText(messages[1]);
        guesses.setText(messages[2]);
        current.setText(messages[3]);
    }
    private void writeInfo(String s) {
        String[] messages = s.split(Constants.INFO_DELIMETER);
        writeInfo(messages);
    }
    public void onGuessClick(View v) {
        Button b = (Button) v;
        String s;
        if (b.getId() == R.id.guess_button) {
            EditText text = findViewById(R.id.word_guess);
            s = text.getText().toString();
        } else {
            s = b.getText().toString();
        }
        new GuessSender().execute(s);
    }
    public void onNewGameClick(View v) {
        new Starter().execute();
    }
    public void onQuitClick(View v) {
        Intent i = new Intent(GameActivity.this, MainActivity.class);
        SocketConnection.disconnect();
        startActivity(i);
    }
    private class Starter extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return ServerSender.newGame();
        }
        @Override
        protected void onPostExecute(String s) {
            writeInfo(s);
        }
    }
    private class GuessSender extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            if (!playing) {
                return "not connected";
            }
            String res = ServerSender.sendGuess(strings[0]);
            if (res == null) {
                res = "server error";
            }

            return res;
        }
        @Override
        protected void onPostExecute(String s) {
            String[] messages = s.split(Constants.INFO_DELIMETER);
            if (messages.length == 1) {
                TextView t = findViewById(R.id.server_message);
                t.setText(messages[0]);
                return;
            }
            boolean gameDone = Boolean.valueOf(messages[4]);
            if (gameDone) {
                Intent i = new Intent(GameActivity.this, GameOver.class);
                i.putExtra(GameActivity.GAMEINFO_KEY, messages);
                startActivity(i);
                return;
            }
            TextView score = findViewById(R.id.score_info);
            TextView guess = findViewById(R.id.guesses_info);
            TextView info = findViewById(R.id.server_message);
            TextView current = findViewById(R.id.current_word);
            info.setText(messages[0]);
            score.setText(messages[1]);
            guess.setText(messages[2]);
            current.setText(messages[3]);
        }
    }
}
