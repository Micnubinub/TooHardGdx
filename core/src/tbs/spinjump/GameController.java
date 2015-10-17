package tbs.spinjump;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class GameController implements InputProcessor {

    public static void pressScreen(int x, int y) {
        Game.log("" + x + " , " + y);
        y = Game.h - y;
        if (Game.state == GameState.Menu) {
            if (Game.rateButton.isClicked(x, y)) {
                Game.log("menu > rate");
          /*Todo      final String appPackageName = Game.context.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    Game.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    Game.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }*/
            } else if (Game.leaderButton.isClicked(x, y)) {
                Game.log("menu > lead");
/*          Todo      if (BaseGameActivity.getApiClient().isConnected()) {
                    ((BaseGameActivity) Game.context).startActivityForResult(
                            Games.Leaderboards.getLeaderboardIntent(
                                    MainActivity.getApiClient(), GameValues.leaderboardID),
                            10101);
                } else {
                    // LOGIN TO PLAY SERVICES:
                    ((BaseGameActivity) Game.context).getGameHelper()
                            .beginUserInitiatedSignIn();
                }*/
            } else if (Game.storeButton.isClicked(x, y)) {
                Game.log("menu > store");
                Game.state = GameState.Store;
                Game.showStore();
            } else if (Game.gambleButton.isClicked(x, y)) {
                Game.log("menu > gamble");
                // START GAMBLING:
                if (Game.player.coins >= Game.casinoManager.playCost) {
                    Game.casinoManager.moneySpent = Game.casinoManager.playCost;
                    Game.player.coins -= Game.casinoManager.playCost;
                    Game.casinoManager.generateRewards();
                    Game.player.earnCoinAnim(x, y, 0);
                    Game.state = GameState.Casino;
                    Game.moneySound.play();
                } else {
                    Game.buttonSound.play();
                }
            } else {
                Game.log("menu > play");
                Game.buttonSound.play();
                Game.state = GameState.Playing;
            }
        } else if (Game.state == GameState.Playing) {
            Game.log("play > jump");
            Game.player.jump();
        } else if (Game.state == GameState.Death) {
            Game.log("death > rate");
            if (Game.homeButton.isClicked(x, y)) {
                Game.log("death > setup");
                Game.setup();
            } else if (Game.shareButton.isClicked(x, y)) {
                Game.log("death > share");
//                MainActivity.unlockAchievement("CgkIxIfix40fEAIQDA");
//      Todo          Game.Share(Game.takeScreenShot());
            } else if (Game.retryButton.isClicked(x, y)) {
                Game.log("death > retry");
                Game.setup();
                Game.state = GameState.Playing;
            } else if (Game.adButton.isClicked(x, y)) {
                Game.log("death > ad");
                // SHOW AD:
                Game.showAd(true);
                Game.adButton.active = false;
            } else if (Game.likeButton.isClicked(x, y)) {
                Game.log("death > like");
                // SHOW FB PAGE:
//   Todo             try {
//                    Game.context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
//                    Game.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/337927283025915")));
//                } catch (Exception e) {
//                    Game.context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/AndroidHackerApp")));
//                }
                Game.likeButton.active = false;
            } else if (Game.buyButton.isClicked(x, y)) {
                Game.log("death > buy");
                // EARN MONEY REWARD:
//        Todo        MainActivity.unlockAchievement("CgkIxIfix40fEAIQCA");
                Game.moneySound.play();
                Game.player.earnCoinAnim(x, y, Utility.getRandom(1, 20));
                Game.buyButton.active = false;
                if (Game.player.score > 0)
                    Game.reviveButton.active = Game.player.coins >= Game.revivalCost;
            } else if (Game.reviveButton.isClicked(x, y)) {
                Game.log("death > revive");
                // REVIVAL LOGIC:
                Game.reviveButton.active = false;
                Game.player.coins -= Game.revivalCost;
                Game.player.saveData();
                Game.player.revive();
                Game.state = GameState.Playing;
            }
        } else if (Game.state == GameState.Casino) {
            if (!Game.casinoManager.rewardAnim) {
                Game.log("casino > reward anim");
                for (int i = 0; i < Game.casinoManager.items.size(); ++i) {
                    if (Game.casinoManager.items.get(i).isClicked(x, y)) {
                        for (int z = 0; z < Game.casinoManager.items.size(); ++z) {
                            Game.casinoManager.items.get(z).active = false;
                        }
                        // GET REWARD:
                        Game.casinoManager.playerSelectButton(i, x, y);
                        Game.player.earnCoinAnim(x, y, 0);
                    }
                }
            } else {

                if (Game.homeButton2.isClicked(x, y)) {
                    Game.log("casino > home");
                    Game.state = GameState.Menu;
                    Game.player.saveData();
                }
            }
        }

    }


    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        pressScreen(x, y);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
                pressScreen(0, 0);
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
