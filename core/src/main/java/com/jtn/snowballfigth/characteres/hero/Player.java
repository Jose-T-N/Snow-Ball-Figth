package com.jtn.snowballfigth.characteres.hero;

import java.util.ArrayList;

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
import com.jtn.snowballfigth.itens.bullets.Bullet;
import com.jtn.snowballfigth.screens.PlayScreen;
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
    private boolean destring, lastAnimation;;

	public Player(PlayScreen screen) {

		world = screen.world;
		this.screen = screen;
        this.stage = (Stage)screen;

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

	}

	/**
	 * Desenha os graficos da jogador
	 */
	public void draw(float delta) {
		if (drawPlayer && !lastAnimation) {
			// Caso o player esteja em movimento a animacao e atualizada.
			// Caso parado a animacao para.
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
            //----------------------------------------------------------------------------
            // --------------- Animação de morte do protagonista -------------------------
            //----------------------------------------------------------------------------
		}else if(drawPlayer && lastAnimation){
            this.w = 27f / Main.PPM;
            this.h = 18f / Main.PPM;
            batch.draw(dead.getKeyFrame(0, true), b2body.getPosition().x - w / 2,
                b2body.getPosition().y - h / 2, w, h);
            //timer_attack += delta;
        }
		for (Bullet bullet : bullets) {
			bullet.draw();
		}
		if (powerBarOn && !destring) {
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
        if(!destring) {
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
	public void shot() {
		// Mosta o Powerbar
		if (!powerBarOn) {
			powerBarOn = true;
			mov = Movs.ATTACK;

		} else {
			// faz o tiro e tira a Powerbar
			powerBarOn = false;
			bullets.add(new Bullet(this, screen,
                    //Cria ump parelelo entre ate onde o tiro pode ir e o numero de bolas na powerbar
					Map.map(power.getPower(), 0, 1.6f, ((Main.V_HEIGHT/Main.PPM)/2f) - 30f /Main.PPM, ((Main.V_HEIGHT / 2f) + 50f) / Main.PPM)));
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
}
