package ru.compot.pomsrest.ashley.utils.comparators;

import com.badlogic.ashley.core.Entity;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;

import java.util.Comparator;

// штука, сортирующая текстуры по их слою
public class LayerComparator implements Comparator<Entity> {

    public static final LayerComparator INSTANCE = new LayerComparator();

    private LayerComparator() {
    }

    @Override
    public int compare(Entity o1, Entity o2) {
        return Integer.compare(Mappers.TEXTURE_MAPPER.get(o1).zIndex, Mappers.TEXTURE_MAPPER.get(o2).zIndex);
    }
}
