package org.example.chpaters.refactor.directions;

public class Right implements Input {

    @Override
    public void handle() {
        moveHorizontal(1);
    }
}
