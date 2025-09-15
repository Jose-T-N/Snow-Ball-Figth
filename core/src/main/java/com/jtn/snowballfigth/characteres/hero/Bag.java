package com.jtn.snowballfigth.characteres.hero;

import com.jtn.snowballfigth.itens.*;

import java.util.ArrayList;

public class Bag {

    private Hero player;
    private ArrayList<Item> itens;
    private static Bag context;

    private Bag(Hero player) {
        this.player = player;
        this.itens = new ArrayList<>();
        //this.itens.add();
    }

    public static Bag getContext(Hero player) {
        if (context == null) {
            context = new Bag(player);
        }
        return context;
    }

    public static Bag getContext() {
        return context;
    }

    public ArrayList<Item> getItens() {
        return itens;
    }
    public boolean listIsEmpty(){
        for (int i = 0; i < itens.size(); i++){
            if (!itens.get(i).isUsed()){
                return false;
            }
        }
        return true;
    }
}
