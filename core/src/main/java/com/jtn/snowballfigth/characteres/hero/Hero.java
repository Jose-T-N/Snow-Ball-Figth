package com.jtn.snowballfigth.characteres.hero;

import com.badlogic.gdx.physics.box2d.Body;

public interface Hero {

	public void draw(float delta);

	public void update(float delta);

	public float w();

	public float h();

	public Body getB2body();

    public float getLife();

	/**Mostra o movimento atual do player*/
	public Movs getMov();
	/**Configura o movimento do player*/
	public void setMov(Movs mov);

	public enum Movs {
		WALK, STOP, ATTACK, SPECIAL
	};

    public void dispose();
}
