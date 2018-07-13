package br.com.edsondev.elevador.testes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.edsondev.elevador.classes.Elevador;

public class ElevadorTester {

    private static List<Elevador> elevadores = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int opcao, qtdeElevadores = 0;
        Scanner reader = new Scanner(System.in);
        
        System.out.print("Quantos elevadores existem no prédio? ");
        qtdeElevadores = reader.nextInt();
        for (int i = 0; i < qtdeElevadores; i++) {
        	System.out.println("Digite a quantidade de andares do elevador " + (i + 1));
        	int q = reader.nextInt();
        	Elevador elevador = new Elevador(q);
        	elevadores.add(elevador);
        }
        
        if(qtdeElevadores <= 0) {
        	System.out.println("Quantidade inválida!");
        }else {
        	do {
                System.out.println("Escolha uma das opções abaixo.");
                System.out.println(" 1 -> Mostrar estado do(s) elevador(es)");
                System.out.println(" 2 -> Escolher elevador. Quantidade (" + Elevador.QUANTIDADE_ELEVADORES() + ")");
                System.out.println("-1 -> Sair");

                opcao = reader.nextInt();

                switch (opcao) {
                    case 1:
                        for (int i = 0; i < elevadores.size(); i++) {
                            System.out.println("Elevador " + (i + 1));
                            System.out.println(elevadores.get(i).toString() + "\n");
                        }
                        System.out.print("\n\n");
                        break;
                    case 2:
                        System.out.println("\nTemos " + Elevador.QUANTIDADE_ELEVADORES() + " elevador(es).");
                        System.out.print("Qual elevador deseja escolher? ");

                        int eEscolha = reader.nextInt();
                        if(eEscolha > 0 && eEscolha <= Elevador.QUANTIDADE_ELEVADORES()){
                        	selecionarElevador(eEscolha);
                        }else{
                            System.out.println("Elevador não encontrado.\n\n");
                        }
                        break;
                    default:
                }
            } while (opcao != -1);
        }
        
        reader.close();
        System.out.println("Programa finalizado.");
    }

    private static void selecionarElevador(int eEscolha) {
        Scanner r = new Scanner(System.in);
        System.out.println("\nElevador escolhido -> " + eEscolha);
        String mensagem = elevadores.get(eEscolha -1).getAndarAtual() == 0 ? "térreo" : elevadores.get(eEscolha -1).getAndarAtual() + "° andar";
        
        System.out.println("Você está no " + mensagem);
        System.out.print("Escolha os andares separados por vírgula: ");
        String andaresString = "";
        andaresString = r.nextLine().replaceAll("\\s+", "");
        String[] a = andaresString.split("\\,"); // fatia a string onde tiver v�rgula

        List<Integer> andaresInt = Arrays.stream(a).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println("Andares selecionados -> " + andaresInt + "\n\n");

        Elevador e = elevadores.get(eEscolha - 1);
        andaresInt.stream().forEach(andar -> e.escolher(andar));
        if(e.getAndaresSelecionados().size() > 0) {
        	e.mover();
        }

        System.out.println("\n\n\n");
    }

}
