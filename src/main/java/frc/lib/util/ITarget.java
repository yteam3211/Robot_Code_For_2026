package frc.lib.util;

import edu.wpi.first.units.Measure;

@FunctionalInterface
public interface ITarget<T extends Measure> {
    public T getTarget();
}
