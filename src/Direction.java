public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NONE;

    public static Direction opposite(Direction direction){
        if (direction == UP) return DOWN;
        if (direction == DOWN) return UP;
        if (direction == LEFT) return RIGHT;
        if (direction == RIGHT) return LEFT;
        return NONE;
    }
}
