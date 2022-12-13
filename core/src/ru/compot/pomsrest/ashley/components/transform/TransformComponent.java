package ru.compot.pomsrest.ashley.components.transform;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

// класс содержащий позицию и размер энтити на экране
public class TransformComponent implements Component, Pool.Poolable {

    public float x, y; //позиция где отрисовывается
    public float originX, originY; //центральные точки энтити
    public float width, height; //размеры сущности
    public float scaleX = 1f, scaleY = 1f; //размеры всегда на них умножаются (коэффициент)

    @Override
    public void reset() {
    }
}
