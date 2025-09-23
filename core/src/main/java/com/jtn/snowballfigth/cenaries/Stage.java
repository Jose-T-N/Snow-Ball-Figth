package com.jtn.snowballfigth.cenaries;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.characteres.enemys.Enemy;
import com.jtn.snowballfigth.characteres.hero.Player;
import com.jtn.snowballfigth.characteres.hero.special.Mountain;
import com.jtn.snowballfigth.characteres.hero.special.Special;
import com.jtn.snowballfigth.util.UI;

public class Stage extends Scenario {

	protected Texture writeBg;
	protected ArrayList<Enemy> enemys;
    protected UI ui;
    protected Special special;
    private boolean end;

	public Stage(Main main) {
		super(new Texture("scenario/back1.png"), main, false);
		writeBg = new Texture("scenario/write.png");
        ui = new UI(this);

		enemys = new ArrayList<>();
        special = null;

		for(int i = 0; i< 4; i++) {
			enemys.add(new Enemy(this,100*i+10,120));
		}

        //Define que o estagio chegou ao final
        end = false;

	}

	@Override
	public void update(float delta) {

        if (!end)
            /// Quando o jogo estiver em especial
            /// O jogo paraliza ate o final do especial
            if(special != null){
                special.update();
                //Caso o specia chege ao fim o objeto e excluido;
                if (special.isFin()){
                    //Liga o efeito de blink de todos os inimigos
                    for (Enemy e: enemys){
                        //Causa dano em todos os inimigos
                        e.hit(special.getDamage());
                    }
                    special = null;
                }
            }
            //Caso o estagio não esteja em modo especial
            else {
                //Atualiza o jogador e seus objetos
                player.update(delta);
                //Atualiza o inimigo e seus objetos
                for(Enemy enemy: enemys) {
                    enemy.update(delta);
                }
                //Define o fim quando a vida do jogador chega a zero
                if (player.getLife() <= 0){
                    end = true;
                    player.getB2body().setLinearVelocity(0,0);
                }
                else if (allEnemiesIsDead()){
                    end = true;
                    player.getB2body().setLinearVelocity(0,0);
                    ((Player)(player)).victoryOn();
                }

            }
        else {
            player.update(delta);
            if (((Player)player).isEnd()){
                end();
            }
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

        if(special != null){
            special.draw();
        }

	}

	@Override
	public void handleInput() {
        Player p = (Player) player;

        /// Verifica se o player esta morto
        /// Ou verifica se o jogo esta em modo especial
        if ((!p.isDestring()) && special == null && !end) {
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
            // Shoot
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z))
                ((Player) player).shoot();
            // Mudar item selecionado
            if (Gdx.input.isKeyJustPressed(Input.Keys.C))
                ui.getItensHero().changeItem();
            // Usar item
            if (Gdx.input.isKeyJustPressed(Input.Keys.V))
                ui.getItensHero().useItem();
            // Manda o special
            if (Gdx.input.isKeyJustPressed(Input.Keys.X)){
                special = ((Player)player).special();
            }
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
    /**
     * Verifica se todos os inimigos estão mortos
     */
    private boolean allEnemiesIsDead(){
        for (Enemy e: enemys){
            //Caso achar algum inimigo vivo
            //E retornado false
            if (!e.isDestroyed()){
                return false;
            }
        }
        //Caso nenhum inimigo for encontrado vivo
        //retorna true
        return true;
    }
    /***
     * Termina o estagio e retorna a vila
     */
    private void end(){
        this.game.setScenario(game.getVillage());
        this.game.setScreen(this.game.getScenario());
    }
}
