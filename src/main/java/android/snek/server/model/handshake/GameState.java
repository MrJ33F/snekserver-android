package android.snek.server.model.handshake;

import android.snek.server.game.Food;
import android.snek.server.game.Game;
import android.snek.server.game.Player;

import java.util.List;

public class GameState {

    private final List<Player> playerList;
    private final List<Food> foodList;
    private final String gameId;

    private GameState(List<Player> playerList, List<Food> foodList, String gameId) {
        this.playerList = playerList;
        this.foodList = foodList;
        this.gameId = gameId;
    }

    public static GameState fromGame(Game game) {
        final var players = game.getPlayers();
        final var food = game.getFood();
        final var id = game.getId();

        return new GameState(players, food, id);
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public String getGameId() {
        return gameId;
    }
}
