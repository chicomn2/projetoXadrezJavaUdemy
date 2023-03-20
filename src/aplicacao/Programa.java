package aplicacao;

import java.util.Locale;
import java.util.Scanner;

import tabuleirodejogo.Posicao;
import tabuleirodejogo.Tabuleiro;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Locale.setDefault(Locale.US);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		while (true) {
			UI.imprimeTabluleiro(partidaXadrez.getpecas());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

			PecaXadrez pecaCapturada = partidaXadrez.efetuaMovimentoXadrez(origem, destino);

		}

	}

}
