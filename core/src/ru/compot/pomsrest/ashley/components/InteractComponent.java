package ru.compot.pomsrest.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import ru.compot.pomsrest.ashley.utils.constants.enums.InteractType;

// компонент маркер для области взаимодействия (дверь, плита)
public class InteractComponent implements Component, Pool.Poolable {

    public InteractType interactType = InteractType.WORLD_ENTER_AREA; // тип взаимодействия
    public int interactPriority; // приоритет взаимодействия

    @Override
    public void reset() {
    }
}
