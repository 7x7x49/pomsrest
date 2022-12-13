package ru.compot.pomsrest.ashley.utils.constants;

import com.badlogic.ashley.core.ComponentMapper;
import ru.compot.pomsrest.ashley.components.*;
import ru.compot.pomsrest.ashley.components.texture.TextureAnimationComponent;
import ru.compot.pomsrest.ashley.components.texture.TextureComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformAnimationComponent;
import ru.compot.pomsrest.ashley.components.transform.TransformComponent;

// штуки для получения компонентов из энтити
public class Mappers {

    public static final ComponentMapper<TransformComponent> TRANSFORM_MAPPER = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<TextureComponent> TEXTURE_MAPPER = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<TextureAnimationComponent> TEXTURE_ANIMATION_MAPPER = ComponentMapper.getFor(TextureAnimationComponent.class);
    public static final ComponentMapper<TransformAnimationComponent> TRANSFORM_ANIMATION_MAPPER = ComponentMapper.getFor(TransformAnimationComponent.class);
    public static final ComponentMapper<InteractComponent> INTERACT_MAPPER = ComponentMapper.getFor(InteractComponent.class);
    public static final ComponentMapper<PlayerComponent> PLAYER_MAPPER = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<NPCComponent> NPC_MAPPER = ComponentMapper.getFor(NPCComponent.class);
    public static final ComponentMapper<FoodComponent> FOOD_MAPPER = ComponentMapper.getFor(FoodComponent.class);
    public static final ComponentMapper<CloudComponent> CLOUD_MAPPER = ComponentMapper.getFor(CloudComponent.class);

    private Mappers() {
    }
}
