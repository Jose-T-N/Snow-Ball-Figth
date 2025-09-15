package com.jtn.snowballfigth.characteres.hero;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.itens.Item;
import com.jtn.snowballfigth.itens.bullets.Bullet;
import com.jtn.snowballfigth.screens.PlayScreen;

public class PlayerIcon implements Hero {

	private World world;
	public Body b2body;
	private SpriteBatch batch;
	private Texture playerIcon;
	private float w,h;
	private boolean drawPlayer;
	//Quantia de dinheiro do jogador
	private int maney;
    private float life;
    private ArrayList<Item> itens;

	public PlayerIcon(PlayScreen screen) {

		world = screen.world;

		this.batch = screen.batch;
		this.playerIcon = new Texture("characteres/hero/hero_icon.png");

		this.w = 16f/Main.PPM;
		this.h = 16f/Main.PPM;

		drawPlayer = true;

		definePlayer();

		this.maney = 50;

		//Lista de itens
		itens = Bag.getContext(this).getItens();

	}
	/**
	 * Desenha os graficos da jogador
	 */
	public void draw(float delta) {
		if(drawPlayer) {
			batch.draw(playerIcon,b2body.getPosition().x-w/2,b2body.getPosition().y-h/2,w,h);
		}
	}
	/**
	 * Atuliza as infomacoes do Jogador e derivados
	 */
	public void update(float delta) {

	}
	/**
	 * Se for igual a false apagara o jogador da tela
	 * @param drawPlayer
	 */
	public void setDrawPlayer(boolean drawPlayer) {
		this.drawPlayer = drawPlayer;
	}
	/**
	 * Retorna a quantia de dinheiro
	 * @return money
	 */
	public int getManey() {
		return maney;
	}
	/**
	 *
	 * Configura a quantia em dinheiro
	 *
	 * @param maney Quantia em dinheiro
	 */
	public void setManey(int maney) {
		this.maney = maney;
	}
	/**
	 * Adicina itens a lista
	 * Retorna true se o item ainda nao foi adicionado
	 * Retorna false se o item for duplicado
	 *
	 */
	public boolean addItem(Item item) {
		if(!itens.contains(item)) {
			itens.add(item);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Cria o corpo fisico do jogador
	 */
	protected void definePlayer() {
		// Definindo a posicao do corpo
		BodyDef bdef = new BodyDef();
		bdef.position.set(200f / Main.PPM, 99f / Main.PPM);
		bdef.type = BodyType.DynamicBody;

		// criando o corpo
		this.b2body = world.createBody(bdef);

		// Definido o tamanho do corpo
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(7f / Main.PPM, 7f / Main.PPM);
		fdef.shape = shape;

		// Adicionado tamanho ao corpo
		this.b2body.createFixture(fdef);

	}
	@Override
	public Body getB2body() {
		// TODO Auto-generated method stub
		return b2body;
	}

    @Override
    public float getLife() {
        return life;
    }

    @Override
	public float w() {
		// TODO Auto-generated method stub
		return w;
	}

	@Override
	public float h() {
		// TODO Auto-generated method stub
		return h;
	}
	@Override
	public Movs getMov() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setMov(Movs mov) {
		// TODO Auto-generated method stub

	}

    @Override
    public void dispose() {

    }

}
