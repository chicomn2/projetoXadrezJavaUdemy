package xadrez.pecas;

import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Diagonal direita superior
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
			p.setColuna(p.getColuna() + 1);
		}

		if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Diagonal direita inferior
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
			p.setColuna(p.getColuna() + 1);
		}

		if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Diagonal esquerda superior
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
			p.setColuna(p.getColuna() - 1);
		}

		if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Diagonal esquerda inferior
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
			p.setColuna(p.getColuna() - 1);
		}

		if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

}
