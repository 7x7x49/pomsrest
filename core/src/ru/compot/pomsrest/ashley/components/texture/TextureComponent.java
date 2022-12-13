package ru.compot.pomsrest.ashley.components.texture;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

// компонент текстуры
public class TextureComponent implements Component, Pool.Poolable {

    public TextureRegion region; // текстура энтити
    public int zIndex; // слой
    public float width, height; // размеры
    public boolean visible = true; // должна ли в данный момент отрисовываться картинка

    @Override
    public void reset() {
        visible = true;
    }
}
