package com.jtn.snowballfigth.cenaries;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.cenaries.stores.Drugstore;
import com.jtn.snowballfigth.cenaries.stores.GunStore;
import com.jtn.snowballfigth.cenaries.stores.Store;

public class Village extends Scenario {
	/**
	 * Imagem que indica que o cenario estao fechado
	 */
	private Texture closeStage;
    /**
     * Imagem que indica que o cenario estao abertos
     */
    private TextureRegion[] openStages;
	/**
	 * Imagem que tapa a bandeira
	 */
	private Texture groundTx, snowTx;
	/**
	 * Verifica os estagios liberados
	 */
	private boolean stage2, stage3, stage4 = false;

	/**
	 * Porta dos estagios
	 */
	private Body stage1Door, stage2Door, stage3Door, stage4Door;
	/**
	 * Verifica se o usuario entrou em uma das lojas
	 */
	private boolean drugstore, gunStore;

	/**
	 * Porta das Lojas
	 */

	private Body drugstoreDoor, gunStoreDoor;

	/**
	 * Cenario da farmacia
	 */
	private Store drugstoreScenario,gunStoreScenario;

	/**
	 * Crai o Vilarego onde o jogo comessa
	 *
	 * @param main
	 *            Classe principal
	 */
	public Village(Main main) {

		super(new Texture("scenario/village_no_schools.png"), main,true);
        //Imagens das escolas desbloqueadas
        this.openStages = new TextureRegion[4];
        //Percorre toda sprite separando as imagens
        Texture t = new Texture("scenario/schools_unlocked.png");
        for (int i = 0 ; i < 4; i++){
            openStages[i] = new TextureRegion(t,i* (t.getWidth()/4),0,(t.getWidth()/4),t.getHeight());
        }

		this.closeStage = new Texture("scenario/school.png");
		this.groundTx = new Texture("scenario/ground-bg.png");
		this.snowTx = new Texture("scenario/snow-bg.png");
		this.drugstoreScenario = new Drugstore(game,this);
		this.gunStoreScenario = new GunStore(game,this);

		// Linhas de limitacao
		limitationLines();
		// Portas
		doors();

	}

	@Override
	public void fontDraw(float delta) {
		//Desenha os textos da farmacia caso o jogador esteja nela
		if(drugstore)
			drugstoreScenario.fontDraw(delta);
		//Desenha os textos da loja de armas caso o jogador esteja nela
		if(gunStore)
			gunStoreScenario.fontDraw(delta);
	}

	@Override
	public void draw(float delta) {
		// Desenha a vila
		batch.draw(scenario, 0, 0, width, height);
		/**
		 * @see drawCloseStages()
		 */
		drawCloseStages();
		// Desenha o jogador
		player.draw(delta);
		//Desenha a farmacia caso o jogador esteja nela
		if (drugstore)
			drugstoreScenario.draw(delta);
		//Desenha a loja caso o jogador esteja nela
		if(gunStore)
			gunStoreScenario.draw(delta);
	}

	@Override
	public void update(float delta) {
		//Atuliza a farmacia caso o jogador esteja nela
		if (drugstore)
			drugstoreScenario.update(delta);
		//Atuliza a loja de armas caso o jogador esteja nela
		if(gunStore)
			gunStoreScenario.update(delta);

	}

	@Override
	public void shapeDraw(float delta) {
		//Desenha os quadrados de selecao da farmacia caso o jogador esteja nela
		if (drugstore)
			drugstoreScenario.shapeDraw(delta);
		//Desenha os quadrados de seleaco da loja de armas caso o jogador esteja nela
		if(gunStore)
			gunStoreScenario.shapeDraw(delta);
	}

	public void handleInput() {
		if (!drugstore && !gunStore) {
			final float velocity = 1f;
			// Tecla para Cima
			if (Gdx.input.isKeyPressed(Input.Keys.UP))
				player.getB2body().setLinearVelocity(0, velocity);
			// Tecla para direita
			else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
				player.getB2body().setLinearVelocity(velocity, 0);
			// Tecla para esquerda
			else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
				player.getB2body().setLinearVelocity(-velocity, 0);
			// tecla para baixo
			else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
				player.getB2body().setLinearVelocity(0, -velocity);
			// nenhuma tecla
			else
				player.getB2body().setLinearVelocity(0, 0);

		}
		else if(drugstore){
			drugstoreScenario.handleInput();
		}
		else if(gunStore){
			gunStoreScenario.handleInput();
		}
	}

	/**
	 * Desenhas os estagios fechados
	 */

	private void drawCloseStages() {
        batch.draw(openStages[3], 300f / Main.PPM, 84f / Main.PPM, 74f / Main.PPM, 35f / Main.PPM);
		// Desenha enqunto o segundo estagio estiver bloqueado
		if (!stage2) {
			// Segundo estagio estagio
			batch.draw(closeStage, 206f / Main.PPM, 31f / Main.PPM, 74f / Main.PPM, 35f / Main.PPM);
			// Tapando a bandeira
			//batch.draw(groundTx, 230f / Main.PPM, 64f / Main.PPM, 27f / Main.PPM, 9f / Main.PPM);
		}
        //Quando o Segundo estagio estiver liberado
        else {
            batch.draw(openStages[2], 206f / Main.PPM, 31f / Main.PPM, 74f / Main.PPM, 35f / Main.PPM);
        }
		// Desenha enqunto o terceiro estagio estiver bloqueado
		if (!stage3) {
			// Terceiro estagio estagio
			batch.draw(closeStage, 115f / Main.PPM, 31f / Main.PPM, 74f / Main.PPM, 35f / Main.PPM);
			// Tapando a bandeira
			//batch.draw(groundTx, 145f / Main.PPM, 64f / Main.PPM, 22f / Main.PPM, 9f / Main.PPM);

		}
        //Quando o Segundo estagio estiver liberado
        else {
            batch.draw(openStages[1], 115f / Main.PPM, 31f / Main.PPM, 74f / Main.PPM, 35f / Main.PPM);
        }
		// Desenha enqunto o quarto estagio estiver bloqueado
		if (!stage4) {
			// Quarto estagio estagio
			batch.draw(closeStage, 22f / Main.PPM, 84f / Main.PPM, 74f / Main.PPM, 35f / Main.PPM);
			// Tapando a bandeira
			//batch.draw(snowTx, 52f / Main.PPM, 117f / Main.PPM, 20f / Main.PPM, 9f / Main.PPM);
		}
        //Quando o Segundo estagio estiver liberado
        else {
            batch.draw(openStages[0], 22f / Main.PPM, 84f / Main.PPM, 74f / Main.PPM, 35f / Main.PPM);
        }
	}

	/**
	 * Cria as limitacoes da Vila
	 */
	private void limitationLines() {
		// Linhas Horizontais
		// Linha Horizontal do Cetro De cima
		createBody(200f / Main.PPM, 113f / Main.PPM, 20f / Main.PPM, 1f / Main.PPM);
		// Linha Horizontal do Cetro De Baixo
		createBody(200f / Main.PPM, 86f / Main.PPM, 20f / Main.PPM, 1f / Main.PPM);
		// Linha Horizontal da Esqurda De Cima
		createBody(115f / Main.PPM, 113f / Main.PPM, 15f / Main.PPM, 1f / Main.PPM);
		// Linha Horizontal da Esqurda De Baixo
		createBody(115f / Main.PPM, 86f / Main.PPM, 15f / Main.PPM, 1f / Main.PPM);
		// Linha Horizontal da Direita de Cima
		createBody(285f / Main.PPM, 113f / Main.PPM, 17f / Main.PPM, 1f / Main.PPM);
		// Linha Horizontal da Direita de Baixo
		createBody(285f / Main.PPM, 86f / Main.PPM, 17f / Main.PPM, 1f / Main.PPM);
		// Linhas Verticais
		// Linha Vertical da Direita em Baixo 1
		createBody(260f / Main.PPM, 74f / Main.PPM, 1f / Main.PPM, 10f / Main.PPM);
		// Linha Vertical da Direita em Baixo 2
		createBody(223f / Main.PPM, 74f / Main.PPM, 1f / Main.PPM, 10f / Main.PPM);
		// Linha Vertical do Esquerda em Baixo 1
		createBody(173f / Main.PPM, 74f / Main.PPM, 1f / Main.PPM, 10f / Main.PPM);
		// Linha Vertical do Esquerda em Baixo 2
		createBody(136f / Main.PPM, 74f / Main.PPM, 1f / Main.PPM, 10f / Main.PPM);
		// Linha Vertical do Direita em Cima 1
		createBody(260f / Main.PPM, 125f / Main.PPM, 1f / Main.PPM, 10f / Main.PPM);
		// Linha Vertical do Direita em Cima 2
		createBody(223f / Main.PPM, 125f / Main.PPM, 1f / Main.PPM, 12f / Main.PPM);
		// Linha Vertical do Esquerda em Cima 1
		createBody(173f / Main.PPM, 125f / Main.PPM, 1f / Main.PPM, 12f / Main.PPM);
		// Linha Vertical do Esquerda em Cima 2
		createBody(136f / Main.PPM, 125f / Main.PPM, 1f / Main.PPM, 12f / Main.PPM);

	}

	/**
	 * Cria as passagens para os estagios e as lojas
	 */
	private void doors() {
		// Mascara
		short mask = Main.PLAYER;
		// Porta da farmacia
		drugstoreDoor = createBody(155f / Main.PPM, 155f / Main.PPM, 10f / Main.PPM, 10f / Main.PPM, Main.DRUGSTORE,
				mask);
		// Porta da Loja de armas
		gunStoreDoor = createBody(245f / Main.PPM, 155f / Main.PPM, 10f / Main.PPM, 10f / Main.PPM, Main.GUN_STORE,
				mask);
		// Porta do primeiro estagio
		stage1Door = createBody(310f / Main.PPM, 98f / Main.PPM, 10f / Main.PPM, 10f / Main.PPM,Main.STAGE1,mask);
		// Porta do segundo estagio
		stage2Door = createBody(245f / Main.PPM, 55f / Main.PPM, 10f / Main.PPM, 10f / Main.PPM);
		// Porta do terceiro estagio
		stage3Door = createBody(155f / Main.PPM, 55f / Main.PPM, 10f / Main.PPM, 10f / Main.PPM);
		// Porta do quarto estagio
		stage4Door = createBody(90f / Main.PPM, 98f / Main.PPM, 10f / Main.PPM, 10f / Main.PPM);

	}

	/**
	 * Verifica se o jogador tocou a porta da farmacia
	 */
	public void drugstore() {
		// caso seja verdadeiro e atualizado para falso
		// caso seja fauso e atualizado para verdadeiro
		if (drugstore) {
			drugstore = false;
		} else {
			drugstore = true;
		}

	}

	/**
	 * Verifica se o jogador tocou a porta da loja de armas
	 */
	public void gunStore() {
		// caso seja verdadeiro e atualizado para falso
		// caso seja fauso e atualizado para verdadeiro
		if (gunStore) {
			gunStore = false;
		} else {
			gunStore = true;
		}

	}

	/**
	 * Verifica se o jogador tocou a porta do primeiro estagio
	 */
	public void stage1() {
        this.game.setScenario(new StageInfo(game.getStage1(),game));
		this.game.setScreen(this.game.getScenario());
		//System.out.println("did");
	}

	/**
	 * Cria um corpo sem filtro
	 *
	 * @param x
	 *            Posicao x
	 * @param y
	 *            Posicao y
	 * @param w
	 *            Largura do corpo
	 * @param h
	 *            Altura do corpo
	 * @return Body
	 */
	private Body createBody(float x, float y, float w, float h) {
		// Preparando configuracoes
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;

		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set(x, y);
		// Criando o corpo
		body = world.createBody(bdef);

		// Configura o tamanho da limitacao
		shape.setAsBox(w, h);
		fdef.shape = shape;
		// Adicionando o tamanho ao corpo
		body.createFixture(fdef).setUserData(this);

		return body;
	}

	/**
	 * Cria um corpo com filtro
	 *
	 * @param x
	 *            Posicao x
	 * @param y
	 *            Posicao y
	 * @param w
	 *            Largura do corpo
	 * @param h
	 *            Altura do corpo
	 *            Filto
	 * @param mask
	 *            Mascara
	 * @return Body
	 */
	private Body createBody(float x, float y, float w, float h, short filer, short mask) {
		// Cria as limitacaes no mapa
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;

		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set(x, y);
		// Coloca a limitacaos no map
		body = world.createBody(bdef);

		// Configura o tamanho da limitacao
		shape.setAsBox(w, h);
		fdef.shape = shape;
		// Adicionando filtro e mascara
		fdef.filter.categoryBits = filer;
		fdef.filter.maskBits = mask;

		body.createFixture(fdef).setUserData(this);

		return body;
	}

    public void reset(){
        player.getB2body().setTransform((Main.V_WIDTH/ Main.PPM)/2f,(Main.V_HEIGHT/ Main.PPM)/2f,0);
    }


}
