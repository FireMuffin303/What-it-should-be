package net.firemuffin303.wisb.config;

import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.function.IntFunction;

public interface IdOption<T> {
      IntFunction<T> byId(int id);

}
