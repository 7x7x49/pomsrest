package ru.compot.pomsrest.ashley.systems.animations;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.TransformAnimationData;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.Priorities;

// система анимации позиции
public class TransformAnimationSystem extends IteratingSystem {

    public TransformAnimationSystem() {
        super(Family.all(TransformComponent.class, TransformAnimationComponent.class).get(), Priorities.TRANSFORM_ANIMATION_SYSTEM);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) { //проходимся по каждой ентити
        TransformAnimationComponent transformAnimation = Mappers.TRANSFORM_ANIMATION_MAPPER.get(entity); //извлекаем трансформанимейшнкомпонент а потом трансформкомпонент
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(entity);
        for (TransformAnimationData tad : transformAnimation.animations) { //смотрим в его лист запущенных анимаций, проходимся по всем анимках в нём
            Vector2 state = getAnimationState(tad, deltaTime); //возвращает текущий кадр анимки
            tad.type.acceptAction(transform, state); //позиция или размер (тайп - логика изменения) ((полученный кадр применяется к сущности))
            runFinishActions(transformAnimation, tad); //чекаем если анимка закончилась (прошла) то запускаем тот раннбл
        }
    }

    private Vector2 getAnimationState(TransformAnimationData tad, float deltaTime) { //возвращает кадр в зависимости от прошедшего времени с начала анимки
        Vector2 result = tad.animation.getKeyFrame(tad.estimatedTime);
        tad.estimatedTime += deltaTime;
        return result;
    }

    private void runFinishActions(TransformAnimationComponent component, TransformAnimationData data) { //запускает действие при конце анимки если она всё
        if (!data.animation.isAnimationFinished(data.estimatedTime)) return;
        Runnable onFinish = data.onFinish;
        if (onFinish != null) onFinish.run();
        component.animations.remove(data);
    }
}
