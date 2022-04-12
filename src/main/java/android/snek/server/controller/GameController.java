package android.snek.server.controller;

import android.snek.server.exception.GameNotFoundException;
import android.snek.server.exception.PlayerNotFoundException;
import android.snek.server.game.Game;
import android.snek.server.game.Player;
import android.snek.server.model.message.DirectionChangedMessage;
import android.snek.server.model.message.PlayerJoinMessage;
import android.snek.server.service.GameProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    private final GameProvider gameProvider;


    @Autowired
    public GameController(GameProvider gameProvider) {
        this.gameProvider = gameProvider;
    }

    @MessageMapping("direction/{gameId}")
    public ResponseEntity<?> changeDirection(@DestinationVariable String gameId,
                                             DirectionChangedMessage message) {
        final Game game = gameProvider.gameById(gameId);
        game.updatePlayer(message.getDirection(), message.getPlayerId());

        return ResponseEntity.ok().build();
    }

    @SubscribeMapping("subscribe/{id}")
    public void subscribe(@DestinationVariable(value = "id") String gameId) {
        log.info("Someone subscribed to game: id[" + gameId + "]");
    }

    @MessageMapping("quit/{id}")
    public void quit(@DestinationVariable(value = "id") String gameId, PlayerJoinMessage message) {
        log.info("Player [" + message.getPlayerName() + "] quits game: [id: " + gameId +"]");

        gameProvider.quitGame(gameId, new Player(message.getPlayerName(), message.getPlayerId(), message.getSkinName()));
    }

    @MessageExceptionHandler(value = {
            GameNotFoundException.class,
            PlayerNotFoundException.class,
            RuntimeException.class
    })
    public ResponseEntity<?> handleException(RuntimeException ex) {
        log.info(ex.getMessage(), ex);
        final var body = ex.getMessage();

        return ResponseEntity.badRequest().body(body);
    }

}
