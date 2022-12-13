package ru.compot.pomsrest.ashley.systems.render;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;
import ru.compot.pomsrest.ashley.utils.comparators.LayerComparator;
import ru.compot.pomsrest.ashley.utils.constants.Mappers;
import ru.compot.pomsrest.ashley.utils.constants.Priorities;

// система отрисовки текстур
public class RenderSystem extends SortedIteratingSystem {

    private final SpriteBatch batch;

    public RenderSystem(SpriteBatch batch) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get(), LayerComparator.INSTANCE, Priorities.RENDER_SYSTEM);
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) { //проходимся по каждой сущности
        TransformComponent transform = Mappers.TRANSFORM_MAPPER.get(entity);
        TextureComponent texture = Mappers.TEXTURE_MAPPER.get(entity);
        if (!texture.visible) return; //если текстура не должна отображаться, то не отрисовываем
        float width = texture.width > 0f ? texture.width : texture.region.getRegionWidth(); //если в компоненте высота и длина задана, то юзаем их, но если нет - юзаем размер текстуры по умолчанию
        float height = texture.height > 0f ? texture.height : texture.region.getRegionHeight();
        batch.begin(); //пошел процесс отрисовки
        batch.draw(
                texture.region, //текстура
                transform.x, transform.y, //позиция
                transform.originX, transform.originY, //центр
                width, height, //размеры
                transform.scaleX, transform.scaleY, //коэф.
                0f); //поворот
        batch.end(); //финал
    }
}
