package ru.compot.pomsrest.scene2d.restaurant.background;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

// кухонные машины (холодос, плита...)
public class KitchenActor extends Actor {

    private final TextureRegion sink;
    private final TextureRegion cupboard;
    private final TextureRegion stove;
    private final TextureRegion fridge;
    private final float sinkX, cupboardX, stoveX, fridgeX;

    public KitchenActor(TextureRegion sink, TextureRegion cupboard, TextureRegion stove, TextureRegion fridge, float x, float prefWidth) {
        this.sink = sink;
        this.cupboard = cupboard;
        this.stove = stove;
        this.fridge = fridge;
        float offsetSum = prefWidth - sink.getRegionWidth() - cupboard.getRegionWidth() - stove.getRegionWidth() - fridge.getRegionWidth();
        float offset = offsetSum > 50f ? offsetSum / 5f : 50f;
        this.sinkX = offset; //высчитываем позиции мкухонных машин
        this.cupboardX = sinkX + sink.getRegionWidth() + offset; //высчитываем позиции мкухонных машин
        this.stoveX = cupboardX + cupboard.getRegionWidth() + offset; //высчитываем позиции мкухонных машин
        this.fridgeX = stoveX + stove.getRegionWidth() + offset; //высчитываем позиции мкухонных машин
        setX(x);
        setWidth(fridgeX + fridge.getRegionWidth() + offset); //адаптация под большие экраны
        setHeight(Math.max(sink.getRegionHeight(), Math.max(cupboard.getRegionHeight(), Math.max(stove.getRegionHeight(), fridge.getRegionHeight()))));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) { //отрисовка
        batch.draw(sink, getX() + sinkX, getY());
        batch.draw(cupboard, getX() + cupboardX, getY());
        batch.draw(stove, getX() + stoveX, getY());
        batch.draw(fridge, getX() + fridgeX, getY());
    }

    public float getStoveX() {
        return stoveX;
    }
}
