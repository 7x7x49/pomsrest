package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.scene2d.restaurant.RecipeData;

public class NPCComponent implements Component, Pool.Poolable {

    public float targetX, targetY; // в какую точку идет нпс (диван)
    public float xSitOffset, ySitOffset; // отступы от координат дивана
    public RecipeData food; // какую еду хочет
    public long lastTimeSpawn; // последнее время спавна
    public boolean spawned; // заспавлен ли нпс
    public Entity cloud; // энтити облако

    public long startTimeEat = -1L; // когда начал есть, -1 если не ел после появления

    @Override
    public void reset() {
        food = null;
        spawned = false;
        lastTimeSpawn = 0L;
        startTimeEat = -1L;
    }
}
