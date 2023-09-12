package org.example.chpaters.refactor.directions;

public class Up implements Input {

    @Override
    public void handle() {
        moveHorizontal(-1);
    }
}
