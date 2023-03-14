package aplicacao;

import java.util.Locale;

import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;
import xadrez.PartidaXadrez;

public class Programa {

	public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	PartidaXadrez partidaXadrez = new PartidaXadrez();
	UI.imprimeTabluleiro(partidaXadrez.getpecas());

	}

}
