package ru.compot.pomsrest.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import ru.compot.pomsrest.utils.constants.OtherConstants;

// анимированная камера
public class Animated2DCamera extends OrthographicCamera {

    private Vector2Animation animation;
    private float estimatedTime;

    @Override
    public void update() {
        super.update();
        if (animation == null) return;
        Vector2 state = animation.getKeyFrame(estimatedTime);
        position.set(state.x, state.y, 0f);
        if (animation.isAnimationFinished(estimatedTime)) {
            animation = null;
            estimatedTime = 0f;
            return;
        }
        estimatedTime += Gdx.graphics.getDeltaTime();
    }

    public void animate(float x, float y) {
        animation = animation == null
                ? new Vector2Animation(OtherConstants.PLAYER_SPEED, position.x, position.y, x, y, Interpolation.sine)
                : animation.copy(x, y, estimatedTime);
        estimatedTime = 0f;
    }
}
