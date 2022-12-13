package ru.compot.pomsrest.ashley.components.texture;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

import java.util.concurrent.ConcurrentHashMap;

// компонент анимации текстуры
public class TextureAnimationComponent implements Component, Pool.Poolable {

    public final ConcurrentHashMap<Integer, AnimationData> animations = new ConcurrentHashMap<>(); // карта с инфорацией о каждой анимации
    public Animation<TextureRegion> currentAnimation; // текущая проигрывающаяся анимация
    public TextureRegion idleTexture; // текстура при паузе или конце анимации
    public float estimatedTime; // сколько времени прошло после старта анимации
    public boolean looping; // должна ли вечно повторяться анимация

    /**
     * анимируем ентити
     * @param id ключ анимации
     * @param looping должна ли анимка повторяться вечно
     */
    public void animate(int id, boolean looping) {
        this.reset();
        AnimationData data = animations.get(id); //передаём ключ (id), получаем инфу
        this.currentAnimation = data.animation;
        this.idleTexture = data.idleTexture;
        this.looping = looping;
    }

    /**
     * добавляет анимацию в карту
     * @param id ключ анимации
     * @param region скорость кадра и кадры анимации
     * @param idleTexture текстура стопа анимации
     */
    public void add(int id, Animation<TextureRegion> region, TextureRegion idleTexture) { //скорость кадра и все кадры
        animations.put(id, new AnimationData(region, idleTexture));
    }

    /**
     * останавливает все анимации
     */
    @Override
    public void reset() { //удаляет текущую анимацию
        currentAnimation = null;
        estimatedTime = 0f;
        looping = false;
    }

    /**
     * класс хранящий основную инфу о анимации
     */
    public static class AnimationData {
        public final Animation<TextureRegion> animation; // скорость кадра и кадры
        public final TextureRegion idleTexture; // текстура стопа

        public AnimationData(Animation<TextureRegion> animation, TextureRegion idleTexture) {
            this.animation = animation;
            this.idleTexture = idleTexture;
        }
    }
}
