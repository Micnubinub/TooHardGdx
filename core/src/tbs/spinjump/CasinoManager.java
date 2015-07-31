package tbs.spinjump;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class CasinoManager {
    private static final Color color = new Color();
    private static Texture flash;
    public ArrayList<CanvasButton> items;
    public int rewardIndex;
    public int rewardIndex2;
    public boolean rewardAnim;
    public float rewardTextize;
    public String rewardText;
    public int rotation;
    public boolean rotateLeft;
    public int flashAlpha;
    public int wiggleCountdown;
    public boolean won;
    public int playCost;
    public String itemText;
    public int moneySpent;

    public CasinoManager() {
        items = new ArrayList<CanvasButton>();
        int itemRowC = 1;
        int itemColC = 1;
        float startXPos = ((Game.w / 2) - ((GameValues.CASINO_ITEM_SCALE * 1.5f) + GameValues.CASINO_ITEM_PADDING));
        float startYPos = ((Game.h / 2) - ((GameValues.CASINO_ITEM_SCALE * 1.5f) + GameValues.CASINO_ITEM_PADDING));
        for (int i = 0; i < 9; ++i) {
            items.add(new CanvasButton((int) (startXPos + ((GameValues.CASINO_ITEM_SCALE + GameValues.CASINO_ITEM_PADDING) * (itemRowC - 1))), (int) (startYPos + ((GameValues.CASINO_ITEM_SCALE + GameValues.CASINO_ITEM_PADDING) * (itemColC - 1))), "qe_btn", false));
            itemRowC += 1;
            if (itemRowC == 4) {
                itemRowC = 1;
                itemColC += 1;
            }
            items.get(i).id = i;
            items.get(i).animated = true;
        }

        flash = getColorTexture(0xffffffff);
    }

    private static Texture getColorTexture(int color) {
        final Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA4444);
        p.setColor(color);
        p.fill();
        return new Texture(p);
    }

    public void update(float delta) {
        if (rewardAnim) {
            rewardTextize += (((float) GameValues.CASINO_TEXT_SCALE / 500) * delta);
            if (rewardTextize > GameValues.CASINO_TEXT_SCALE) {
                rewardTextize = GameValues.CASINO_TEXT_SCALE;
            }

            if (rotateLeft) {
                rotation -= (((float) GameValues.CASINO_TEXT_SCALE / 2500) * delta);
                if (rotation < -12)
                    rotateLeft = false;
            } else {
                rotation += (((float) GameValues.CASINO_TEXT_SCALE / 2500) * delta);
                if (rotation > 12)
                    rotateLeft = true;
            }

            // FIX ROTATION GLITCH:
            if (rotation < -12)
                rotation = -12;
            if (rotation > 12)
                rotation = 12;
        }
        for (int i = 0; i < items.size(); ++i) {
            items.get(i).update(delta);
        }
        if (flashAlpha > 0) {
            flashAlpha -= delta / 2;
            if (flashAlpha < 0)
                flashAlpha = 0;
        }

        // WIGGLER:
        wiggleCountdown -= delta;
        if (wiggleCountdown <= 0) {
            wiggleCountdown = 50;
            items.get(Utility.getRandom(0, items.size() - 1)).wiggle(4);
        }
    }

    public void draw(SpriteBatch batch) {
        // FLASH:
        if (flashAlpha > 0) {
            batch.setColor(1, 1, 1, (flashAlpha / 255f));
            batch.draw(flash, 0, 0, Game.w, Game.h);
            batch.setColor(1, 1, 1, 1);
        }
        // OTHER:
        if (rewardAnim) {
            color.set(0xFFFFFFFF);
            //Todo maybe use getImageTextHere canvas.rotate(-rotation, (Game.w/2), (Game.h/2));
            Utility.drawCenteredText(batch, color, rewardText, Game.w / 2, (Game.h / 2), Utility.getScale(rewardTextize));
            color.set(1, 1, 1, 120 / 255f);
            if (won) {
                Utility.drawCenteredText(batch, color, itemText, Game.w / 2, (Game.h / 2) + (Game.h / 10), Utility.getScale(rewardTextize / 1.5f));
            } else {
                Utility.drawCenteredText(batch, color, "NO REWARD", Game.w / 2, (Game.h / 2) + (Game.h / 10), Utility.getScale(rewardTextize / 1.5f));
            }


        } else {
            for (int i = 0; i < items.size(); ++i) {
                items.get(i).draw(batch);
            }
        }
    }

    public void generateRewards() {
        flashAlpha = 0;
        rewardAnim = false;
        rewardIndex = Utility.getRandom(0, items.size() - 1);
        rewardIndex2 = Utility.getRandom(0, items.size() - 1);
        rewardTextize = 0;
        rotation = 0;
        Game.homeButton2.active = false;
        for (int i = 0; i < items.size(); ++i) {
            items.get(i).active = true;
        }
    }

    public void playerSelectButton(int index, int x, int y) {
        if (rewardIndex == index) {
            // WIN:
            rewardText = "YOU WIN!";
            won = true;
            Game.winSound.play();

            // MAKE REWARD:
            int unlockAbleIndex = -1;
            for (int i = 0; i < Game.storeItems.size(); ++i) {
                Game.storeItems.get(i).bought = Game.player.purchases.get(i) == 1; // IMPORTANT
                if (!Game.storeItems.get(i).buyable && !Game.storeItems.get(i).bought) {
                    if (unlockAbleIndex == -1)
                        if (Utility.getRandom(0, Game.storeItems.get(i).unlockRarity) == 0) {
                            unlockAbleIndex = i;
                        }
                }
            }

            if (unlockAbleIndex == -1) {
                itemText = moneySpent * 10 + " COINS!";
                Game.player.earnCoinAnim((Game.w / 2), (Game.h / 2), moneySpent * 10);
            } else {
                Game.storeItems.get(unlockAbleIndex).bought = true;
                Game.player.purchases.set(unlockAbleIndex, 1);
                Game.player.saveData();
                if (Game.storeItems.get(unlockAbleIndex).trail) {
                    itemText = "NEW TRAIL!";
                } else {
                    itemText = "NEW SKIN!";
                }
            }
        } else {
            // LOSE:
            rewardText = "YOU LOSE!";
            won = false;
            // FIND NEW SOUND:
            Game.deathSound.play();
        }
        flashAlpha = 255;
        rewardAnim = true;
        Game.homeButton2.active = true;
    }

    public void updateCost(int coins) {
        if (coins <= 10) {
            playCost = 5;
        } else if (coins <= 20) {
            playCost = 10;
        } else if (coins <= 50) {
            playCost = 20;
        } else if (coins <= 100) {
            playCost = 50;
        } else if (coins <= 500) {
            playCost = 100;
        } else if (coins <= 1000) {
            playCost = 500;
        } else if (coins <= 10000) {
            playCost = 1000;
        } else if (coins <= 100000) {
            playCost = 5000;
        } else {
            playCost = 10000;
        }
    }

}
