package br.com.campoMinado.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.campoMinado.model.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{
	
	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(tabuleiro.getLINHAS(),
				tabuleiro.getCOLUNAS()));
		
		tabuleiro.paraCadaCampo(c -> add(new CampoBotao(c)));
		tabuleiro.addObservador(e -> {
			
			SwingUtilities.invokeLater(() -> {
				if (e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "VITÓRIA!!!");
				} else {
					JOptionPane.showMessageDialog(this, "DERROTA!!!");
				}
				tabuleiro.reiniciar();
			});
		});
		
	}

}
