package xadrez.pecas;

import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;

	}

	@Override
	public String toString() {
		return "P";
	}

	/*
	 * private boolean podeMover(Posicao posicao) { PecaXadrez p = (PecaXadrez)
	 * getTabuleiro().peca(posicao); return p == null || p.getCor() != getCor(); }
	 */
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCAS) {

			// acima 1 linha
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// acima 2 linhas
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(p.getLinha() - 2, p.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p) && getTabuleiro().posicaoExiste(p2)
					&& !getTabuleiro().temPecaAi(p2) && getContadorDeMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// Diagonal direita superior
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// Diagonal esquerda superior
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			// Movimento especial "En Passant BRANCAS"
			// Diagonal esquerda
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && temPecaDoOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez.getVuneravelEnPassant()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
					;
				}
			}

			// Diagonal direita
			if (posicao.getLinha() == 3) {
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && temPecaDoOponente(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez.getVuneravelEnPassant()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
					;
				}
			}

		} else {
			// PEÇAS PRETAS
			// abaixo 1 linha
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPecaAi(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// abaixo 2 linhas
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(p.getLinha() + 2, p.getColuna());
			if (getTabuleiro().posicaoExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temPecaAi(p2)
					&& getContadorDeMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// Diagonal direita inferior
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// Diagonal esquerda inferior
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaDoOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// Movimento especial "En Passant PRETAS"
			// Diagonal esquerda
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && temPecaDoOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez.getVuneravelEnPassant()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
					;
				}
			}

			// Diagonal direita
			if (posicao.getLinha() == 4) {
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && temPecaDoOponente(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez.getVuneravelEnPassant()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
					;
				}
			}

		}

		return mat;
	}

}
