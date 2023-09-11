package br.com.campoMinado.model;

public class ResultadoEvento {
	
	private final boolean GANHOU;

	public ResultadoEvento(boolean ganhou) {
		this.GANHOU = ganhou;
	}

	public boolean isGanhou() {
		return GANHOU;
	}

}
