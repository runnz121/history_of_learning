package org.example.chpaters.refactor;

import java.util.Objects;

import org.example.Main;
import org.example.chpaters.refactor.directions.Input;
import org.example.chpaters.refactor.directions.Input2;

public class MainRefactoring {

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

    public enum RawInput {
        UP(0), DOWN(1), LEFT(2), RIGHT(3)
        ;

        private final int code;

        RawInput(int code) {
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
    public static Main.Input[] inputs = {};

    public void update() {

        handleInputs();

        updateMap();
    }

    private void handleInputs() {

        while (inputs.length > 0) {

            Input input = Main.Input.values();

            input.handle();
        }
    }

    // -> Input 인터페이스가 해당 메서드를 구현
    // private void handleInput(Input input) {
    //
    //     input.handle();
    // }

    private void updateMap() {

        for (int y = map.length - 1; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                if ((map[y][x] == Main.Tile.STONE.getCode() || map[y][x] == Main.Tile.FALLING_STONE.getCode())
                    && map[y + 1][x] == Main.Tile.AIR.getCode()) {
                    map[y + 1][x] = Main.Tile.FALLING_STONE.getCode();
                    map[y][x] = Main.Tile.AIR.getCode();
                } else if ((map[y][x] == Main.Tile.BOX.getCode() || map[y][x] == Main.Tile.FALLING_BOX.getCode())
                    && map[y + 1][x] == Main.Tile.AIR.getCode()) {
                    map[y + 1][x] = Main.Tile.FALLING_BOX.getCode();
                    map[y][x] = Main.Tile.AIR.getCode();
                } else if (map[y][x] == Main.Tile.FALLING_STONE.getCode()) {
                    map[y][x] = Main.Tile.STONE.getCode();
                } else if (map[y][x] == Main.Tile.FALLING_BOX.getCode()) {
                    map[y][x] = Main.Tile.BOX.getCode();
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
                if (map[y][x] === Main.Tile.FLUX)
                    g.fillStyle = "#ccffcc";
                else if (map[y][x] === Main.Tile.UNBREAKABLE)
                    g.fillStyle = "#999999";
                else if (map[y][x] === Main.Tile.STONE || map[y][x] === Main.Tile.FALLING_STONE)
                    g.fillStyle = "#0000cc";
                else if (map[y][x] === Main.Tile.BOX || map[y][x] === Main.Tile.FALLING_BOX)
                    g.fillStyle = "#8b4513";
                else if (map[y][x] === Main.Tile.KEY1 || map[y][x] === Main.Tile.LOCK1)
                    g.fillStyle = "#ffcc00";
                else if (map[y][x] === Main.Tile.KEY2 || map[y][x] === Main.Tile.LOCK2)
                    g.fillStyle = "#00ccff";

                if (map[y][x] !== Main.Tile.AIR && map[y][x] !== Main.Tile.PLAYER)
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void drawPlayer(Objects g) {
        // Draw player
        g.fillStyle = "#ff0000";
        g.fillRect(playerx * TILE_SIZE, playery * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}
