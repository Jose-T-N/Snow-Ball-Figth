package com.jtn.snowballfigth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jtn.snowballfigth.cenaries.Scenario;
import com.jtn.snowballfigth.cenaries.Village;

public class Main extends Game {

	// Sprites e Texturas
	public SpriteBatch batch;
	// Formas geometricas
	public ShapeRenderer shape;
	// Textos e numeros
	public BitmapFont font;
	// Sprites e Texturas
	public SpriteBatch batchFont;

	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final int PPM = 100;
	public static final short PLAYER = 1;
	public static final short DRUGSTORE = 2;
	public static final short GUN_STORE = 4;
	public static final short STAGE1 = 8;
	public static final short STAGE2 = 16;
	public static final short STAGE3 = 32;
	public static final short STAGE4 = 64;
	public static final short ENEMY = 128;
	public static final short BULLET = 256;
	public static final short SHOOT = 512;
    public static final short BULLET_ENEMY = 1024;

    private Scenario scenario;

	@Override
	public void create() {
		batch = new SpriteBatch();
		batchFont = new SpriteBatch();
		shape = new ShapeRenderer();

		font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));

        scenario = new Village(this);
        setScreen(scenario);

	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
        scenario.dispose();
	}

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public void screenChange(Screen screen) {
		setScreen(screen);
	}

}
