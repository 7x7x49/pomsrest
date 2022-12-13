package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

// компонент маркер (делает из сущности облака облако), который задается энтити облачкам над нпс
public class CloudComponent implements Component, Pool.Poolable {

    public Entity foodEntity; // запрашиваемая еда

    @Override
    public void reset() {

    }
}
