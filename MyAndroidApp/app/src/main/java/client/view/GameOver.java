package client.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pertt.myandroidapp.R;

import client.net.SocketConnection;

/**
 * Created by Perttu on 2017-12-16.
 */

public class GameOver extends Activity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.game_over);
        String[] info = getIntent().getStringArrayExtra(GameActivity.GAMEINFO_KEY);
        int guesses = Integer.parseInt(info[2]);
        TextView inf = findViewById(R.id.game_result);
        if (guesses > 0) {
            inf.setText("Congratulations");
        } else {
            inf.setText("Game Over");
        }
        TextView game = findViewById(R.id.result_message);
        String serverMessage = info[0];
        game.setText(serverMessage);

        TextView gameScore = findViewById(R.id.game_score);
        String score = info[1];
        gameScore.setText(score);
        TextView text = findViewById(R.id.client_message);
        text.setVisibility(View.INVISIBLE);
    }
    public void NextGameClick(View v) {
        TextView text = findViewById(R.id.client_message);
        text.setText("Starting next game...");
        text.setVisibility(View.VISIBLE);
        String[] arr = getIntent().getStringArrayExtra(GameActivity.GAMEINFO_KEY);
        Intent i = new Intent(GameOver.this, GameActivity.class);
        i.putExtra(GameActivity.GAMEINFO_KEY, arr);
        startActivity(i);
    }
    public void ExitClick(View v) {
        TextView text = findViewById(R.id.client_message);
        text.setText("Disconnecting...");
        SocketConnection.disconnect();
        Intent i = new Intent(GameOver.this, MainActivity.class);
        startActivity(i);
    }
}
