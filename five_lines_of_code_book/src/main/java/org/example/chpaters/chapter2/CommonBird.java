package org.example.chpaters.chapter2;

public class CommonBird implements Bird {



    @Override
    public boolean hasBeak() {
        return false;
    }

    @Override
    public boolean canFly() {
        return true;
    }
}
