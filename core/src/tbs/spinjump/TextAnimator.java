package tbs.spinjump;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Sidney on 10/06/2015.
 */
public class TextAnimator {
    public ArrayList<AnimatedText> texts;
    public int nextTextIndex;

    public TextAnimator() {
        nextTextIndex = 0;
        texts = new ArrayList<AnimatedText>();
        for (int i = 0; i < 3; ++i) {
            texts.add(new AnimatedText());
        }
    }

    public void update(float delta) {
        for (int i = 0; i < texts.size(); ++i) {
            texts.get(i).update(delta);
        }
    }

    public void draw(final SpriteBatch batch) {
        for (int i = 0; i < texts.size(); ++i) {
            texts.get(i).draw(batch);
        }
        batch.setColor(1, 1, 1, 1);
    }

    public void startText(String text, int x, int y) {
        texts.get(nextTextIndex).setup(x, y, text);
        nextTextIndex += 1;
        if (nextTextIndex >= texts.size()) {
            nextTextIndex = 0;
        }
    }
}
