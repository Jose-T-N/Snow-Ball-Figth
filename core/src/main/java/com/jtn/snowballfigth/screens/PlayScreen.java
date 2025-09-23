
package com.jtn.snowballfigth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.cenaries.Scenario;
import com.jtn.snowballfigth.characteres.hero.Hero;
import com.jtn.snowballfigth.characteres.hero.Player;
import com.jtn.snowballfigth.characteres.hero.PlayerIcon;
import com.jtn.snowballfigth.tools.WorldContactListener;

public abstract class PlayScreen implements Screen {

	/**
	 * Objeto da classe principal
	 */
	public Main game;
	// SpriteBatch
	public SpriteBatch batch;
	// Camera
	protected OrthographicCamera gamecam;
	private Viewport gameport;
	// Camera das fontes
	protected OrthographicCamera fontcam;
	private Viewport fontport;
	// Village
	private Scenario village;
	// Colisoes e Box2D
	public World world;
	private Box2DDebugRenderer b2dr;
	// Jogador
	protected Hero player;
	// Formas geometricas
	protected ShapeRenderer shape;
	// Textos
	protected BitmapFont font;

	/**
	 *
	 * Cria os cenarios.
	 *
	 * @param game instancia do objeto game
	 * @param villageScenario tipo do "jogador".
	 * Si true jogador do vilarejo se false jogador dos "estagio".
	 * */
	public PlayScreen(Main game,boolean villageScenario,boolean stageInfo) {
		// Objeto da classe principal
		this.game = game;
		// SpriteBatch
		this.batch = game.batch;
		this.shape = game.shape;
		// Texto
		this.font = game.font;
		// Configuacao da camera
		this.gamecam = new OrthographicCamera();
		this.gameport = new FitViewport(Main.V_WIDTH / Main.PPM, Main.V_HEIGHT / Main.PPM, gamecam);
		// Configurando a posicao da camera para o 0,0
		gamecam.position.set(Main.V_WIDTH / 2 / Main.PPM, Main.V_HEIGHT / 2 / Main.PPM, 0);
		// Configuacao da camera das fontes
		this.fontcam = new OrthographicCamera();
		this.fontport = new FitViewport(Main.V_WIDTH, Main.V_HEIGHT, fontcam);
		// Configurando a posicao da camera para o 0,0
		fontcam.position.set(Main.V_WIDTH / 2, Main.V_HEIGHT / 2, 0);
		// Colisoes e Box2D
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new WorldContactListener());
		b2dr = new Box2DDebugRenderer();
        //Caso seja a informacao de estagio
        if (!stageInfo)
            // Instanciando jogado
		    if(villageScenario)
			    this.player = new PlayerIcon(this);
            else
                this.player = new Player(this);
	}

	@Override
	public void show() {

	}

	private void refresh() {
		/**
		 * @see handleInput()
		 */
        handleInput();
		// Configuracao de atualicao do mundo
		world.step(1 / 60f, 6, 2);
		// Atualizando a camera
		gamecam.update();
		// Atualizando a camera das fontes
		fontcam.update();
	}

	@Override
	public void render(float delta) {
		refresh();
		update(delta);
		// Limpa a tela
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Desenha as sprite na tela
		batch.setProjectionMatrix(gamecam.combined);
		batch.begin();
		draw(delta);
		batch.end();

		// Desenha as fontes na tela
		batch.setProjectionMatrix(fontcam.combined);
		batch.begin();
		//Tamanho da fonte
		font.getData().setScale(0.7f);
		//Metodo onde estao as fontes
		fontDraw(delta);
		batch.end();

		// Almetar o tamanho do Stroke
		Gdx.gl.glLineWidth(4);

		// Desenha formas geometricas
		shape.setProjectionMatrix(gamecam.combined);
		shape.begin(ShapeType.Line);
		shape.setAutoShapeType(true);
		shapeDraw(delta);
		shape.end();
		Gdx.gl.glLineWidth(1);
		// Desenha a fisica do jogo
		b2dr.render(world, gamecam.combined);
	}

	@Override
	public void resize(int w, int h) {
		gameport.update(w, h);
		fontport.update(w, h);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		world.dispose();
		b2dr.dispose();
        player.dispose();
	}

	/**
	 * Atualiza as informacoes do cenario e seus deriados
	 */
	public abstract void update(float delta);

	/**
	 * Desenha as informacoes do cenario e seus deriados
	 */
	public abstract void draw(float delta);

	/**
	 * Detectacoo de toque do jogador
	 */
	public abstract void handleInput();

	/**
	 * Desenha formas geometricas
	 */
	public void shapeDraw(float delta) {

	}

	/**
	 * Desenha formas geometricas
	 */
	public void fontDraw(float delta) {

	}

	/**
	 * Retorna o objeto do jogador
	 *
	 * @return Player
	 */
	public Hero getPlayer() {
		return player;
	}

    public SpriteBatch getBatch() {
        return batch;
    }

    public ShapeRenderer getShape() {
        return shape;
    }

    public Main getGame() {
        return game;
    }
}
