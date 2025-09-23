package com.jtn.snowballfigth.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jtn.snowballfigth.Main;
import com.jtn.snowballfigth.cenaries.Stage;
import com.jtn.snowballfigth.characteres.hero.Bag;
import com.jtn.snowballfigth.characteres.hero.Player;
import com.jtn.snowballfigth.itens.*;
import com.jtn.snowballfigth.tools.TimerCount;

public class Itens_Hero {
    /**
     * Mochila com os itens do player
     */
    private Bag bag;
    /**
     * Item selecionado
     * Item 1,2,3 e 4
     */
    private Item selected;
    /**
     * Lista com as imagens dos itens
     */
    private SpriteBatch batch;

    private int itemSelected;

    private float w,h;

    private ShapeRenderer shape;

    private TimerCount blink;

    private Player player;

    public Itens_Hero(Stage stage) {
        this.bag = ((Player)(stage.getPlayer())).getBag();
        this.batch = stage.getBatch();
        this.shape = stage.getShape();
        this.player = (Player) stage.getPlayer();

        shape.setColor(Color.GREEN);

        itemSelected = 0;

        //Percorre os itens do usuario
        //e verifica se quais itens ele tem
        if(!Bag.getContext().getItens().isEmpty()) {
            selected = Bag.getContext().getItens().get(0);
            itemSelected = 1;
        }


        w = 10f/ Main.PPM;
        h = 15f/ Main.PPM;

        blink = new TimerCount();

    }

    public void update(){
        if (itemSelected == 1){
            selected = Bag.getContext().getItens().get(0);
        }
        else if (itemSelected == 2){
            selected = Bag.getContext().getItens().get(1);
        }
        else if (itemSelected == 3){
            selected = Bag.getContext().getItens().get(2);
        }
        else if (itemSelected == 4){
            selected = Bag.getContext().getItens().get(3);
        }
        else if (itemSelected == 5){
            selected = Bag.getContext().getItens().get(4);
        }
    }

    public void draw(){
        if (itemSelected != 0){
            batch.draw(selected.getImg(),(Main.V_WIDTH/Main.PPM) - 25f/Main.PPM,15f/Main.PPM,h,w);
        }
        //Para não dar eror verifica o tamanho da lista
        if(!Bag.getContext().listIsEmpty()) {
            if (!Bag.getContext().getItens().get(0).isUsed()) {
                batch.draw(Bag.getContext().getItens().get(0).getImg(), 120f / Main.PPM, 15f / Main.PPM, h, w);
            }
            //Para não dar error verifica o tamanho da lista
            if (Bag.getContext().getItens().size() >= 2)
                if (!Bag.getContext().getItens().get(1).isUsed()) {
                    batch.draw(Bag.getContext().getItens().get(1).getImg(), 158f / Main.PPM, 15f / Main.PPM, h, w);
                }
            //Para não dar error verifica o tamanho da lista
            if (Bag.getContext().getItens().size() >= 3)
                if (!Bag.getContext().getItens().get(2).isUsed()) {
                    batch.draw(Bag.getContext().getItens().get(2).getImg(), 196f / Main.PPM, 15f / Main.PPM, h, w);
                }
            //Para não dar error verifica o tamanho da lista
            if (Bag.getContext().getItens().size() >= 4)
                if (!Bag.getContext().getItens().get(3).isUsed()) {
                    batch.draw(Bag.getContext().getItens().get(3).getImg(), 232f / Main.PPM, 15f / Main.PPM, h, w);
                }
            //Para não dar error verifica o tamanho da lista
            if (Bag.getContext().getItens().size() >= 5)
                if (!Bag.getContext().getItens().get(4).isUsed()) {
                    batch.draw(Bag.getContext().getItens().get(4).getImg(), 270f / Main.PPM, 15f / Main.PPM, h, w);
                }
        }
        //shapeDraw();
    }

    /**
     * Muda o item selecionado
     */
    public void changeItem(){
        //Caso não haja itens a posição sempre sera 0
        do {
            if (itemSelected < Bag.getContext().getItens().size())
                itemSelected += 1;
            else if (Bag.getContext().listIsEmpty()){
                itemSelected = 0;
                break;
            }
            else
                itemSelected = 1;
        } while (Bag.getContext().getItens().get(itemSelected-1).isUsed());
    }

    boolean isBlink = true;
    public void shapeDraw() {
        //Faz um efeito o seletor ficar piscando
        if (blink.getTimer() >=.5f){
            if (isBlink){
                shape.setColor(Color.GREEN);
                isBlink = false;
            }
            else {
                shape.setColor(0,0,0,0);
                isBlink = true;
            }
            blink.resetTimer();
        }
        // Item 1

        float ws = 24f / Main.PPM,
            hs = 13f / Main.PPM;

        if (itemSelected == 1 || itemSelected == 0)
            shape.rect(116f / Main.PPM, 14f / Main.PPM, ws, hs);
        // Item 2
        if (itemSelected == 2)
            shape.rect(154f / Main.PPM, 14f / Main.PPM, ws, hs);
        // Item 3
        if (itemSelected == 3)
            shape.rect(192f / Main.PPM, 14f / Main.PPM, ws, hs);
        // Item 4
        if (itemSelected == 4)
            shape.rect(229f / Main.PPM, 14f / Main.PPM, ws, hs);
        if (itemSelected == 5)
            shape.rect(266f / Main.PPM, 14f / Main.PPM, ws, hs);

        blink.update();

    }

    public void useItem() {
        if (selected != null && itemSelected != 0) {
            //Aplica o efeito do item
            selected.useItem(player);
            Bag.getContext().getItens().get(itemSelected - 1).setUsed(true);
            //Altomaticamnte ve-se onde esta o proximo item não vasio
            do {
                if (itemSelected < Bag.getContext().getItens().size())
                    itemSelected += 1;
                else if (Bag.getContext().listIsEmpty()){
                    itemSelected = 0;
                    break;
                }
                else
                    itemSelected = 1;


            } while (Bag.getContext().getItens().get(itemSelected-1).isUsed());
        }
    }
}
