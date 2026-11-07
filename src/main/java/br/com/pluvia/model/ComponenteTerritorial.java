package br.com.pluvia.model;

// Classe abstrata que representa um componente do território.
public abstract class ComponenteTerritorial {

	// Atributos encapsulados.
	private Long id;
	private String nome;
	private String regiao;
	private String status;

	// Construtor.
	public ComponenteTerritorial(Long id, String nome, String regiao) {
		this.id = id;
		this.nome = nome;
		this.regiao = regiao;
		this.status = "NORMAL";
	}

	// Método abstrato: cada classe filha deverá implementá-lo.
	public abstract void verificarStatus();

	// Getters.
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getRegiao() {
		return regiao;
	}

	public String getStatus() {
		return status;
	}

	// Setter protegido: permite que classes filhas atualizem o status.
	protected void setStatus(String status) {
		this.status = status;
	}
}
