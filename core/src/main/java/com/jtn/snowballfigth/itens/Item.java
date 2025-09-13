package com.jtn.snowballfigth.itens;

import com.jtn.snowballfigth.cenaries.stores.Store;
import com.jtn.snowballfigth.characteres.hero.PlayerIcon;

public class Item {
	
	private String name;
	private int price;
	protected Store store;
	
	public Item(String name, int price,Store store) {
		super();
		
		this.store = store;
		this.name = name;
		this.price = price;
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
	
}
