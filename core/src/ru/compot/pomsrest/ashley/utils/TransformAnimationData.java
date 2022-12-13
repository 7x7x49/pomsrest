package ru.compot.pomsrest.ashley.utils;

import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.utils.Vector2Animation;

// информация об анимации позиции или размеровв
public class TransformAnimationData {

    public final Vector2Animation animation;
    public final TransformAnimationType type;
    public final Runnable onFinish;
    public float estimatedTime;

    public TransformAnimationData(Vector2Animation animation, TransformAnimationType type, Runnable onFinish) {
        this.animation = animation;
        this.type = type;
        this.onFinish = onFinish;
    }
}
