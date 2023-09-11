package br.com.campoMinado.view;

import javax.swing.JFrame;

import br.com.campoMinado.model.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
	
	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16,30,50);
		add(new PainelTabuleiro(tabuleiro));
		
		setTitle("  **TÉSSIO**  "+
		"Projeto Campo-Minado do Curso de Java. "
				+ "  **TÉSSIO**  ");
		setSize(1024, 720);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new TelaPrincipal();
	}

}
