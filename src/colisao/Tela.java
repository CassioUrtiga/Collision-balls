package colisao;

import javax.swing.JFrame;

public class Tela {

    public static void main(String[] args) {
        JFrame tela = new JFrame("Teste de Colisão");
        
        Colisao c = new Colisao();
        
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setSize(600, 600);
        tela.setLocationRelativeTo(null);
        tela.setResizable(false);

        tela.add(c);
        tela.setVisible(true);
    }
    
}
