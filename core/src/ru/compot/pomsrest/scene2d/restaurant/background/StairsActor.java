package ru.compot.pomsrest.scene2d.restaurant.background;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

// лестница
public class StairsActor extends Actor {

    private final TextureRegion stair;

    public StairsActor(TextureRegion stair, float x, float y, float height) {
        this.stair = stair;
        setPosition(x, y);
        setSize(stair.getRegionWidth(), height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float currentHeight = getHeight();
        while (currentHeight > 0f) { //пока не дорисовалась до верха
            batch.draw(stair, getX(), getY() + currentHeight); //+ новые ступеньки
            currentHeight -= stair.getRegionHeight();
        }
    }
}
