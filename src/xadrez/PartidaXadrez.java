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
	private boolean chequeMate;
	private PecaXadrez vulneravelEnPassant;
	private PecaXadrez promovida;
	

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

	public boolean getChequeMate() {
		return chequeMate;
	}

	public PecaXadrez getVuneravelEnPassant() {
		return vulneravelEnPassant;
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
	
	public PecaXadrez getPromovida() {
		return promovida;
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
		if (testeCheque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ExcecaoXadrez("Você se colocou em cheque, refaça o movimento");
		}
		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);
		
		// Movimento especial "Promoção"
		promovida = null;
		if(pecaMovida instanceof Peao) {
			if((pecaMovida.getCor()== Cor.BRANCAS && destino.getLinha()==0)||(pecaMovida.getCor()== Cor.PRETAS && destino.getLinha()==7)) {
				promovida = (PecaXadrez)tabuleiro.peca(destino);
				promovida = substituirPecaPromovida("D");
			}
		}
		

		cheque = (testeCheque(oponente(jogadorAtual))) ? true : false;
		if (testeChequeMate(oponente(jogadorAtual))) {
			chequeMate = true;
		} else {
			proximoTurno();
		}

		// Movimento especial "En Passant"
		if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2)
				|| (destino.getLinha() == origem.getLinha() + 2)) {
			vulneravelEnPassant = pecaMovida;
		} else {
			vulneravelEnPassant = null;
		}
		return (PecaXadrez) pecaCapturada;
	}
	
	public PecaXadrez substituirPecaPromovida(String tipo) {
		if(promovida==null) {
			throw new IllegalStateException("Não há peça a ser promovida");
		}
		if(!tipo.equals("B")&&!tipo.equals("C")&&!tipo.equals("T")&&!tipo.equals("D")) {
			return promovida;
		}
		
		Posicao pos = promovida.getPosicaoXadrez().paraPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez pecaNova = pecaNova(tipo, promovida.getCor());
		tabuleiro.colocarPeca(pecaNova, pos);
		pecasNoTabuleiro.add(pecaNova);
		
		return pecaNova;
		
		
		
	}
	
	private PecaXadrez pecaNova(String tipo, Cor cor) {
		if(tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if(tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if(tipo.equals("T")) return new Torre(tabuleiro, cor);
		return new Dama(tabuleiro, cor);
		
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(origem);
		p.amuentaContadorDeMovimentos();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// Movimento especial "Roque Pequeno"
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.amuentaContadorDeMovimentos();
		}

		// Movimento especial "Roque Grande"
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.amuentaContadorDeMovimentos();
		}

		// Movimento especial "En Passant"
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCAS) {
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;

	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(destino);
		p.diminuiContadorDeMovimentos();
		tabuleiro.colocarPeca(p, origem);
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		// Movimento especial "Roque Pequeno"
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.diminuiContadorDeMovimentos();
		}

		// Movimento especial "Roque Grande"
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.diminuiContadorDeMovimentos();
		}

		// Movimento especial "En Passant"
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == vulneravelEnPassant) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removePeca(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCAS) {
					posicaoPeao = new Posicao(3, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				
				tabuleiro.colocarPeca(peao, posicaoPeao);
			}
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
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
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
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeChequeMate(Cor cor) {
		if (!testeCheque(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testeCheque = testeCheque(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeCheque) {
							return false;
						}
					}
				}
			}

		}
		return true;
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
		colocaPecaNova('e', 1, new Rei(tabuleiro, Cor.BRANCAS, this));
		colocaPecaNova('e', 8, new Rei(tabuleiro, Cor.PRETAS, this));
		for (char c = 'a'; c <= 'h'; c++) {
			colocaPecaNova(c, 2, new Peao(tabuleiro, Cor.BRANCAS, this));
		}
		for (char c = 'a'; c <= 'h'; c++) {
			colocaPecaNova(c, 7, new Peao(tabuleiro, Cor.PRETAS, this));
		}

	}

}
