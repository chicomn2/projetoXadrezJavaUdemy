package xadrez;

import tabuleirodejogo.Tabuleiro;

public class PartidaXadrez {
	private Tabuleiro tabuleiro;
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro (8,8);
	}
	
	public PecaXadrez [][] getpecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0;i<tabuleiro.getLinhas();i++) {
			for (int j=0;i<tabuleiro.getColunas();i++) {
				mat[i][j]= (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
		
	}

}
