package ru.compot.pomsrest.ashley.systems.animations;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.Priorities;

// система анимации текстуры
public class TextureAnimationSystem extends IteratingSystem {
    public TextureAnimationSystem() {
        super(Family.all(TextureComponent.class, TextureAnimationComponent.class).get(), Priorities.TEXTURE_ANIMATION_SYSTEM);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureAnimationComponent textureAnimation = Mappers.TEXTURE_ANIMATION_MAPPER.get(entity);
        TextureComponent texture = Mappers.TEXTURE_MAPPER.get(entity);
        if (textureAnimation.currentAnimation == null) { //если не запущены анимации
            if (textureAnimation.idleTexture != null) { //но айдл текстура есть
                texture.region = textureAnimation.idleTexture; //перезаписываем текущую текстуру и обнуляем айдл
                textureAnimation.idleTexture = null;
            }
            return;
        }
        if (textureAnimation.currentAnimation.isAnimationFinished(textureAnimation.estimatedTime)) { //если анимация завершена (естемайтедтайм время прошедшее с начала анимки)
            if (textureAnimation.looping) textureAnimation.estimatedTime = 0f; //чекаем бесконечная ли она, если да, то время с начала анимки обновляем
            else {
                texture.region = textureAnimation.idleTexture;
                textureAnimation.idleTexture = null;
                textureAnimation.reset();
                return;
            }
        }
        texture.region = textureAnimation.currentAnimation.getKeyFrame(textureAnimation.estimatedTime); //получаем текущий кадр анимации и суем в текстуру
        textureAnimation.estimatedTime += deltaTime; //к текущей + время прошлого прохода по сущностям
    }
}
