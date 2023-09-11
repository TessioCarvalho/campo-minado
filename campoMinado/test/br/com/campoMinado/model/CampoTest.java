package br.com.campoMinado.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CampoTest {
	
	private Campo campo;
	
	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3,3);
	}
	@Test
	void testeVizinhoDistancia1() {
		Campo vizinho = new Campo(3,4);
		boolean resultado = campo.addVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDistancia2() {
		Campo vizinho = new Campo(2,2);
		boolean resultado = campo.addVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1,1);
		boolean resultado = campo.addVizinho(vizinho);
		assertFalse(resultado);
	}
	@Test
	void alternarMarcacaoVariasVezes() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	@Test
	void alternarMarcacaoTrue() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	@Test
	void alternarMarcacaoFalse() {
		assertFalse(campo.isMarcado());
	}
	@Test
	void abrir() {
		campo.abrir();
		assertTrue(campo.isAberto());
	}
	@Test
	void naoAbrirMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.isAberto());
	}
	@Test
	void naoAbrirAberto() {
		campo.alternarMarcacao();
		campo.abrir();
		assertFalse(campo.isAberto());
	}
	@Test
	void minado() {
		campo.minar();
		assertTrue(campo.isMinado());
	}
	@Test
	void naoMinado() {
		assertFalse(campo.isMinado());
	}

}
