package android.snek.server.controller;

import android.snek.server.game.Game;
import android.snek.server.game.Player;
import android.snek.server.model.handshake.GameState;
import android.snek.server.model.message.PlayerJoinMessage;
import android.snek.server.service.GameProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServerController {

    private final Logger log = LoggerFactory.getLogger(ServerController.class);
    private final GameProvider gameProvider;

    @Autowired
    public ServerController(GameProvider gameProvider) {
        this.gameProvider = gameProvider;
    }

    @GetMapping("/")
    public String home() {
        return ("<body style=\"background-color: black; color: white;\"> \n<pre>" +
                "Snek server xdd" +
                "</pre> </body>").replaceAll("\n", "<br>");
    }


    @GetMapping("/game")
    public ResponseEntity<String> findGame() {
        final Game game = gameProvider.findGame();

        return ResponseEntity.ok(game.getId());
    }

    @PostMapping("/game/{id}/join")
    public ResponseEntity<GameState> join(@PathVariable(value = "id") String gameId, @RequestBody PlayerJoinMessage message) {
        log.info("Player [" + message.getPlayerName() + "] connected to game: [id: " + gameId +"]");
        log.info(message.toString());

        gameProvider.joinGame(gameId, new Player(message.getPlayerName(), message.getPlayerId(), message.getSkinName()));

        return ResponseEntity.ok(GameState.fromGame(gameProvider.gameById(gameId)));
    }

    @GetMapping("/games")
    public ResponseEntity<?> findGames() {
        final List<Game> games = gameProvider.findAllGames();

        return ResponseEntity.ok(games);
    }

}
