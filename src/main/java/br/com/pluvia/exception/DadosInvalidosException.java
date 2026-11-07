package br.com.pluvia.exception;

// Exceção personalizada para dados inválidos.
public class DadosInvalidosException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// Construtor que recebe a descrição do problema.
	public DadosInvalidosException(String mensagem) {
		super(mensagem);
	}
}
