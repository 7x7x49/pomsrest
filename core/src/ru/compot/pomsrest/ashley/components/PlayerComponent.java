package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.utils.Animated2DCamera;

public class PlayerComponent implements Component, Pool.Poolable {

    public final Vector2 interactPoint = new Vector2(); // точка взаимодействия с экраном
    public final Vector2 mousePoint = new Vector2(); // текущая позиция мыши
    public boolean dragging; // зажата ли левая кнопка мыши сейчас
    public Screen screen; // экран где находится плеер
    public Animated2DCamera camera; // камера плеера
    public boolean interactRequested; // запрошено ли взаимодействие
    public boolean moveBlocked; // передвижение отключено

    @Override
    public void reset() {
        interactPoint.set(0f, 0f);
        interactRequested = false;
        moveBlocked = false;
        dragging = false;
    }

    /**
     * запрашивает взаимодействие
     * @param x
     * @param y
     */
    public void interact(float x, float y) {
        interactPoint.set(x, y);
        interactRequested = true;
    }
}
