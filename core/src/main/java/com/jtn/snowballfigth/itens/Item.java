package com.jtn.snowballfigth.itens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jtn.snowballfigth.cenaries.stores.Store;
import com.jtn.snowballfigth.characteres.hero.Player;
import com.jtn.snowballfigth.characteres.hero.PlayerIcon;

public abstract class Item {

	private String name;
	private int price;
	protected Store store;
    protected TextureRegion img;
    protected boolean used;

	public Item(String name, int price,Store store,String img) {
		super();

        Texture t = new Texture(img);
        this.img = new TextureRegion(t,t.getWidth(), t.getHeight());
		this.store = store;
		this.name = name;
		this.price = price;
        this.used = false;
	}

	public void sale(PlayerIcon player) {
		store.getTimer().resetTimer();
		//Verifica se ha dinheiro suficiente para a compra
		if(player.getManey()-price >= 0) {
			//Verifica se o item e duplicado
			if(player.addItem(this)) {
				//Deduz a compra do dinheiro do jogadore
				player.setManey(player.getManey()-price);
				store.setMsg3(true);

			}else {
				store.setMsg2(true);
			}

		}
		else {
			store.setMsg1(true);
		}
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

    protected abstract void effect(Player player);

    public TextureRegion getImg() {
        return img;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    /**
     * Aplica o efeito do item
     * @param player jogador
     */
    public void useItem(Player player){
        //Verifica se o item ja foi usado
        if (!used)
            effect(player);
    }
}
