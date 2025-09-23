package com.jtn.snowballfigth.characteres.hero;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.cenaries.Stage;
import com.jtn.snowballfigth.characteres.hero.special.Dragon;
import com.jtn.snowballfigth.characteres.hero.special.Mountain;
import com.jtn.snowballfigth.characteres.hero.special.SnowMan;
import com.jtn.snowballfigth.characteres.hero.special.Special;
import com.jtn.snowballfigth.itens.bullets.Bullet;
import com.jtn.snowballfigth.screens.PlayScreen;
import com.jtn.snowballfigth.tools.TimerCount;
import com.jtn.snowballfigth.util.Map;
import com.jtn.snowballfigth.util.PowerBar;
import com.jtn.snowballfigth.util.UI;

public class Player implements Hero{

	private SpriteBatch batch;
	private Animation<TextureRegion> player, attack, dead;
	private float w, h;
	private boolean drawPlayer;
	public Body b2body;
	private World world;
	private ArrayList<Bullet> bullets;
	private PlayScreen screen;
    private Stage stage;
	private PowerBar power;
	private boolean powerBarOn;
    private float life;

	private Movs mov;
	private float timer_walk,timer_attack;
    private UI ui;
    private Hero_Life heroLife;
    private boolean destring, lastAnimation;
    private float powerEnergy;

    /**
     *Bousa do player
     */
    private Bag bag;
    private boolean blinkOn;
    private TimerCount blinkDelay;
    private int timesBlink;
    private boolean imageOn;
    //-------------------------------------
    // --- Animacao de vitoria ------------
    //-------------------------------------
    private boolean victory;
    private Texture victoryTexture,vTexture;
    private TimerCount victoryCount;
    //Tamanhos e posicoes da mao fazendo "V"
    private float xv,yv,wv,hv;
    /// Indica fim do estagio com vitoria
    private boolean endAnimationVictory,end;

    public Player(PlayScreen screen) {

		world = screen.world;
		this.screen = screen;
        this.stage = (Stage)screen;
        this.ui = this.stage.getUi();

		this.batch = screen.batch;
		// Imagem do heroi
		Texture tex = new Texture("characteres/hero/hero.png");
		Array<TextureRegion> array = new Array<TextureRegion>();
		// Criando animacao
		for (int i = 0; i < 2; i++) {
			array.add(new TextureRegion(tex, i * tex.getWidth() / 2, 0, tex.getWidth() / 2, tex.getHeight()));
		}
		// Animacao
		player = new Animation<TextureRegion>(0.2f, array);
		array.clear();
		// attack
		tex = new Texture("characteres/hero/hero-attack.png");
		// Criando animacao
		for (int i = 0; i < 2; i++) {
			array.add(new TextureRegion(tex, i * tex.getWidth() / 2, 0, tex.getWidth() / 2, tex.getHeight()));
		}
		// Animacao
		attack = new Animation<TextureRegion>(0.1f, array);
        array.clear();
        //-------------------------------------------------------------
        // ---------------- Morte ------ Morte ------------------------
        // ------------------------------------------------------------
        tex = new Texture("characteres/hero/hero-lose.png");
        // Criando animacao
        array.add(new TextureRegion(tex,0, 0, tex.getWidth(), tex.getHeight()));

        // Animacao
        dead = new Animation<TextureRegion>(0.1f, array);
		this.w = 27f / Main.PPM;
		this.h = 27f / Main.PPM;

		drawPlayer = true;

		definePlayer();

		// Lista de balas
		bullets = new ArrayList<>();
		// Power bar
		power = new PowerBar(this);
		// Movimento
		mov = Movs.STOP;

        life = Hero_Life.getMAX_LIFE();

        // Verifica se o jogador ainda tem life
        destring = false;
        //Bousa de itens do player
        bag =  Bag.getContext(this);
        //Energia do poder do jogador
        powerEnergy = screen.getGame().getEnergyPower();
        //Efeitos de blink
        imageOn = true;
        blinkOn = false;
        blinkDelay = new TimerCount();
        timesBlink = 0;
        /// -----------------------------------------------------
        /// Texturas e configuracoes da comemoracao do jogado
        /// ---------------------------------------------------
        victoryTexture = new Texture("characteres/hero/hero-vic.png");
        vTexture = new Texture("characteres/hero/v.png");
        victoryCount = new TimerCount();
        /// Posicoes e tamanhos da mao fazendo v
        wv = (float) vTexture.getWidth() /Main.PPM ;
        hv = (float) vTexture.getHeight() /Main.PPM ;
        /// Indica o final da animacao de vitoria
        endAnimationVictory = false;
        end = false;
	}

	/**
	 * Desenha os graficos da jogador
	 */
	public void draw(float delta) {
        //Caso o jogador ainda não tenha ganhado
        if(!victory) {
            if (drawPlayer && !lastAnimation) {
                // Caso o player esteja em movimento a animacao e atualizada.
                // Caso parado a animacao para.
                if (imageOn) {
                    if (mov != Movs.ATTACK) {
                        if (mov == Movs.WALK) {
                            timer_walk += Gdx.graphics.getDeltaTime();
                        }
                        batch.draw(player.getKeyFrame(timer_walk, true), b2body.getPosition().x - w / 2,
                            b2body.getPosition().y - h / 2, w, h);
                    } else {
                        batch.draw(attack.getKeyFrame(timer_attack, true), b2body.getPosition().x - w / 2,
                            b2body.getPosition().y - h / 2, w, h);
                        timer_attack += delta;
                    }
                }
                /// ---------------------------------------------------------------------------
                /// --------------- Animação de morte do protagonista -------------------------
                /// ---------------------------------------------------------------------------
            } else if (drawPlayer && lastAnimation) {
                this.w = 27f / Main.PPM;
                this.h = 18f / Main.PPM;
                batch.draw(dead.getKeyFrame(0, true), b2body.getPosition().x - w / 2,
                    b2body.getPosition().y - h / 2, w, h);
                //timer_attack += delta;
            }
        }
        else {
            //Posicoes da mao fazendo "V"
            xv = b2body.getPosition().x + (w - 19f / Main.PPM);
            yv = b2body.getPosition().y - (h / 2) + (6f / Main.PPM);
            //Faz a mao em forma de "V" crescer
            float increaseHand = 0.001f;
            //Desenaha a comoracao do jogador
            batch.draw(victoryTexture, b2body.getPosition().x - w / 2, b2body.getPosition().y - h / 2, w, h);
            //Desenaha a mao fazendo v na comemoracao
            batch.draw(vTexture, xv, yv, wv, hv);
            //Atuliza o contador que faz a contagem para o fim da animacao
            victoryCount.update();
            //System.out.println(victoryCount.getTimer());
            if (!endAnimationVictory && victoryCount.getTimer() <= 1f) {
                wv += increaseHand;
                hv += increaseHand;
            }
            //Verifica se chegou no fim do tempo
            else if (victoryCount.getTimer() >= 1f && victoryCount.getTimer() <4f) {
                endAnimationVictory = true;

            }
            else if (victoryCount.getTimer() >= 4f){
                end = true;
                System.out.println("fim");
            }
        }
        for (Bullet bullet : bullets) {
            bullet.draw();
        }
        if (powerBarOn && !destring && !victory) {
            power.draw();
        }
	}
	/**
	 * Atuliza as infomacoes do Jogador e derivados
	 */
	public void update(float delta) {
        ///----------------------------------------------------------------
        /// ------------- Enquanto o jogador ainda estiver vivo -----------
        /// ---------------------------------------------------------------
        if(!destring){
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).update();
                if (bullets.get(i).isDestroyed()) {
                    bullets.remove(i);
                }
            }
        }
        else if (destring){
            lastAnimation = true;
            b2body.setLinearVelocity(0,0);
        }
        blink();
	}
	/**
	 * Se for igual a false apagara o jogador da tela
	 *
	 * @param drawPlayer
     *
     * */
	public void setDrawPlayer(boolean drawPlayer) {
		this.drawPlayer = drawPlayer;
	}
	/**
	 * Cria o corpo fisico do jogador
	 */
	protected void definePlayer() {
		// Definindo a posicao do corpo
		BodyDef bdef = new BodyDef();
		bdef.position.set(200f / Main.PPM, 50f/Main.PPM);
		bdef.type = BodyType.DynamicBody;

		// criando o corpo
		this.b2body = world.createBody(bdef);
        // Filtro
        // Definido o tamanho do corpo
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(7f / Main.PPM, 7f / Main.PPM);
		fdef.shape = shape;
        fdef.filter.categoryBits =  Main.PLAYER;
        fdef.filter.maskBits = Main.BULLET_ENEMY;
        // Adicionado tamanho ao corpo

        Fixture fixture = this.b2body.createFixture(fdef);
        fixture.setUserData(this);
    }
	/**
	 * Mosta a PowerBar depois execulta o tiro
	 */
	public void shoot() {
		// Mosta o Powerbar
		if (!powerBarOn) {
			powerBarOn = true;
			mov = Movs.ATTACK;

		} else {
			// faz o tiro e tira a Powerbar
			powerBarOn = false;
			bullets.add(new Bullet(this, screen,
                    //Cria ump parelelo entre ate onde o tiro pode ir e o numero de bolas na powerbar
					Map.map(power.getPower(), 0, 1.6f, ((Main.V_HEIGHT/Main.PPM)/2f) - 50f /Main.PPM, ((Main.V_HEIGHT / 2f) + 50f) / Main.PPM)));
			power.reset();
			mov = Movs.STOP;
		}
	}

	@Override
	public Body getB2body() {
		// TODO Auto-generated method stub
		return b2body;
	}
    /**
     * Retorna os pontos de vida do jogador
     * @return pontos de vida do jogador
     */
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

	public SpriteBatch getBatch() {
		return batch;
	}

    /**
     * Quando a bala do inimigo acerta o jogador
     */
    public void hit(float shoot){
        heroLife = stage.getUi().getHeroLife();

        life -= shoot;

        heroLife.setLife(life);
        blinkOn();

        if(life <= 0){
            destring = true;
        }

    }

    /** Mostra o movimento atual do player */
	public Movs getMov() {
		return mov;
	}

	/** Configura o movimento do player */
	public void setMov(Movs mov) {
		this.mov = mov;
	}

    @Override
    public void dispose() {
        for (Bullet b : bullets){
            b.dispose();
        }
    }

    public boolean isDestring() {
        return destring;
    }

    /**
     * @return Retorna os itens que o player tem
     */
    public Bag getBag() {
        return bag;
    }

    public void setLife(float life) {
        this.life = life;
    }

    public Hero_Life getHeroLife() {
        heroLife = stage.getUi().getHeroLife();
        return heroLife;
    }
    /**
     * Configura a energia do poder do player
     */
    public void setPowerEnergy(float pe) {
        screen.getGame().setEnergyPower(pe);
        this.powerEnergy = pe;
    }
    /**
     * Cria o especial de acordo com a energia do jogador
     */
    public Special special(){
        if (powerEnergy >= Special.SPECIAL1 && powerEnergy < Special.SPECIAL2 ){
            return new Mountain(this);
        }
        else if (powerEnergy >= Special.SPECIAL2 && powerEnergy < Special.SPECIAL3){
            return new  Dragon(this);
        }
        else if (powerEnergy >= Special.SPECIAL3){
            return new SnowMan(this);
        }
        return null;
    }
    public void blink(){
        ///  Configura o efeito de blink
        if (blinkOn && !destring){
            /// Atualiza o contador do delay do blink
            /// Caso o contador atinga o limite
            blinkDelay.update();
            //No inicio do efeito
            //O personagem desliga imediatamente
            if (timesBlink == 0) {
                timesBlink += 1;
                if (blinkDelay.getTimer() >= 0) {
                    /// se a imagem estiver ligada e desligada
                    /// se a imagem estiver desligada e ligada
                    imageOn = !imageOn;
                    //Reseta o contador
                    blinkDelay.resetTimer();
                    /// adiciona 1 ao contador de piscada
                    //System.out.println("blick " + imageOn);
                    //Caso o numero de piscadas for atingido o efeito acaba
                    if (timesBlink >= 4) {
                        blinkOn = false;
                        imageOn = true;
                        timesBlink = 0;
                        //System.out.println("fim");
                    }
                }
            }
            else {
                if (blinkDelay.getTimer() >= .2f) {
                    /// se a imagem estiver ligada e desligada
                    /// se a imagem estiver desligada e ligada
                    imageOn = !imageOn;
                    //Reseta o contador
                    blinkDelay.resetTimer();
                    /// adiciona 1 ao contador de piscadas
                    timesBlink += 1;
                    //System.out.println("blick " + imageOn);
                    //Caso o numero de piscadas for atingido o efeito acaba
                    if (timesBlink >= 4) {
                        blinkOn = false;
                        imageOn = true;
                        timesBlink = 0;
                    }
                }
            }
        }
    }

    /**
     * Liga o efeito de blink
     */
    public void blinkOn(){
        blinkOn = true;
    }
    /**
     * Liga a comeoracao do jogador
     */
    public void victoryOn(){
        victory = true;
        imageOn = true;
    }
    /**
     * Indica se pode finalizar o estagio
     */
    public boolean isEnd() {
        return end;
    }
}
