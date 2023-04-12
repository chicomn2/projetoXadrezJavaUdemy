package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import xadrez.ExcecaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Locale.setDefault(Locale.US);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>(); 
		while (!partidaXadrez.getChequeMate()) {
			try {
				UI.limaTela();
				UI.imprimePartida(partidaXadrez, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean [][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.limaTela();
				UI.imprimeTabluleiro(partidaXadrez.getPecas(), movimentosPossiveis);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

				PecaXadrez pecaCapturada = partidaXadrez.efetuaMovimentoXadrez(origem, destino);
				if(pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
				
				if(partidaXadrez.getPromovida()!= null) {
					System.out.print("Para qual peça o peão será promovido (B/C/T/D): ");
					String tipo = sc.next().toUpperCase();
					while(!tipo.equals("B")&&!tipo.equals("C")&&!tipo.equals("T")&&!tipo.equals("D")) {
						System.out.println("Tipo inválido!");
						System.out.print("Para qual peça o peão será promovido (B/C/T/D): ");
						tipo = sc.next().toUpperCase();
					}
					partidaXadrez.substituirPecaPromovida(tipo);
				}
			} 
			catch (ExcecaoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}

			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();

			}

		}
		
		UI.limaTela();
		UI.imprimePartida(partidaXadrez, capturadas);

	}
}
