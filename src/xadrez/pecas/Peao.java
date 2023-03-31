package xadrez.pecas;

import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}
	
	@Override
	public String toString() {
		return "P";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			}
		
		// Diagonal direita superior
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					}
				
				
				// Diagonal esquerda superior
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

		return mat;
	}

}
