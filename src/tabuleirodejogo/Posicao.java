package tabuleirodejogo;

public class Posicao {
	private Integer linha;
	private Integer coluna;

	public Posicao(Integer linha, Integer coluna) {
		this.linha = linha;
		this.coluna = coluna;

	}

	public Integer getRow() {
		return linha;
	}

	public void setRow(Integer linha) {
		this.linha = linha;
	}

	public Integer getColumn() {
		return coluna;
	}

	public void setColumn(Integer coluna) {
		this.coluna = coluna;
	}

	@Override
	public String toString() {
		return linha + ", " + coluna;
	}
}
