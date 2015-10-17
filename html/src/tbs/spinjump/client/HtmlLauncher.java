package tbs.spinjump.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.user.client.Window;

import tbs.spinjump.Game;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {

        int w, h;
        h = Math.round(Window.getClientHeight() * 0.82f);
        w = (h * 9) / 16;

        return new GwtApplicationConfiguration(w, h);
    }

    @Override
    public ApplicationListener getApplicationListener() {
        return new Game();
    }
}