package com.jtn.snowballfigth.characteres.enemys;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.hero.Hero;
import com.jtn.snowballfigth.itens.bullets.Bullet;
import com.jtn.snowballfigth.screens.PlayScreen;
import com.jtn.snowballfigth.tools.TimerCount;
import com.jtn.snowballfigth.util.LifeBar;
import com.jtn.snowballfigth.util.Map;
import com.jtn.snowballfigth.util.PowerBar;

public class Enemy implements Disposable {

	private SpriteBatch batch;
	private Animation<TextureRegion> enemy, dead;
	private float w, h, x, y;
	private boolean drawPlayer;
	public Body b2body;
	private World world;
	private ArrayList<Bullet> bullets;
    //--------------------------------------------------------
    //--------------Delay entre um tiro e outro --------------
    //--------------------------------------------------------
    private TimerCount shootDelayTimeCount;
    private float shootDelayTime;
	private PlayScreen screen;
	private LifeBar lifeBar;
	private boolean lifeBarOn;
	// Destruido o inimigo
	private boolean destroyed;
	private boolean destring, lastAnimation;
	private float timer;
	private float life;
	private float max_life ;
    private final float resistance = 1.5f;
    /// ------------------------------------------------------------------------
    /// ----------- OS intervalos de delay enter um tiro e outro -----------------
    /// ------------------------------------------------------------------------
    private final float FIRST_TIME_SHOOT = 1;
    private final float SECOND_TIME_SHOOT = 8;
    /// ------------------------------------------------------------------------
    /// ----------- OS intervalos de delay enter um tiro e outro -----------------
    /// ------------------------------------------------------------------------
    private final float FIRST_TIME_STEEP = 1;
    private final float SECOND_TIME_STEEP = 8;
    //---------------------------------------------------------------------------
    // ----------------- Delay entre um passo e outro ---------------------------
    //---------------------------------------------------------------------------
    private TimerCount steepDelayTimeCount;
    private float steepDelayTime;
    //-------------------------------------------------------------------------
    //-------------- determina o tamanho do passo do inimigo ------------------
    private final float steep = 10f/Main.PPM;
    // ---------------------------------------------------------------------------
    // -----------------Determina os limites que o inimigo pode andar -----------
    // --------------------------------------------------------------------------
    private final float LIMITER_BOTTON = ((Main.V_HEIGHT/Main.PPM)/2f) + 10f /Main.PPM; ;
    private final float LIMITER_TOP = ((Main.V_HEIGHT/Main.PPM)/2f) + 40f /Main.PPM;

    /**
     * dano da bola de neve do inimigo
     */
    private float shoot_power = 0.5f;

    @Override
    public void dispose() {
        for (Bullet b : bullets){
            b.dispose();
        }
    }

    public enum Movs {
		DEFALT, ATTACK, DEAD
	};

	private Movs mov;
	private float timer_defalt, timer_attack;

	public Enemy(PlayScreen screen, float x, float y) {

		world = screen.world;
		this.screen = screen;

		this.batch = screen.batch;
		// Imagem do inimigo
		Texture tex = new Texture("characteres/enemy1/enemy-defalt.png");
		Array<TextureRegion> array = new Array<TextureRegion>();
		// Criando animacao
		for (int i = 0; i < 2; i++) {
			array.add(new TextureRegion(tex, i * tex.getWidth() / 2, 0, tex.getWidth() / 2, tex.getHeight()));
		}
		// Animacao
		enemy = new Animation<TextureRegion>(0.2f, array);
		array.clear();
		///
		///
		///
		// Imagem do inimigo morto
		tex = new Texture("characteres/enemy1/enemy-dead.png");
		// Criando animacao
		for (int i = 0; i < 1; i++) {
			array.add(new TextureRegion(tex, i * tex.getWidth(), 0, tex.getWidth(), tex.getHeight()));
		}
		// Animacao
		dead = new Animation<TextureRegion>(0.2f, array);
		array.clear();

		this.w = 20f / Main.PPM;
		this.h = 20f / Main.PPM;
		this.x = x / Main.PPM;
		this.y = y / Main.PPM;

		drawPlayer = true;

		defineEnemy();

		// Lista de balas
		bullets = new ArrayList<>();
		// Power bar
		lifeBar = new LifeBar(this);
		// Movimento
		mov = Movs.DEFALT;
		//lifebar
		lifeBarOn = true;
		//valor da vida
		max_life = LifeBar.getMAX_LIFE();
		life = max_life;
        //----------------------------------------------------------------------
        // ----------------- Contador de tempo entre um tiro e outro ----------------------
        //--------------------------------------------------------------
        shootDelayTime = TimerCount.randomFloatNumber(FIRST_TIME_SHOOT/2f,SECOND_TIME_SHOOT/2f);
        shootDelayTimeCount = new TimerCount();
        //----------------------------------------------------------------------
        // ----------------- Contador de tempo entre um PASSO e outro ----------------------
        //--------------------------------------------------------------
        steepDelayTime = TimerCount.randomFloatNumber(FIRST_TIME_STEEP,SECOND_TIME_STEEP);
        steepDelayTimeCount = new TimerCount();

	}

	/**
	 * Desenha os graficos da jogador
	 */
	public void draw(float delta) {
		if (drawPlayer) {
			// Caso o player esteja em movimento a animacao e atualizada.
			// Caso parado a animacao para.
			if (mov != Movs.ATTACK && mov != Movs.DEAD && mov == Movs.DEFALT) {
				//defalt
				batch.draw(enemy.getKeyFrame(timer_defalt, true), b2body.getPosition().x - w / 2,
						b2body.getPosition().y - (h / 4) - 5f / Main.PPM, w, h);
				timer_defalt += delta;

			} else if(mov == Movs.ATTACK && mov != Movs.DEAD) {
				//ataque
				batch.draw(dead.getKeyFrame(timer_attack, true), b2body.getPosition().x - w / 2,
						b2body.getPosition().y - h / 2, w, h);
				timer_attack += delta;
			}else if(mov != Movs.ATTACK && mov == Movs.DEAD && lastAnimation) {
				//morto
				batch.draw(dead.getKeyFrame(timer_attack, true), b2body.getPosition().x - w / 2,
						b2body.getPosition().y - h / 2, w, h);
			}
		}
		for (Bullet bullet : bullets) {
            bullet.draw();
		}
		if (lifeBarOn) {
			lifeBar.draw();
		}
	}

	/**
	 * Atuliza as infomacoes do Jogador e derivados
     * Tambem faz ele se mover e atira
     *
     *
	 */
	public void update(float delta) {
        //------------------------------------------------------------------------------------------
		// ---------------------- Enquanto o inimigo estiver vivo ------------------------------
        //---------------------------------------------------------------------------------------
		if (!destring && !lastAnimation && !destroyed) {
			// atuliza as balas
			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).update();
				if (bullets.get(i).isDestroyed()) {
					bullets.remove(i);
				}
			}
            //---------------------------------------------------------------------------------------------------
            // --------------------------------- Efetua o tiro do inimigo ---------------------------------------
            // -----------------------------------------------------
            // -- Cria um tempo aletario para o inimigo atirar -----
            // -----------------------------------------------------
            shootDelayTimeCount.update();
            //-----------------------------------------------------------
            //Se o contator chegar no tempo determido
            // ----------------------- -o tiro e efetuado -----------------------
            //------------------------
            if(shootDelayTimeCount.getTimer() >= shootDelayTime){
                bullets.add(new Bullet(this,screen,60,shoot_power));
                shootDelayTimeCount.resetTimer();
                /// escolhe outro tempo para efetuar o tiro
                shootDelayTime = TimerCount.randomFloatNumber(FIRST_TIME_SHOOT, SECOND_TIME_SHOOT);
            }
            //---------------------------------------------------------------------------------
            // -------------------Passos do inimigo -------------------------------------------
            //---------------------------------------------------------------------------------
            steepDelayTimeCount.update();
            //-----------------------------------------------------------
            //Se o contator chegar no tempo determido
            // ----------------------- -o tiro e efetuado -----------------------
            //------------------------
            if(steepDelayTimeCount.getTimer() >= steepDelayTime){
                steepDelayTimeCount.resetTimer();
                //Efetua o passo
                // -------------------------------------------------------------------------------------
                // ------ Escolhe um intervalo randomico para decidir se vai para frente para traz ----
                //  ------------------------ Ou para a esquerda ou direita -----------------------------
                //--------------------------------------------------------------------------------------
                Random randon = new Random();
                int min = 0;
                int max = 11;
                // obs:  nr - randon number
                int nr = randon.nextInt((max - min) + 1) + min;
                //----------------
                // -- Faz nada --
                //----------------
                //if(nr <=2){
                    //System.out.println("n");
                //}
                //----------------
                // -- Esquerda --
                //----------------
                if(nr >=3 && nr<=5){
                    b2body.setTransform(b2body.getPosition().x - steep,b2body.getPosition().y,0);
                    // Caso o inimigo tentar sair da tela
                    if (b2body.getPosition().x <= 10f/Main.PPM ){
                        b2body.setTransform(10f/Main.PPM,b2body.getPosition().y,0);
                    }
                }
                //----------------
                // -- Direita --
                //----------------
                else if(nr <=8){
                    b2body.setTransform(b2body.getPosition().x + steep,b2body.getPosition().y,0);
                    // Caso o inimigo tentar sair da tela
                    if (b2body.getPosition().x >= (Main.V_WIDTH/Main.PPM) - w ){
                        b2body.setTransform((Main.V_WIDTH/Main.PPM) - w,b2body.getPosition().y,0);
                    }
                }
                //----------------
                // -- Baixo --
                //----------------
                else if(nr == 9){
                    b2body.setTransform(b2body.getPosition().x,b2body.getPosition().y - steep,0);
                    // Caso o inimigo tentar sair do limite de baixo
                    if (b2body.getPosition().y <= LIMITER_BOTTON ){
                        b2body.setTransform(b2body.getPosition().x,LIMITER_BOTTON,0);
                    }
                    //System.out.println("b");
                }
                //----------------
                // -- Cima ------
                //----------------
                else if(nr == 10){
                    b2body.setTransform(b2body.getPosition().x,b2body.getPosition().y + steep,0);
                    // Caso o inimigo tentar sair do limite de baixo
                    if (b2body.getPosition().y >= LIMITER_TOP ){
                        b2body.setTransform(b2body.getPosition().x,LIMITER_TOP,0);
                    }
                    //System.out.println("c");
                }

                /// escolhe outro tempo para efetuar o tiro
                steepDelayTime = TimerCount.randomFloatNumber(FIRST_TIME_STEEP, SECOND_TIME_STEEP);
            }
		}
		// Destrundo o corpo do mundo
		else if (destring && !lastAnimation && !destroyed) {
			//world.destroyBody(b2body);
			lastAnimation = true;
		}
		// Fazendo a utima animacao
		else if (destring && lastAnimation && !destroyed) {
			destroyed = true;
			lifeBarOn = false;
			// Conta o tempo de amostra da imagem.
			timer += Gdx.graphics.getDeltaTime();
			// Tempo de amostra da imagem de impacto.
			if (timer >= 0.9) {
				destroyed = true;
			}
		}
	}
	/**
	 * Quando o inimigo e acertado
	 * */
	public void hit(float shoot_point) {
        //------------------------------------------
        // -- Caulcula diminui o dano do tiro com a resistencia
        //----------------------------------------------------
		life -= shoot_point - resistance;
        lifeBar.setLife(life);
        //System.out.println(this.life);
		if(life <= 0)
			dead();

	}
	/**
	 * Quando o inimigo e morto
	 * */
	private void dead(){
		this.mov = Movs.DEAD;
		destring = true;
	}

	/**
	 * Se for igual a false apagara o jogador da tela
	 *
	 * @param drawPlayer
	 */
	public void setDrawPlayer(boolean drawPlayer) {
		this.drawPlayer = drawPlayer;
	}

	/**
	 * Cria o corpo fisico do jogador
	 */
	protected void defineEnemy() {
		// Definindo a posicao do corpo
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, y);
		bdef.type = BodyType.DynamicBody;

		// criando o corpo
		this.b2body = world.createBody(bdef);

		// Definido o tamanho do corpo
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5f / Main.PPM, 5f / Main.PPM);
		fdef.shape = shape;
		// Filtro
		fdef.filter.maskBits = Main.SHOOT;
		fdef.filter.categoryBits = Main.ENEMY;

		// Adicionado tamanho ao corpo
		this.b2body.createFixture(fdef).setUserData(this);
		//System.out.println();

	}

	/**
	 * Mosta a PowerBar depois execulta o tiro
	 */
	public void shot() {
	}

	public Body getB2body() {
		// TODO Auto-generated method stub
		return b2body;
	}

	public float w() {
		// TODO Auto-generated method stub
		return w;
	}

	public float h() {
		// TODO Auto-generated method stub
		return h;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	/** Mostra o movimento atual do player */
	public Movs getMov() {
		return mov;
	}

	/** Configura o movimento do player */
	public void setMov(Movs mov) {
		this.mov = mov;
	}

    public float getLife() {
        return life;
    }


}
