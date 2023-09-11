package org.example;

import java.util.Map;
import java.util.Objects;

public class Main {
    public enum Tile {
        AIR(0),
        FLUX(1),
        UNBREAKABLE(2),
        PLAYER(3),
        STONE(4),
        FALLING_STONE(5),
        BOX(6), FALLING_BOX(7),
        KEY1(8), LOCK1(9),
        KEY2(10), LOCK2(11)
        ;

        private final int code;

        Tile(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }
    }

    public enum Input {
        UP(0), DOWN(1), LEFT(2), RIGHT(3)
        ;

        private final int code;

        Input(int code) {
        this.code = code;
        }

        public int getCode() {
            return this.code;
        }
    }

    public static int playerx = 1;
    public static int playery = 1;
    public static int[][] map = {
        {2, 2, 2, 2, 2, 2, 2, 2},
        {2, 3, 0, 1, 1, 2, 0, 2},
        {2, 4, 2, 6, 1, 2, 0, 2},
        {2, 8, 4, 1, 1, 2, 0, 2},
        {2, 4, 1, 1, 1, 9, 0, 2},
        {2, 2, 2, 2, 2, 2, 2, 2},
    };
    public static int[] inputs = {};

    public void remove(Tile tile) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[x][y] == tile.getCode()) {
                    map[y][x] = Tile.AIR.getCode();
                }
            }
        }
    }

    public void moveToTile(int newx, int newy) {
        map[playery][playerx] = Tile.AIR.getCode();
        map[newy][newx] = Tile.PLAYER.getCode();
        playerx = newx;
        playery = newy;
    }

    public void moveHorizontal(int dx) {
        if (map[playery][playerx + dx] == Tile.FLUX.getCode()
        || map[playery][playerx + dx] == Tile.AIR.getCode()) {
            moveToTile(playerx + dx, playery);
        } else if ((map[playery][playerx + dx] == Tile.STONE.getCode()
            || map[playery][playerx + dx] == Tile.BOX.getCode())
            && map[playery][playerx + dx + dx] == Tile.AIR.getCode()
            && map[playery + 1][playerx + dx] != Tile.AIR.getCode()) {
            map[playery][playerx + dx + dx] = map[playery][playerx + dx];
            moveToTile(playerx + dx, playery);
        } else if (map[playery][playerx + dx] == Tile.KEY1.getCode()) {
            remove(Tile.LOCK1);
            moveToTile(playerx + dx, playery);
        } else if (map[playery][playerx + dx] == Tile.KEY2.getCode()) {
            remove(Tile.LOCK2);
            moveToTile(playerx + dx, playery);
        }
    }

    public void moveVertical(int dy) {
        if (map[playery + dy][playerx] == Tile.FLUX.getCode()
            || map[playery + dy][playerx] == Tile.AIR.getCode()) {
            moveToTile(playerx, playery + dy);
        } else if (map[playery + dy][playerx] == Tile.KEY1.getCode()) {
            remove(Tile.LOCK1);
            moveToTile(playerx, playery + dy);
        } else if (map[playery + dy][playerx] == Tile.KEY2.getCode()) {
            remove(Tile.LOCK2);
            moveToTile(playerx, playery + dy);
        }
    }

    public void update() {
        while (inputs.length > 0) {
            int current = inputs[inputs.length - 1];
            if (current == Input.LEFT.getCode())
                moveHorizontal(-1);
            else if (current == Input.RIGHT.getCode())
                moveHorizontal(1);
            else if (current == Input.UP.getCode())
                moveVertical(-1);
            else if (current == Input.DOWN.getCode())
                moveVertical(1);
        }

        for (int y = map.length - 1; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                if ((map[y][x] == Tile.STONE.getCode() || map[y][x] == Tile.FALLING_STONE.getCode())
                    && map[y + 1][x] == Tile.AIR.getCode()) {
                    map[y + 1][x] = Tile.FALLING_STONE.getCode();
                    map[y][x] = Tile.AIR.getCode();
                } else if ((map[y][x] == Tile.BOX.getCode() || map[y][x] == Tile.FALLING_BOX.getCode())
                    && map[y + 1][x] == Tile.AIR.getCode()) {
                    map[y + 1][x] = Tile.FALLING_BOX.getCode();
                    map[y][x] = Tile.AIR.getCode();
                } else if (map[y][x] == Tile.FALLING_STONE.getCode()) {
                    map[y][x] = Tile.STONE.getCode();
                } else if (map[y][x] == Tile.FALLING_BOX.getCode()) {
                    map[y][x] = Tile.BOX.getCode();
                }
            }
        }
    }

    public void draw() {
        // 호출 또는 전달 한가지만 하는 메서드 생성
        Objects g = createGraphics();
        // 메서드 추출
        drawing(g);

        drawPlayer(g);
    }

    private Objects createGraphics() {

        Objects canvas = document.getElementById("GameCanvas") as HTMLCanvasElement;
        Objects g = canvas.getContext("2d");
        g.clearRect(0, 0, canvas.width, canvas.height);

        return g;
    }

    private void drawing (Objects g) {
        // Draw map
        for (let y = 0; y < map.length; y++) {
            for (let x = 0; x < map[y].length; x++) {
                if (map[y][x] === Tile.FLUX)
                    g.fillStyle = "#ccffcc";
                else if (map[y][x] === Tile.UNBREAKABLE)
                    g.fillStyle = "#999999";
                else if (map[y][x] === Tile.STONE || map[y][x] === Tile.FALLING_STONE)
                    g.fillStyle = "#0000cc";
                else if (map[y][x] === Tile.BOX || map[y][x] === Tile.FALLING_BOX)
                    g.fillStyle = "#8b4513";
                else if (map[y][x] === Tile.KEY1 || map[y][x] === Tile.LOCK1)
                    g.fillStyle = "#ffcc00";
                else if (map[y][x] === Tile.KEY2 || map[y][x] === Tile.LOCK2)
                    g.fillStyle = "#00ccff";

                if (map[y][x] !== Tile.AIR && map[y][x] !== Tile.PLAYER)
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void drawPlayer(Objects g) {
        // Draw player
        g.fillStyle = "#ff0000";
        g.fillRect(playerx * TILE_SIZE, playery * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }


    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}