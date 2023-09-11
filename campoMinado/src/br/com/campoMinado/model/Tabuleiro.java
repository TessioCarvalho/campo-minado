package br.com.campoMinado.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador{
	
	private final int LINHAS, COLUNAS, MINAS;
	
	private final List<Campo> CAMPOS = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> OBSERVADORES = 
			new ArrayList<>();

	public Tabuleiro(int linha, int coluna, int mina) {
		super();
		this.LINHAS = linha;
		this.COLUNAS = coluna;
		this.MINAS = mina;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void paraCadaCampo(Consumer<Campo> funcao) {
		CAMPOS.forEach(funcao);
	}
	
	public void addObservador(Consumer<ResultadoEvento> observador) {
		OBSERVADORES.add(observador);
	}
	
	private void notificarObservadores(boolean resultado) {
		OBSERVADORES.stream()
		.forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}
	
	public void abrir(int linha, int coluna) {
		CAMPOS.parallelStream()
		.filter(c -> c.getLINHA() == linha && c.getCOLUNA() == coluna)
		.findFirst()
		.ifPresent(c -> c.abrir());
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		CAMPOS.parallelStream()
		.filter(c -> c.getLINHA() == linha && c.getCOLUNA() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}

	private void gerarCampos() {
		for (int linha = 0; linha < LINHAS; linha++) {
			for (int coluna = 0; coluna < COLUNAS; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.addObservador(this);
				CAMPOS.add(campo);
			}
		}
	}
	
	private void associarVizinhos() {
		for(Campo c1: CAMPOS) {
			for(Campo c2: CAMPOS) {
				c1.addVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas=0;
		Predicate<Campo>minado = c -> c.isMinado();
		
		do {
			int aleatorio = (int)(Math.random() * CAMPOS.size());
			CAMPOS.get(aleatorio).minar();
			minasArmadas = CAMPOS.stream().filter(minado).count();
		} while(minasArmadas < MINAS);
		
	}
	
	public boolean objetivoAlcancado() {
		return CAMPOS.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		CAMPOS.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		if(evento == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservadores(false);
		}else if(objetivoAlcancado()) {
			notificarObservadores(true);
		}
	}
	
	private void mostrarMinas() {
		CAMPOS.stream()
			.filter(c -> c.isMinado())
			.forEach(c -> c.setAberto(true));
	}

	public int getLINHAS() {
		return LINHAS;
	}

	public int getCOLUNAS() {
		return COLUNAS;
	}

	public int getMINAS() {
		return MINAS;
	}

	public List<Campo> getCAMPOS() {
		return CAMPOS;
	}

	public List<Consumer<ResultadoEvento>> getOBSERVADORES() {
		return OBSERVADORES;
	}
	
}
