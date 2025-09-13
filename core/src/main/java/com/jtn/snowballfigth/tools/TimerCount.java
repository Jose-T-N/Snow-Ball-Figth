package com.jtn.snowballfigth.tools;

import com.badlogic.gdx.Gdx;

import java.util.Random;

public class TimerCount {

	private float timer,dt;
	public TimerCount() {

	}
	//Atualiza o cronometro
	public float update() {

		dt +=  Gdx.graphics.getDeltaTime();

		if(dt >= 0.5f) {
			timer += 0.5f;
			dt = 0;
		}

		return timer;
	}
	//Retorna o valor do cronometro
	public float getTimer() {
		return timer;
	}

	//reseta o cronometro
	public void resetTimer() {
		timer = 0;
	}

    public static float randomFloatNumber(float min , float max){
        Random rand = new Random();
        return rand.nextFloat() * (max - min) + min;

    }


}
