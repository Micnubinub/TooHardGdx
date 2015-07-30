package tbs.spinjump;

public class GameController {

    public static void pressScreen(int x, int y) {
        if (Game.state == GameState.Menu) {
            if (Game.achievementButton.isClicked(x, y)) {

            } else if (Game.rateButton.isClicked(x, y)) {

            } else if (Game.leaderButton.isClicked(x, y)) {

            } else if (Game.storeButton.isClicked(x, y)) {
//  todp              Game.showStore();
            } else {
                Game.state = GameState.Playing;
            }
        } else if (Game.state == GameState.Playing) {
            Game.player.jump();
        } else if (Game.state == GameState.Death) {
            if (Game.homeButton.isClicked(x, y)) {
                Game.setup();
            } else if (Game.shareButton.isClicked(x, y)) {
//       todo         Game.Share(Game.takeScreenShot());
            }
        }
    }
}
