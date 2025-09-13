package com.jtn.snowballfigth.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.enemys.Enemy;

public class LifeBar {
	private Animation<TextureRegion> lifeBar;
	private float powerBarW, powerBarH, checkW, checkH,checkShadowW,checkShadowH;
	private Enemy enemy;
	private SpriteBatch batch;
	private float delta,life;
	public final static float MAX_LIFE = (float) 3.5;
	public final float LIFE_HIT = .5f;

	/**
	 * Barra que mostra a forca do arremeco
	 */
	public LifeBar(Enemy enemy) {
		this.enemy = enemy;
		this.batch = enemy.getBatch();
		//animacao do PowerBar
		Texture powerBarTexture = new Texture("util/power-bar.png");
		Array<TextureRegion> array = new Array<>();
		for(int i = 0;i<8;i++) {
			array.add(new TextureRegion(powerBarTexture, i * powerBarTexture.getWidth()/8, 0,5,23));
		}
		lifeBar = new Animation<>(LIFE_HIT, array);
		//tamanhos PowerBar
		powerBarW = 5f/Main.PPM;
		powerBarH = 23f/Main.PPM;

		life = MAX_LIFE;
	}

	/**
     * ---------------------------------------------------------------
	 *O contador vai de 1 a 8 ( 1 e sem sangue e 8  e sangue completo)
     * ---------------------------------------------------------------
	 **/
	public void draw() {
        //------------------------------------------------------------
        // -- A variavel life representa o valor do frame do sangue--
        // -- que vai de 1 a 7.5 -------------------------------------
        //------------------------------------------------------------
		batch.draw(lifeBar.getKeyFrame(life,true), enemy.b2body.getPosition().x + enemy.w() / 2, enemy.b2body.getPosition().y,powerBarW,powerBarH);
	}
	/**
	 * reseta o Powerbar
	 *
	 * */
	public void reset() {
		life = 0;
	}
	/**
	 * Retorna o valor do lifebar
	 * */
	public float getLife() {
		return life;
	}
	/**
	 * muda a o valor do lifebar
     * @param  life valor da vida atual do personagem
	 * */
	public void setLife(float life) {
		//---------------------------------------------------------
        // --- Diminui o valor da vida do personagem na barra -----
        //---------------------------------------------------------
        this.life =  life;
        //O valor da vida nao pode ser menor que zero
        if (this.life <= 0)
            this.life = 0;
	}

    public static float getMAX_LIFE() {
        return MAX_LIFE;
    }
}
