package ru.compot.pomsrest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.compot.pomsrest.screens.GameScreen;
import ru.compot.pomsrest.screens.world.WorldScreen;

public class Application extends Game { //стирается экран + переход на экран

    public static final Application INSTANCE = new Application();

    private Application() {
    }

    @Override
    public void create() {
        GameCore.INSTANCE.init();
        setScreen(new WorldScreen(WorldScreen.CAMERA_MOVE_OFFSET));
    }

    @Override
    public void render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f, true);
        super.render();
    }

    @Override
    public void dispose() {
        GameCore.INSTANCE.dispose();
    }

    public void setScreenWithTransition(Screen screen) { //переход
        if (this.screen == null) return;
        if (this.screen instanceof GameScreen)
            ((GameScreen) this.screen).getTransition().startTransition(screen);
        else setScreen(screen);
    }
}
