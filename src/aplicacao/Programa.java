package aplicacao;

import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;
import xadrez.PartidaXadrez;

public class Programa {

	public static void main(String[] args) {
	PartidaXadrez partidaXadrez = new PartidaXadrez();
	UI.imprimeTabluleiro(partidaXadrez.getpecas());

	}

}
