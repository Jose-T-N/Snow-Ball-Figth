package com.jtn.snowballfigth.cenaries;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.enemys.Enemy;
import com.jtn.snowballfigth.characteres.hero.Player;
import com.jtn.snowballfigth.util.UI;

public class Stage extends Scenario {

	protected Texture writeBg;
	protected ArrayList<Enemy> enemys;
    protected UI ui;

	public Stage(Main main) {
		super(new Texture("scenario/back1.png"), main, false);
		writeBg = new Texture("scenario/write.png");
        ui = new UI(this);

		enemys = new ArrayList<>();

		for(int i = 0; i< 4; i++) {
			enemys.add(new Enemy(this,100*i+10,120));
		}

	}

	@Override
	public void update(float delta) {
		player.update(delta);

		for(Enemy enemy: enemys) {
			enemy.update(delta);
		}

        ui.update();

	}

	@Override
	public void draw(float delta) {
		// Desenha o fundo do estagio
		batch.draw(writeBg, 0, 0, width, (height));
        batch.draw(scenario, 0, (height / 2 ) + 35f /Main.PPM  , width, height / 3f);
        ui.draw();

		for(Enemy enemy: enemys) {
			enemy.draw(delta);
		}

		player.draw(delta);

	}

	@Override
	public void handleInput() {
        Player p = (Player) player;

        if (!p.isDestring()) {
            final float velocity = 1f;
            // Tecla para direita
            // Caso a posio do corpo seja maior que o tamanho da tela menos a largura do
            // jogador
            // o jogador no se move.
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                && player.getB2body().getPosition().x <= (Main.V_WIDTH - ((player.w() / 2) * Main.PPM)) / Main.PPM) {
                player.getB2body().setLinearVelocity(velocity, 0);
                if (player.getMov() != Player.Movs.ATTACK) {
                    player.setMov(Player.Movs.WALK);
                }
            }
            // Tecla para esquerda
            // Caso a posio do corpo seja menor que a largura do jogador divido por 2
            // o jogador no se move.
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getB2body().getPosition().x > player.w() / 2) {
                player.getB2body().setLinearVelocity(-velocity, 0);
                if (player.getMov() != Player.Movs.ATTACK) {
                    player.setMov(Player.Movs.WALK);
                }
            }
            // nenhuma tecla
            else {
                player.getB2body().setLinearVelocity(0, 0);
                if (player.getMov() != Player.Movs.ATTACK) {
                    player.setMov(Player.Movs.STOP);
                }
            }
            // shot
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z))
                ((Player) player).shot();
            // mudar item selecionado
            if (Gdx.input.isKeyJustPressed(Input.Keys.C))
                ui.getItensHero().changeItem();
            // Usar item
            if (Gdx.input.isKeyJustPressed(Input.Keys.V))
                ui.getItensHero().useItem();
        }

	}

    @Override
    public void dispose() {
        player.dispose();
        for (Enemy e : enemys){
            e.dispose();
        }
    }

    @Override
    public void shapeDraw(float delta) {
        ui.shapeDraw();
    }

    public UI getUi() {
        return ui;
    }
}
