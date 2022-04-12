package android.snek.server.game;

import android.snek.server.model.GameConstants;
import android.snek.server.utils.MathUtils;
import android.snek.server.utils.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FoodManager {

    private static final int BATCH_SIZE = 1000;

    private final List<List<android.snek.server.game.Food>> foodLists;
    private final int row = GameConstants.BOARD_SIZE / BATCH_SIZE;

    FoodManager() {
        final int size = (int) Math.pow(row, 2);
        this.foodLists = new ArrayList<>(size);

        IntStream.range(0, size).forEach(i -> foodLists.add(new ArrayList<>()));
    }

    void initFood(int N) {
        IntStream.range(0, N)
                .mapToObj(android.snek.server.game.Food::new)
                .forEach(food -> {
                    final int index = calculateFoodListIndex(food.getPosition());
                    foodLists.get(index).add(food);
                });
    }

    List<android.snek.server.game.Food> update(List<android.snek.server.game.Player> players) {
        return players.stream()
                .map(this::updatePlayer)
                .reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return b;
                });
    }

    private List<android.snek.server.game.Food> updatePlayer(android.snek.server.game.Player player) {
        final var index = calculateFoodListIndex(player.getPosition());

        List<android.snek.server.game.Food> updated = toCheck(index).stream()
                .filter(food -> food.getPosition().distance(player.getPosition()) <= player.getRadius())
                .collect(Collectors.toList());

        final Double deltaR = updated.stream()
                .reduce(0.0, (a, f) -> MathUtils.pythagoras(a, f.getRadius()), Double::sum);

        updated.stream()
                .peek(food -> {
                    final var i = calculateFoodListIndex(food.getPosition());
                    foodLists.get(i).remove(food);
                })
                .peek(android.snek.server.game.Food::relocate)
                .forEach(food -> {
                    final var i = calculateFoodListIndex(food.getPosition());
                    foodLists.get(i).add(food);
                });

        updated.stream()
                .findAny()
                .ifPresent(food -> player.setColor(food.getColor()));

        player.grow(deltaR);

        return updated;
    }



    private List<android.snek.server.game.Food> toCheck(int index) {
        int upper = index - row;
        int bottom = index + row;
        int left = index - 1;
        int right = index + 1;

        final var toCheck = new ArrayList<>(foodLists.get(index));

        if (upper >= 0)
            toCheck.addAll(foodLists.get(upper));
        if (bottom < row * row)
            toCheck.addAll(foodLists.get(bottom));
        if (left >= 0 && left % row < row - 1)
            toCheck.addAll(foodLists.get(left));
        if (right < row * row && right % row > 0)
            toCheck.addAll(foodLists.get(right));

        return toCheck;
    }

    private int calculateFoodListIndex(Vector v) {
        int x = (int) (v.x / BATCH_SIZE);
        int y = (int) (v.y / BATCH_SIZE);

        x = MathUtils.between(0, x, row - 1);
        y = MathUtils.between(0, y, row - 1);

        return x + (y * row);
    }

    List<android.snek.server.game.Food> getFoodList() {
        return foodLists.stream()
                .reduce(new ArrayList<>(GameConstants.FOOD_COUNT), (a, b) -> {
                    a.addAll(b);
                    return a;
                });
    }

}
