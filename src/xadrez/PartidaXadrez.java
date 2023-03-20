package xadrez;

import tabuleirodejogo.Peca;
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
	
	public PecaXadrez efetuaMovimentoXadrez (PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino){
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		return (PecaXadrez) pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return pecaCapturada;
		
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if(!tabuleiro.temPecaAi(posicao)) {
			throw new ExcecaoXadrez("Não existe peça na posição origem");
		}
	}
		
	
		
	
	private void colocaPecaNova(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		
	}

	private void setupInicial() {		
		colocaPecaNova('a', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocaPecaNova('h', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocaPecaNova('a', 8, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('h', 8, new Torre(tabuleiro, Cor.PRETA));
		colocaPecaNova('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		colocaPecaNova('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		colocaPecaNova('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
		colocaPecaNova('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
		colocaPecaNova('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
		colocaPecaNova('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
		colocaPecaNova('c', 8, new Bispo(tabuleiro, Cor.PRETA));
		colocaPecaNova('f', 8, new Bispo(tabuleiro, Cor.PRETA));
		colocaPecaNova('d', 1, new Dama(tabuleiro, Cor.BRANCA));
		colocaPecaNova('d', 8, new Dama(tabuleiro, Cor.PRETA));
		colocaPecaNova('e', 1, new Rei(tabuleiro, Cor.BRANCA));
		colocaPecaNova('e', 8, new Rei(tabuleiro, Cor.PRETA));
		for(char c = 'a';c<='h';c++) {
			colocaPecaNova(c,2 , new Peao(tabuleiro, Cor.BRANCA));
			}
		for(char c = 'a';c<='h';c++) {
			colocaPecaNova(c,7 , new Peao(tabuleiro, Cor.PRETA));
			}


	}

}
