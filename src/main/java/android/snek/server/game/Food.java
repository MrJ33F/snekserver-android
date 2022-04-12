package android.snek.server.game;

import android.snek.server.model.GameConstants;
import android.snek.server.utils.ColorUtils;
import android.snek.server.utils.RandomUtils;
import android.snek.server.utils.Vector;

public class Food {

    private final int id;
    private final double radius;
    private final int color;
    private Vector position;

    public Food(int id) {
        this.id = id;
        this.color = ColorUtils.rainbowColor();
        this.radius = GameConstants.FOOD_BASE_RADIUS + (Math.random() * 5);

        relocate();
    }

    public void relocate() {
        final double x = RandomUtils.nextInt(GameConstants.BOARD_SIZE);
        final double y = RandomUtils.nextInt(GameConstants.BOARD_SIZE);

        position = Vector.create(x, y);
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public Vector getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;

        Food food = (Food) o;

        return id == food.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
