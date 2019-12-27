package myinterfaces;

import myorganism.Position;

public interface CreatureActions {
    public void move();
    public void die();
    public void deductHP(int atk);
}
