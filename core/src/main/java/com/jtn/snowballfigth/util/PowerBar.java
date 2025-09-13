package com.jtn.snowballfigth.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.hero.Player;

public class PowerBar {
	private Animation<TextureRegion> powerBar;
	private float powerBarW, powerBarH, checkW, checkH,checkShadowW,checkShadowH;
	private Player player;
	private SpriteBatch batch;
	private float delta,power;

	/**
	 * Barra que mostra a forca do arremeco
	 */
	public PowerBar(Player player) {
		this.player = player;
		this.batch = player.getBatch();
		//animacao do PowerBar
		Texture powerBarTexture = new Texture("util/power-bar.png");
		Array<TextureRegion> array = new Array<>();
		for(int i = 0;i<8;i++) {
			array.add(new TextureRegion(powerBarTexture, i * powerBarTexture.getWidth()/8, 0,5,23));
		}
		powerBar = new Animation<>(0.2f, array);
		//tamanhos PowerBar 
		powerBarW = 5f/Main.PPM;
		powerBarH = 23f/Main.PPM;
	}

	/**
	 * 
	 **/
	public void draw() {
		batch.draw(powerBar.getKeyFrame(power,true), player.b2body.getPosition().x + player.w() / 2, player.b2body.getPosition().y,powerBarW,powerBarH);
		if(power <= 1.4f) {
			delta += Gdx.graphics.getDeltaTime();
		}
		if(delta > 0.2) {
			power += 0.2f;
			delta = 0;
		}
		
	}
	/**
	 * reseta o Powerbar
	 * 
	 * */
	public void reset() {
		power = 0;
	}
	/**
	 * Retorna a forca do powerbar
	 * */
	public float getPower() {
		return power;
	}
}
