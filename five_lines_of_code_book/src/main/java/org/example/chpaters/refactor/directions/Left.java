package org.example.chpaters.refactor.directions;

public class Left implements Input {

    @Override
    public void handle() {
        moveHorizontal(-1);
    }
}
