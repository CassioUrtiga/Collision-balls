package colisao;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class Colisao extends JPanel implements Runnable{

    private final int quantBolas, velocidade, tamanho;
    private final ArrayList listax, listay, eixoX, eixoY, movimentos;
    private final Random rand;
    
    public Colisao(){
        
        quantBolas = 2;
        velocidade = 2;
        tamanho = 15;
        
        rand = new Random();
        listax = new ArrayList();
        listay = new ArrayList();
        movimentos = new ArrayList();
        eixoX = new ArrayList();
        eixoY = new ArrayList();
        
        carregarArrays();
        
        Thread gameLoop = new Thread(this);
        gameLoop.start();
    }
    
    private void carregarArrays(){
        for (int i=0; i<quantBolas; i++){
            listax.add(rand.nextInt(200)+20);
            listay.add(200);
            
            movimentos.add(i, -1);
            
            eixoX.add(i, 0);
            eixoY.add(i, 0);
        }
    }
    
    private void bolaColideTela(int i){
        //bola colide com a tela cima
        if ((int)listay.get(i) < 0){
            int vetor[] = {-1,3}; //possíveis direções
            listay.set(i, 0);
            movimentos.set(i, vetor[rand.nextInt(2)]);
            if (rand.nextBoolean()){//direção da diagonal
                eixoX.set(i, true);
                eixoY.set(i, true);
            }else{
                eixoX.set(i, false);
                eixoY.set(i, true);
            }
        }
        
        //bola colide com a tela baixo
        if ((int)listay.get(i) > 552){
            int[] vetor = {1,3};
            listay.set(i, 552);
            movimentos.set(i, vetor[rand.nextInt(2)]);
            if (rand.nextBoolean()){
                eixoX.set(i, true);
                eixoY.set(i, false);
            }else{
                eixoX.set(i, false);
                eixoY.set(i, false);
            }
        }
        
        //bola colide com a tela direita
        if ((int)listax.get(i) > 563){
            int[] vetor = {-2,3};
            listax.set(i, 563);
            movimentos.set(i, vetor[rand.nextInt(2)]);
            if (rand.nextBoolean()){
                eixoX.set(i, false);
                eixoY.set(i, false);
            }else{
                eixoX.set(i, false);
                eixoY.set(i, true);
            }
        }
        
        //bola colide com a tela esquerda
        if ((int)listax.get(i) < 0){
            int[] vetor = {2,3};
            listax.set(i, 0);
            movimentos.set(i, vetor[rand.nextInt(2)]);
            if (rand.nextBoolean()){
                eixoX.set(i, true);
                eixoY.set(i, true);
            }else{
                eixoX.set(i, true);
                eixoY.set(i, false);
            }
        }
    }
    
    private void movimento(int i){
        switch ((int)movimentos.get(i)){
            case -1: // baixo
                listay.set(i, (int)listay.get(i)+velocidade);
                break;
            case 1: // cima
                listay.set(i, (int)listay.get(i)-velocidade);
                break;
            case 2: //Direita
                listax.set(i, (int)listax.get(i)+velocidade);
                break;
            case -2: //Esquerda
                listax.set(i, (int)listax.get(i)-velocidade);
                break;
            case 3: //Diagonal
                if ((boolean)eixoX.get(i)){
                    listax.set(i, (int)listax.get(i)+velocidade);
                }else{
                    listax.set(i, (int)listax.get(i)-velocidade);
                }
                
                if ((boolean)eixoY.get(i)){
                    listay.set(i, (int)listay.get(i)+velocidade);
                }else{
                    listay.set(i, (int)listay.get(i)-velocidade);
                }
                break;
            default:
                break;
        }
    }
    
    private void fps(){
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            System.out.println("FPS ERRO");
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);
        
        for (int i=0; i<quantBolas; i++){
            bolaColideTela(i);
            movimento(i);
            
            g.setColor(Color.RED);
            g.fillOval((int)listax.get(i),(int)listay.get(i),tamanho,tamanho);
        }
    }
    
    @Override
    public void run() {
        while (true){
            repaint();
            fps();
        }
    }
}