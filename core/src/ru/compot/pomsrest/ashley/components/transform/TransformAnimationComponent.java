package ru.compot.pomsrest.ashley.components.transform;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.ashley.utils.TransformAnimationData;
import ru.compot.pomsrest.ashley.utils.constants.enums.TransformAnimationType;
import ru.compot.pomsrest.utils.Vector2Animation;

import java.util.concurrent.CopyOnWriteArrayList;

// анимация позиции, размеров ентити
public class TransformAnimationComponent implements Component, Pool.Poolable { //передвижение альпаки

    public final CopyOnWriteArrayList<TransformAnimationData> animations = new CopyOnWriteArrayList<>(); // список запущенных анимаций

    @Override
    public void reset() {
        animations.clear();
    }

    /**
     * запускает анимацию
     * @param type тип изменения
     * @param speed скорость
     * @param startX х начала
     * @param startY у начала
     * @param destX х конца
     * @param destY у конца
     * @param onFinish логика, которая будет выполнятся в конце анимации
     */
    public void animate(TransformAnimationType type, float speed, float startX, float startY, float destX, float destY, Runnable onFinish) {
        stopAnimations(type);
        animations.add(new TransformAnimationData(new Vector2Animation(speed, startX, startY, destX, destY, null), type, onFinish));
    }

    /**
     * останавливает анимации по определенному типу
     * @param type
     */
    @SuppressWarnings("all")
    public void stopAnimations(TransformAnimationType type) {
        for (TransformAnimationData tad : animations) {
            if (tad.type == type) animations.remove(tad);
        }
    }
}
