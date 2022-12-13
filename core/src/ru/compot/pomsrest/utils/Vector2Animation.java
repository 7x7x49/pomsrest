package ru.compot.pomsrest.utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class Vector2Animation { //анимация координат, плавное передвижение

    private final Vector2 start, distance, end;
    private final Interpolation interpolation;
    private final float speed;
    private final float time;

    public Vector2Animation(float speed, float startX, float startY, float destX, float destY, Interpolation interpolation) {
        this.start = new Vector2(startX, startY);
        this.distance = new Vector2(destX - startX, destY - startY);
        this.end = new Vector2(destX, destY);
        this.interpolation = interpolation == null ? Interpolation.linear : interpolation;
        this.speed = speed;
        this.time = (float) (Math.sqrt(distance.x * distance.x + distance.y * distance.y) / speed);
    }

    public Vector2 getKeyFrame(float estimatedTime) {
        if (isAnimationFinished(estimatedTime)) return end;
        float multiplier = interpolation.apply(estimatedTime / time);
        return new Vector2(start.x + distance.x * multiplier, start.y + distance.y * multiplier);
    }

    public boolean isAnimationFinished(float estimatedTime) {
        return estimatedTime >= time;
    }

    public Vector2Animation copy(float endX, float endY, float lastEstimatedTime) {
        Vector2 state = getKeyFrame(lastEstimatedTime);
        return new Vector2Animation(speed, state.x, state.y, endX, endY, interpolation);
    }
}