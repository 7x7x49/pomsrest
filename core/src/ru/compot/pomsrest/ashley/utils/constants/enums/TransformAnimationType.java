package ru.compot.pomsrest.ashley.utils.constants.enums;

import com.badlogic.gdx.math.Vector2;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;

// типы анимации позиции или размеров
public enum TransformAnimationType {

    POSITION((transform, state) -> {
        transform.x = state.x;
        transform.y = state.y;
    }),
    SCALE((transform, state) -> {
        transform.scaleX = state.x;
        transform.scaleY = state.y;
    });

    private final TransformAnimationApplier action;

    TransformAnimationType(TransformAnimationApplier action) {
        this.action = action;
    }

    public void acceptAction(TransformComponent transform, Vector2 state) {
        action.apply(transform, state);
    }

    @FunctionalInterface
    private interface TransformAnimationApplier {
        void apply(TransformComponent transform, Vector2 state);
    }
}
