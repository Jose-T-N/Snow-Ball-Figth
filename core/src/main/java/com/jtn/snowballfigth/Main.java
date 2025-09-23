package com.jtn.snowballfigth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jtn.snowballfigth.cenaries.Scenario;
import com.jtn.snowballfigth.cenaries.StageInfo;
import com.jtn.snowballfigth.cenaries.Village;
import com.jtn.snowballfigth.screens.PlayScreen;

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

    private PlayScreen scenario;

    //A energia do poder do player que se manten depois dos estagio
    private float energyPower ;
    private int stage1;
    private int stage2;
    private int stage3;
    private int stage4;
    //Vila
    private Village village;

	@Override
	public void create() {
		batch = new SpriteBatch();
		batchFont = new SpriteBatch();
		shape = new ShapeRenderer();

        stage1 = StageInfo.STAGE1_1;
        stage2 = StageInfo.STAGE1_2;
        stage3 = StageInfo.STAGE1_3;
        stage4 = StageInfo.STAGE1_4;

		font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));

        this.village = new Village(this);
        scenario = village;
        setScreen(scenario);
        //Valor inicial da enegia do poder
        energyPower = 0;

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

    public PlayScreen getScenario() {
        return scenario;
    }

    public void setScenario(PlayScreen scenario) {
        this.scenario = scenario;
    }

    public void screenChange(Screen screen) {
		setScreen(screen);
	}

    public float getEnergyPower() {
        return energyPower;
    }

    public void setEnergyPower(float energyPower) {
        this.energyPower = energyPower;
    }
    /**
     * Informa a faze do estagio 1 em que player esta
     */
    public int getStage1() {
        return stage1;
    }
    /**
     * Informa a faze do estagio 2 em que player esta
     */
    public int getStage2() {
        return stage1;
    }
    /**
     * Informa a faze do estagio 3 em que player esta
     */
    public int getStage3() {
        return stage1;
    }
    /**
     * Informa a faze do estagio 4 em que player esta
     */
    public int getStage4() {
        return stage1;
    }
    /**
     * Vilarejo
     */
    public Village getVillage() {
        village.reset();
        return village;
    }
}
