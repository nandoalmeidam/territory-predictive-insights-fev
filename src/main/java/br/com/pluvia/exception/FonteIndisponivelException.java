package br.com.pluvia.exception;

// Exceção personalizada para sensores indisponíveis.
public class FonteIndisponivelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// Construtor que recebe uma mensagem de erro.
	public FonteIndisponivelException(String mensagem) {
		super(mensagem);
	}
}
