package me.aarow.astatine.data.border;

import lombok.Getter;
import lombok.Setter;

public class Shrink {

    @Getter private int seconds;
    @Getter private int blocks;

    @Getter @Setter
    private boolean done = false;

    public Shrink(int seconds, int blocks){
        this.seconds = seconds;
        this.blocks = blocks;
    }
}
