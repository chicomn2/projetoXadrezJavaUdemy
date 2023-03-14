package xadrez;

import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Dama;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	private Tabuleiro tabuleiro;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		setupInicial();
	}

	public PecaXadrez[][] getpecas() {
		PecaXadrez[][] tab = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				tab[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return tab;

	}

	private void setupInicial() {

		tabuleiro.colocarPeca(new Torre(tabuleiro, Cor.PRETA), new Posicao(0, 0));
		tabuleiro.colocarPeca(new Cavalo(tabuleiro, Cor.PRETA), new Posicao(0, 1));
		tabuleiro.colocarPeca(new Bispo(tabuleiro, Cor.PRETA), new Posicao(0, 2));
		tabuleiro.colocarPeca(new Dama(tabuleiro, Cor.PRETA), new Posicao(0, 3));
		tabuleiro.colocarPeca(new Rei(tabuleiro, Cor.PRETA), new Posicao(0, 4));
		tabuleiro.colocarPeca(new Bispo(tabuleiro, Cor.PRETA), new Posicao(0, 5));
		tabuleiro.colocarPeca(new Cavalo(tabuleiro, Cor.PRETA), new Posicao(0, 6));
		tabuleiro.colocarPeca(new Torre(tabuleiro, Cor.PRETA), new Posicao(0, 7));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.PRETA), new Posicao(1, 0));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.PRETA), new Posicao(1, 1));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.PRETA), new Posicao(1, 2));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.PRETA), new Posicao(1, 3));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.PRETA), new Posicao(1, 4));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.PRETA), new Posicao(1, 5));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.PRETA), new Posicao(1, 6));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.PRETA), new Posicao(1, 7));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.BRANCA), new Posicao(6, 0));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.BRANCA), new Posicao(6, 1));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.BRANCA), new Posicao(6, 2));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.BRANCA), new Posicao(6, 3));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.BRANCA), new Posicao(6, 4));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.BRANCA), new Posicao(6, 5));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.BRANCA), new Posicao(6, 6));
		tabuleiro.colocarPeca(new Peao(tabuleiro, Cor.BRANCA), new Posicao(6, 7));
		tabuleiro.colocarPeca(new Torre(tabuleiro, Cor.BRANCA), new Posicao(7, 0));
		tabuleiro.colocarPeca(new Cavalo(tabuleiro, Cor.BRANCA), new Posicao(7, 1));
		tabuleiro.colocarPeca(new Bispo(tabuleiro, Cor.BRANCA), new Posicao(7, 2));
		tabuleiro.colocarPeca(new Dama(tabuleiro, Cor.BRANCA), new Posicao(7, 3));
		tabuleiro.colocarPeca(new Rei(tabuleiro, Cor.BRANCA), new Posicao(7, 4));
		tabuleiro.colocarPeca(new Bispo(tabuleiro, Cor.BRANCA), new Posicao(7, 5));
		tabuleiro.colocarPeca(new Cavalo(tabuleiro, Cor.BRANCA), new Posicao(7, 6));
		tabuleiro.colocarPeca(new Torre(tabuleiro, Cor.BRANCA), new Posicao(7, 7));

	}

}
