package net.firemuffin303.wisb.common;

import net.minecraft.sound.SoundCategory;

public interface TurtleAccessor {
    void setCover(boolean value);

    int getCoverTime();

    boolean easierTurtleScute$isCovered();

    void brushing(SoundCategory category);
}
