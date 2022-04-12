package android.snek.server.service;

import android.snek.server.game.Game;
import android.snek.server.game.Player;

import java.util.List;

public interface GameProvider {

    List<Game> findAllGames();

    Game findGame();

    Game gameById(String id);

    void joinGame(String gameId, Player player);

    void quitGame(String gameId, Player player);

}
