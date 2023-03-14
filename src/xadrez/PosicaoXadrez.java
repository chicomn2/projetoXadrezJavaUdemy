package xadrez;

import tabuleirodejogo.Posicao;

public class PosicaoXadrez {
	private Character coluna;
	private Integer linha;
	public PosicaoXadrez(Character coluna, Integer linha) {
		if(coluna<'a'||coluna>'h'||linha<1||linha>8) {
			throw new ExcecaoXadrez("Erro instanciando a posição no tabuleiro, valores válidos são de a1 até h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}
	public Character getColuna() {
		return coluna;
	}
	
	public Integer getLinha() {
		return linha;
	}
	
	protected Posicao paraPosicao() {
		return new Posicao(8-linha, coluna-'a'); 
	}
	
	protected static PosicaoXadrez daPosicao (Posicao posicao)	{
		return new PosicaoXadrez((char)('a'-posicao.getColuna()), 8-posicao.getLinha());
	}
	@Override
	public String toString() {
		return ""+linha+coluna;
	}
	

}
