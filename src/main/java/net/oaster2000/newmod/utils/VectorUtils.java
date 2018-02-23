package net.oaster2000.newmod.utils;

import net.minecraft.util.math.Vec3d;

public class VectorUtils {

	public static Vec3d divide(Vec3d v, int i) {
		return new Vec3d(v.x/i, v.y/i, v.z/i);
	}

}
