package tbs.spinjump;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;


public class MainActivity extends Activity {

    // Game Setup:
    private Game game;
    public static GameView gameView;

    static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenObject.setup(this);
        gameView = new GameView(this);
        setContentView(gameView);
        preferences = getPreferences(MODE_PRIVATE);

        //Setup (Called One Time):
        game = new Game(this);
    }
}
