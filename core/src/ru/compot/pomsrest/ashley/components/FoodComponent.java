package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.scene2d.restaurant.RecipeData;

// компонент маркер для энтити еды (переносимой на столе)
public class FoodComponent implements Component, Pool.Poolable {

    public RecipeData recipe; // рецепт
    public int position; // позиция на столе (0 1 2 3)
    public float positionX, positionY; // позиция на экране (либо = позиции стола либо = позиции мышки)
    public boolean dragging; // переносится ли сейчас

    @Override
    public void reset() {
        dragging = false;
    }
}
