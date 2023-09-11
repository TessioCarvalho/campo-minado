package br.com.campoMinado.model;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int COLUNA, LINHA;

	private boolean aberto, minado, marcado;

	List<Campo> vizinhos = new ArrayList<>();
	List<CampoObservador> observadores = new ArrayList<>();
	
	Campo(int x, int y) {
		LINHA = x;
		COLUNA = y;
	}
	
	public void addObservador(CampoObservador observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(CampoEvento evento) {
		observadores.stream()
		.forEach(o -> o.eventoOcorreu(this, evento));
	}
	
	boolean addVizinho(Campo vizinho) {
		boolean xDiferente = LINHA != vizinho.LINHA;
		boolean yDiferente = COLUNA != vizinho.COLUNA;
		boolean diagonal = xDiferente && yDiferente;

		int deltaLinha = Math.abs(LINHA - vizinho.LINHA);
		int deltaColuna = Math.abs(COLUNA - vizinho.COLUNA);
		int delta = deltaColuna + deltaLinha;

		if (delta == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (delta == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	public void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
			if(marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			}else {
				notificarObservadores(CampoEvento.DESMARCAR);
			}
		}
	}

	public boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	public boolean abrir() {
		if (!aberto && !marcado) {
			if (minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
			setAberto(true);
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;
		}
	}

	boolean minar() {
		if (!minado) {
			minado = true;
		}
		return minado;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}

	public int minasNaVizinhanca() {
		return (int)vizinhos.stream()
				.filter(v -> v.minado)
				.count();
	}

	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}

	public boolean isMarcado() {
		return marcado;
	}

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
		if(aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}

	public boolean isMinado() {
		return minado;
	}

	public int getCOLUNA() {
		return COLUNA;
	}

	public int getLINHA() {
		return LINHA;
	}

}
