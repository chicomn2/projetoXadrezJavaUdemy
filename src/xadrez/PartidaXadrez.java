package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	private boolean cheque;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCAS;
		setupInicial();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheque() {
		return cheque;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] tab = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				tab[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return tab;

	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}

	public PecaXadrez efetuaMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		if(testeCheque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ExcecaoXadrez("Você se colocou em cheque, refaça o movimento");
		}
		cheque = (testeCheque(oponente(jogadorAtual)))? true: false;
		proximoTurno();
		return (PecaXadrez) pecaCapturada;
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		return pecaCapturada;

	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, origem);
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temPecaAi(posicao)) {
			throw new ExcecaoXadrez("Não existe peça na posição origem.");
		}

		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecaoXadrez("Esta é uma peça adversária.");
		}

		if (!tabuleiro.peca(posicao).haMovimentoPossivel()) {
			throw new ExcecaoXadrez("Não há movimentos possíveis para a peça origem.");
		}
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new ExcecaoXadrez("A peça escolhida não pode ser movida para a posição de destino");
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCAS) ? Cor.PRETAS : Cor.BRANCAS;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCAS) ? Cor.PRETAS : Cor.BRANCAS;
	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe rei " + cor + " no tabuleiro");
	}
	
	private boolean testeCheque(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for(Peca p: pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private void colocaPecaNova(Character coluna, Integer linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);

	}

	private void setupInicial() {
		colocaPecaNova('a', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocaPecaNova('h', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocaPecaNova('a', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocaPecaNova('h', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocaPecaNova('b', 1, new Cavalo(tabuleiro, Cor.BRANCAS));
		colocaPecaNova('g', 1, new Cavalo(tabuleiro, Cor.BRANCAS));
		colocaPecaNova('b', 8, new Cavalo(tabuleiro, Cor.PRETAS));
		colocaPecaNova('g', 8, new Cavalo(tabuleiro, Cor.PRETAS));
		colocaPecaNova('c', 1, new Bispo(tabuleiro, Cor.BRANCAS));
		colocaPecaNova('f', 1, new Bispo(tabuleiro, Cor.BRANCAS));
		colocaPecaNova('c', 8, new Bispo(tabuleiro, Cor.PRETAS));
		colocaPecaNova('f', 8, new Bispo(tabuleiro, Cor.PRETAS));
		colocaPecaNova('d', 1, new Dama(tabuleiro, Cor.BRANCAS));
		colocaPecaNova('d', 8, new Dama(tabuleiro, Cor.PRETAS));
		colocaPecaNova('e', 1, new Rei(tabuleiro, Cor.BRANCAS));
		colocaPecaNova('e', 8, new Rei(tabuleiro, Cor.PRETAS));
		/*
		 * for(char c = 'a';c<='h';c++) { colocaPecaNova(c,2 , new Peao(tabuleiro,
		 * Cor.BRANCAS)); } for(char c = 'a';c<='h';c++) { colocaPecaNova(c,7 , new
		 * Peao(tabuleiro, Cor.PRETAS)); }
		 */

	}

}
