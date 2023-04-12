package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import tabuleirodejogo.Posicao;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static void limaTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		try {
			String s = sc.next();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);

		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo posição no tabuleiro. Posições válidas são entre a1 e h8");
		}
	}
	
	public static void imprimePartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturadas) {
		imprimeTabluleiro(partidaXadrez.getPecas());
		System.out.println();
		imprimePecasCapturadas(capturadas);
		System.out.println();
		System.out.println("Turno: "+partidaXadrez.getTurno());
		if(!partidaXadrez.getChequeMate()) {
		System.out.println("Esperando jogador: "+partidaXadrez.getJogadorAtual());			
		if(partidaXadrez.getCheque()) {
			System.out.println("Você está em Cheque!");
		}
	} else{
		System.out.println("Cheque mate!");
		System.out.println(partidaXadrez.getJogadorAtual()+" venceram a partida!");
	}
		
	}

	public static void imprimeTabluleiro(PecaXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimePeca(pecas[i][j], false);

			}
			System.out.println();

		}
		System.out.println("  A B C D E F G H");
	}
	
	public static void imprimeTabluleiro(PecaXadrez[][] pecas, boolean [][] movimentosPossiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimePeca(pecas[i][j], movimentosPossiveis[i][j]);

			}
			System.out.println();

		}
		System.out.println("  A B C D E F G H");
	}


	private static void imprimePeca(PecaXadrez peca, boolean background) {
		if (background) {
			System.out.print(ANSI_CYAN_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("- " + ANSI_RESET);
		} else {
			if (peca.getCor() == Cor.BRANCAS) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
			System.out.print(" ");
		}

	}
	
	private static void imprimePecasCapturadas(List<PecaXadrez>capturadas) {
		List<PecaXadrez> brancas = capturadas.stream().filter(x -> x.getCor() == Cor.BRANCAS).collect(Collectors.toList());
		List<PecaXadrez> pretas = capturadas.stream().filter(x -> x.getCor() == Cor.PRETAS).collect(Collectors.toList());
		System.out.println("Peças capturadas:");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(brancas.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Pretas: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(pretas.toArray()));
		System.out.print(ANSI_RESET);
		
	}

}
