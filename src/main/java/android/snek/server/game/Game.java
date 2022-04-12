package android.snek.server.game;

import android.snek.server.model.GameConstants;
import android.snek.server.model.message.GameUpdateMessage;
import android.snek.server.utils.Vector;

import java.util.Collections;
import java.util.List;

public class Game {

    private volatile boolean alive = true;
    private final String id;
    private final FoodManager foodManager;
    private final android.snek.server.game.PlayerManager playerManager;

    public Game(String id) {
        this.id = id;
        foodManager = new FoodManager();
        playerManager = new android.snek.server.game.PlayerManager();

        foodManager.initFood(GameConstants.FOOD_COUNT);
    }

    public GameUpdateMessage run() {
        final var tuple = playerManager.update();
        final var relocatedFood = foodManager.update(tuple.x);

        return new GameUpdateMessage(relocatedFood, tuple.x, tuple.y);
    }

    public void addPlayer(Player player) {
        playerManager.addPlayer(player);
    }

    public void removePlayer(Player player) {
        playerManager.removePlayer(player);
    }

    public int playersNumber() {
        return playerManager.playersNumber();
    }

    public String getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(playerManager.getPlayers());
    }

    public List<android.snek.server.game.Food> getFood() {
        return Collections.unmodifiableList(foodManager.getFoodList());
    }

    public void updatePlayer(Vector direction, String playerId) {
        playerManager.updatePlayer(playerId, direction);
    }

    public boolean isFull() {
        return playersNumber() >= GameConstants.MAX_PLAYERS;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
