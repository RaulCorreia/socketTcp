package modelo;

public class Produto {
	
	private String codigo;
	private String nome;
	private String tipo;
	private String preco;
	
	public Produto(String codigo, String nome, String tipo, String preco) {
		this.codigo = codigo;
		this.nome = nome;
		this.tipo = tipo;
		this.preco = preco;
	}
	
	public Produto() {
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}
	
//	public String toString() {
//		String objeto = this.codigo + ";" + this.nome + ";" + this.tipo + ";" + this.preco;
//		return objeto;
//	}
	
}
