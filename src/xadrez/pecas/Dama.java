package xadrez.pecas;

import tabuleirodejogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Dama extends PecaXadrez{

	public Dama(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
			}
	
	@Override
	public String toString() {
		return "D";
	}

}
