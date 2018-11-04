package com.alimgiray.game.states;

import com.alimgiray.game.Flappy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {

    private Texture background;
    private Texture playButton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playButton = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.pop();
            gsm.push(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(background, 0, 0, Flappy.WIDTH, Flappy.HEIGHT);
        batch.draw(playButton,
                Flappy.WIDTH / 2 - playButton.getWidth() / 2,
                Flappy.HEIGHT / 2 - playButton.getHeight() / 2);
        batch.end();
    }

    @Override
    protected void dispose() {
        background.dispose();
        playButton.dispose();
    }
}
