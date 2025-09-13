package com.jtn.snowballfigth.itens.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.enemys.Enemy;
import com.jtn.snowballfigth.characteres.hero.Player;
import com.jtn.snowballfigth.screens.PlayScreen;

import java.util.Vector;

public class Bullet implements Disposable {

	private SpriteBatch batch;
	private Texture bullet, pok,hitTex;
	private float w, h;
	public Body b2body;
	private World world;
	private Player playerObj;
	private Enemy enemyObj;
	// Destruido a bala
	private boolean destroyed, shoot, hit, end;
	private boolean destring, lastAnimation;
	private float stop;
	private float timer;
	private Fixture fixture;
	private float shoot_point;
    private float dx,dy;
    private Vector2 playerBody, enemyBody;
    private PlayScreen screen;

	public Bullet(Player player, PlayScreen screen, float stop) {
		this(screen, stop);
		playerObj = player;
		defineBulletPlayer();

        this.shoot_point = 3f;

	}

	public Bullet(Enemy enemy, PlayScreen screen, float stop, float shootPower) {
		this(screen, stop);
        playerBody = new Vector2(screen.getPlayer().getB2body().getPosition().x,screen.getPlayer().getB2body().getPosition().y);
        enemyBody = new Vector2(enemy.b2body.getPosition().x,enemy.b2body.getPosition().y);
		enemyObj = enemy;
		defineBulletEnemy();

        //------------------------------------------------------------------------------------------
        // ---------------- faz os valores das direções valer 1.5 ----------------------------------
        //  ---------- Verificando se e positivo ou negativo, para decidir a direcao da bala -------
        //------------------------------------------------------------------------------------------
        //this.dx = (this.dx);
        this.dx = playerBody.x- b2body.getPosition().x ;
        this.dy = playerBody.y- b2body.getPosition().y ;
        this.dy = this.dy < 0 ? -1.5f : 1.5f;
        this.dx = dx *1.5f;

        this.shoot_point = shootPower;
	}

	private Bullet(PlayScreen screen, float stop) {

		world = screen.world;
        this.screen = screen;

		this.batch = screen.batch;
		this.bullet = new Texture("bullets/snow_gauge.png");
		this.pok = new Texture("util/pok.png");
        this.hitTex = new Texture("util/h_bbang.png");

		this.w = 8f / Main.PPM;
		this.h = 8f / Main.PPM;

		// forca do lancamento
		this.stop = stop;
        //Variavel que define o fim definitivo da imagem

	}

	/**
	 * Desenha os graficos da bala
	 */
	public void draw() {
		if (!destring && b2body.getPosition().y >0)
			batch.draw(bullet, b2body.getPosition().x - w / 2, b2body.getPosition().y - h / 2, w, h);
		else if (lastAnimation && !destroyed && !hit && !end)
			batch.draw(pok, b2body.getPosition().x - w / 2, b2body.getPosition().y - h / 2, w, h);
        else if (lastAnimation && !destroyed && hit && !end) {
            playerBody = new Vector2(screen.getPlayer().getB2body().getPosition().x,screen.getPlayer().getB2body().getPosition().y);
            batch.draw(hitTex, playerBody.x, b2body.getPosition().y - h / 2, w, h);
        }
        //System.out.println("ate aqui");
	}

	/**
	 * Atuliza as infomacoes do Jogador e derivados
	 */
	public void update() {

		if (!shoot && !destring) {
            if (playerObj != null)
			    b2body.setLinearVelocity(0, 1.5f);
            else {
                b2body.setLinearVelocity(dx, dy);
            }

            //-----------------------------------------------------------------
            // -----------------Verifica se o tiro saio da tela ---------------
            //------------------------------------------------------------------
            if (b2body.getPosition().y >= (float) Main.V_HEIGHT / Main.PPM ) {
                System.out.println("sdf");
                shoot = false;
                destring = true;
                lastAnimation = false;
                destroyed = false;
            }
            // -----------------------------------------------------------
            // ------- Verifica se o projetio chegou no ponto final ------
            // ------ Definido pelo medidor e para o projetio ------------
            //------------------------------------------------------------
			if (b2body.getPosition().y >= stop && playerObj != null) {
				b2body.setLinearVelocity(0,0);
				shoot = true;
			}
		}
		//Verifica se acertou algum personagem
		else if (shoot && !destring && !destroyed && !lastAnimation) {
			// liga o filtro
			setCategoryFilter(Main.SHOOT);
			// Conta o tempo de amostra da imagem.
			timer += Gdx.graphics.getDeltaTime();
			// Tempo de amostra da imagem de impacto.
			if (timer >= 0.1) {
				destring = true;
				timer = 0;
			}
		}
		// ---------------------------------------------------------------------------
        // -- Indica que é para ir para a utima animação antes de apagar o objeto ----
        // ------- Quando é o jogador que lansou e aucancou o ponto final ------------
        // ---------------------------------------------------------------------------
		else if (shoot && destring && !lastAnimation && !destroyed) {
			lastAnimation = true;
            setCategoryFilter(Main.BULLET);
		}
        // --------------------------
        // -- Apaga o corpo do mundo. ---
        // Quando sai da tela
        //-----------------------------
        else if (!shoot && destring && !lastAnimation && !destroyed) {
            //world.destroyBody(b2body);
            lastAnimation = true;
        }
        else if (shoot && destring && lastAnimation && !destroyed) {
			// Conta o tempo de amostra da imagem.
			timer += Gdx.graphics.getDeltaTime();
			// Tempo de amostra da imagem de impacto.
			if (timer >= 0.9) {
				destroyed = true;
                world.destroyBody(b2body);
			}

		}
	}

	/**
	 * Cria o corpo fisico da bala do Jogador
	 */
	protected void defineBulletPlayer() {
		// Definindo a posicao do corpo
		BodyDef bdef = new BodyDef();
		bdef.position.set(playerObj.b2body.getPosition().x, playerObj.b2body.getPosition().y + playerObj.h());
		bdef.type = BodyType.DynamicBody;

		// criando o corpo
		this.b2body = world.createBody(bdef);

		// Definido o tamanho do corpo
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(2f / Main.PPM, 2f / Main.PPM);
		fdef.shape = shape;

		// Filtro
		fdef.filter.categoryBits =  Main.BULLET;
		fdef.filter.maskBits = Main.ENEMY|Main.PLAYER;
		// massa
		MassData mass = new MassData();
		mass.mass = 0.00001f;

		b2body.setMassData(mass);

		// Adicionado tamanho ao corpo
		fixture = this.b2body.createFixture(fdef);
		fixture.setUserData(this);

	}

    /**
     * Cria o corpo fisico da bala do Enimigo
     */
    protected void defineBulletEnemy() {
        //--------------------------------------------------------------------------
        // ---------------- Caucular a direcao do jogador em relacao oa inimigo ----
        //  ------------ para o inimigo saber em que direcao atirar o projetio ----
        //-------------------------------------------------------------------------
        BodyDef bdef = new BodyDef();
        bdef.position.set(enemyObj.b2body.getPosition().x, enemyObj.b2body.getPosition().y );
        bdef.type = BodyType.DynamicBody;

        // criando o corpo
        this.b2body = world.createBody(bdef);

        // Definido o tamanho do corpo
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5f / Main.PPM, 5f / Main.PPM);
        fdef.shape = shape;

        // Filtro
        fdef.filter.categoryBits =  Main.BULLET_ENEMY;
        fdef.filter.maskBits = Main.PLAYER;
        // massa
        MassData mass = new MassData();
        mass.mass = 0.00001f;

        b2body.setMassData(mass);

        // Adicionado tamanho ao corpo
        fixture = this.b2body.createFixture(fdef);
        fixture.setUserData(this);
    }

	public Body getB2body() {
		// TODO Auto-generated method stub
		return b2body;
	}

	public float w() {
		// TODO Auto-generated method stub
		return w;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	private void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;

		fixture.setFilterData(filter);
	}
	/**
	 * Retorna o valor do dano do tiro
	 * */
	public float getShoot_point() {
        //shoot = true;
        //destring = false;
        //destroyed =false;
        //lastAnimation =false;
		return shoot_point;
	}

    /**
     * Retorna o valor do dano do tiro
     * */
    public float shootPlayer() {
        shoot = true;
        destring = false;
        destroyed =false;
        lastAnimation =false;

        //Faz a bala parar
        this.b2body.setLinearVelocity(new Vector2(0,0));
        //Avisa que a bala acertou o jogador
        hit = true;

        return shoot_point;
    }


    @Override
    public void dispose() {
        bullet.dispose();
        pok.dispose();
    }
}
