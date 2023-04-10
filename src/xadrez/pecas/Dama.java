package xadrez.pecas;

import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Dama extends PecaXadrez {

	public Dama(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "D";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}

		if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// abaixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}

		if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}

		if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}

		if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Diagonal direita inferior
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() + 1,p.getColuna() + 1);
				}

				if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// Diagonal direita superior
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() - 1,p.getColuna() + 1);
				}

				if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// Diagonal esquerda inferior
				p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() + 1,p.getColuna() - 1);
				}

				if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// Diagonal esquerda superior
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() - 1,p.getColuna() - 1);
				}

				if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

		return mat;
	}

}
