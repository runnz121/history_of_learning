package org.example.chpaters.refactor.directions;

public class Down implements Input {

    @Override
    public void handle() {
        moveHorizontal(1);
    }
}
