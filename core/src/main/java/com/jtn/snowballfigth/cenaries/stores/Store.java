package com.jtn.snowballfigth.cenaries.stores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.cenaries.Scenario;
import com.jtn.snowballfigth.cenaries.Village;
import com.jtn.snowballfigth.characteres.hero.PlayerIcon;
import com.jtn.snowballfigth.itens.Item;
import com.jtn.snowballfigth.itens.Lollipop;
import com.jtn.snowballfigth.itens.Portion;
import com.jtn.snowballfigth.itens.PowerfulAdhesive;
import com.jtn.snowballfigth.itens.StickingPlaster;
import com.jtn.snowballfigth.tools.TimerCount;

public abstract class Store extends Scenario {

	// Botoes Vertical
	protected boolean verticalButtons[];
	// Botoes Horizontal
	protected boolean horizontalButtons[];
	// Conta a posi��o dos botoes vertical
	protected int verticalCount = 0;
	// Conta a posicao dos botoes horizontal
	protected int HorizontalCount = 0;
	// Vila
	protected Village village;
	// Lista de produtos
	protected Item list[];
	// Verifica se pode mostrar menssagens
	protected boolean msg1, msg2, msg3;
	// Cronometro
	protected TimerCount timer;

	public Store(Texture img, Main main, Village village) {
		super(img, main,true);
		// Botoes
		this.verticalButtons = new boolean[2];
		this.horizontalButtons = new boolean[4];

		this.verticalButtons[0] = true;
		this.horizontalButtons[0] = true;

		this.village = village;
		// Preco e nome dos produtos
		list = new Item[4];

		// jogador
		super.player = village.getPlayer();

		list[0] = new StickingPlaster(this);
		list[1] = new Lollipop(this);
		list[2] = new PowerfulAdhesive(this);
		list[3] = new Portion(this);
		// Cronometro
		timer = new TimerCount();
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void draw(float delta) {
		// Cenario
		batch.draw(scenario, 50f / Main.PPM, 0, width - 100f / Main.PPM, height);
	}

	@Override
	public void fontDraw(float delta) {
        // Preço do item selecionado
        font.draw(batch, String.valueOf(list[HorizontalCount].getPrice()), 105f, 19f);
		// Texto com a quantidade de dinheiro do jogador
		font.draw(batch, String.valueOf(((PlayerIcon) player).getManey()), 245f, 19f);
		// Menssagem de "Dinheiro Insuficiente"
		if (msg1) {
			msg2 = false;
			msg3 = false;
			timer.update();
			font.draw(batch, "Dinheiro Insuficiente", Main.V_WIDTH / 4 - 15, Main.V_HEIGHT / 2);
			if (timer.getTimer() >= 1f) {
				msg1 = false;
				timer.resetTimer();
			}
		}
		// Menssagem de "Item Duplicado"
		if (msg2) {
			msg1 = false;
			msg3 = false;
			timer.update();
			font.draw(batch, "Item Duplicado", Main.V_WIDTH / 4 - 15, Main.V_HEIGHT / 2);
			if (timer.getTimer() >= 1f) {
				msg2 = false;
				timer.resetTimer();
			}
		}
		// Menssagem de "Item Duplicado"
		if (msg3) {
			msg1 = false;
			msg2 = false;
			timer.update();
			font.draw(batch, "Item Adquirido", Main.V_WIDTH / 4 - 15, Main.V_HEIGHT / 2);
			if (timer.getTimer() >= 1f) {
				msg3 = false;
				timer.resetTimer();
			}
		}

	}

    /**
     * Desenha o quadrado do item selecionado
     * @param delta delta time
     */

	@Override
	public void shapeDraw(float delta) {
		shape.setColor(Color.GREEN);
		// Comprar
		if (verticalButtons[0])
			shape.rect(68f / Main.PPM, 152f / Main.PPM, 100f / Main.PPM, 20f / Main.PPM);
		// Sair
		if (verticalButtons[1])
			shape.rect(68f / Main.PPM, 121f / Main.PPM, 100f / Main.PPM, 20f / Main.PPM);
		shape.setColor(Color.YELLOW);
		// Item 1
		if (horizontalButtons[0])
			shape.rect(82f / Main.PPM, 40f / Main.PPM, 56f / Main.PPM, 40f / Main.PPM);
		// Item 2
		if (horizontalButtons[1])
			shape.rect(142f / Main.PPM, 40f / Main.PPM, 56f / Main.PPM, 40f / Main.PPM);
		// Item 3
		if (horizontalButtons[2])
			shape.rect(202f / Main.PPM, 40f / Main.PPM, 56f / Main.PPM, 40f / Main.PPM);
		// Item 4
		if (horizontalButtons[3])
			shape.rect(262f / Main.PPM, 40f / Main.PPM, 56f / Main.PPM, 40f / Main.PPM);

	}

	@Override
	public void handleInput() {

		int previousPositionVertical;
		int previousPositionHorizontal;

		// But�o para cima
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			// Desativa o bot�o na posi��o anterior
			previousPositionVertical = verticalCount;
			verticalButtons[previousPositionVertical] = false;

			verticalCount += 1;

		}
		// But�o para baixo
		else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			// Desativa o bot�o na posi��o anterior
			previousPositionVertical = verticalCount;
			verticalButtons[previousPositionVertical] = false;
			// Atualiza a posi��o do contador
			verticalCount -= 1;

		}
		// But�o para esquerda
		else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			// Desativa o bot�o na posi��o anterior
			previousPositionHorizontal = HorizontalCount;
			horizontalButtons[previousPositionHorizontal] = false;
			// Atualiza a posi��o do contador
			HorizontalCount -= 1;
		}
		// Butao para direita
		else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			// Desativa o bot�o na posi��o anterior
			previousPositionHorizontal = HorizontalCount;
			horizontalButtons[previousPositionHorizontal] = false;
			// Atualiza a posi��o do contador
			HorizontalCount += 1;

		}
		// Butao Z
		else if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
			if (verticalButtons[0]) {
				// Venda
				list[HorizontalCount].sale(((PlayerIcon) player));
				return;
			}
			if (verticalButtons[1]) {
				// Resetar as configura��oes
				// Vertical
				verticalButtons[verticalCount] = false;
				verticalCount = 0;
				// Horizontal
				horizontalButtons[HorizontalCount] = false;
				HorizontalCount = 0;

				if (this.getClass() == Drugstore.class)
					// Sai da farmacia
					village.drugstore();
				else if (this.getClass() == GunStore.class)
					// Sai da farmacia
					village.gunStore();

				return;
			}
		}

		// Caso o contador for menor que 0
		// muda pra 1
		if (verticalCount < 0) {
			verticalCount = 1;
		}
		// Caso o contador for maior que 1
		// muda pra 0
		else if (verticalCount > 1) {
			verticalCount = 0;
		}
		// Caso o contador for menor que 0
		// muda pra 3
		if (HorizontalCount < 0) {
			HorizontalCount = 3;
		}
		// Caso o contador for maior que 3
		// muda pra 0
		else if (HorizontalCount > 3) {
			HorizontalCount = 0;
		}
		// Ativa o botao na posicao do contador
		horizontalButtons[HorizontalCount] = true;
		verticalButtons[verticalCount] = true;

	}

	/**
	 * menssagem de "Dinheiro Insuficiente"
	 *
	 * @param msg
	 *            valor boolean
	 */
	public void setMsg1(boolean msg) {
		this.msg1 = msg;
	}

	public boolean isMsg1() {
		return msg1;
	}

	/**
	 * menssagem de "Item Duplicado"
	 *
	 * @param msg
	 *            valor boolean
	 */
	public void setMsg2(boolean msg) {
		this.msg2 = msg;
	}

	public boolean isMsg2() {
		return msg2;
	}

	/**
	 * menssagem de "Item Duplicado"
	 *
	 * @param msg
	 *            valor boolean
	 */
	public void setMsg3(boolean msg) {
		this.msg3 = msg;
	}

	public boolean isMsg3() {
		return msg3;
	}

	/**
	 * Retorna o objeto do cronometro
	 */
	public TimerCount getTimer() {
		return timer;
	}
}
