package android.snek.server.model.message;

import android.snek.server.utils.Vector;

public class DirectionChangedMessage {

    private String playerId;
    private Vector direction;

    public String getPlayerId() {
        return playerId;
    }

    public Vector getDirection() {
        return direction;
    }
}
