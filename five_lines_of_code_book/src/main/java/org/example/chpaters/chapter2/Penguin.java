package org.example.chpaters.chapter2;

public class Penguin implements Bird {

    private Bird bird;

    @Override
    public boolean hasBeak() {

        bird.hasBeak();

        return false;
    }

    @Override
    public boolean canFly() {

        bird.canFly();

        return false;
    }
}
