package br.com.edsondev.elevador.classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.edsondev.elevador.enums.Sentido;

/**
 *
 * @author Edson
 */
public class Elevador {

    private static int QUANTIDADE_ELEVADORES = 0;
    private int qtdeAndares;
    private int andarAtual;
    private Sentido sentido;
    private List<Integer> andaresSelecionados;
    private List<Integer> andaresChamados;

    public Elevador(int qtdeAndares) {
        this.qtdeAndares = qtdeAndares;
        this.andaresSelecionados = new ArrayList<>();
        this.andaresChamados = new ArrayList<>();
        this.andarAtual = 0;
        this.sentido = Sentido.PARADO;
        
        QUANTIDADE_ELEVADORES += 1;
    }

    public void escolher(int andar) {
        if (andar > qtdeAndares || andar < 0) {
        	System.out.println("Andar " + andar + " não está disponível neste elevador.");
        }else if(andar == andarAtual) {
        	System.out.println("Andar " + andar + " não será adicionado, pois você já se encontra nele.");
        } else {
            if (!andaresSelecionados.contains(andar)) {
                andaresSelecionados.add(andar);
            }
        }
    }

    public void mover() {
    	// Quais os andares acima do atual
    	List<Integer> andaresAcima = andaresSelecionados.stream().filter(a -> a > andarAtual).collect(Collectors.toList());
    	// Quais os andares abaixo do atual
    	List<Integer> andaresAbaixo = andaresSelecionados.stream().filter(a -> a < andarAtual).collect(Collectors.toList());
    	int menorAbaixo, maiorAcima;
    	menorAbaixo = maiorAcima = andarAtual;
    	if(andaresSelecionados.size() > 1) {
    		if(andaresAbaixo.size() > 0) {
    			menorAbaixo = andaresAbaixo.stream().min(Comparator.comparing(Integer::valueOf)).get();
    		}
    		if(andaresAcima.size() > 0) {
    			maiorAcima = andaresAcima.stream().max(Comparator.comparing(Integer::valueOf)).get();
    		}
    	}
    	
    	int intervaloAcima = maiorAcima - andarAtual;
    	int intervaloAbaixo = andarAtual - menorAbaixo;
    	
//    	System.out.println("andares acima -> " + qtdeAndaresAcima + ", andares abaixo -> " + qtdeAndaresAbaixo );
    	
    	// se a quantidade de andares a se percorrer acima do andar atual for menor 
    	// que quantidade de andares a se percorrer abaixo do andar atual
        if(andaresSelecionados.size() > 1) {
        	sentido = intervaloAcima < intervaloAbaixo ? Sentido.SUBINDO : Sentido.DESCENDO;
        }else {
        	sentido = andaresAcima.size() > 0 ? Sentido.SUBINDO : Sentido.DESCENDO;
        }
        run();
    }

    private void run() {
        //Pegando o andar mais alto
        int max = andaresSelecionados.stream().max(Comparator.comparing(Integer::valueOf)).get();
        int min = andaresSelecionados.stream().min(Comparator.comparing(Integer::valueOf)).get();

        while (!andaresSelecionados.isEmpty()) {
            if (sentido.equals(Sentido.SUBINDO)) {
                for (int i = andarAtual; i <= max; i++) {
                    if (andaresSelecionados.contains(i) || andaresChamados.contains(i)) {
                        andarAtual = i;
                        exibirMensagem(i);
                        removerPiso(i);
                    } else {
                        System.out.println("Andar atual -> " + i + ", " + sentido.getValor());
                    }
                }
                if(andaresSelecionados.size() > 0) {
                	sentido = Sentido.DESCENDO;
                }
            } else if (sentido.equals(Sentido.DESCENDO)) {
                for (int i = andarAtual; i >= min; i--) {
                    if (andaresSelecionados.contains(i) || andaresChamados.contains(i)) {
                        andarAtual = i;
                        exibirMensagem(i);
                        removerPiso(i);
                    } else {
                        System.out.println("Andar atual -> " + i + ", " + sentido.getValor());
                    }
                }
                if(andaresSelecionados.size() > 0) {
                	sentido = Sentido.SUBINDO;
                }
            }
        }
        sentido = Sentido.PARADO;
    }

    private void exibirMensagem(int numeroDoAndar) {
		System.out.println("Andar atual -> " + numeroDoAndar + ", abrindo a porta...");
    }
    
    private void removerPiso(int piso) {
        if (andaresSelecionados.contains(piso)) {
            andaresSelecionados.remove(new Integer(piso));
        }
    }

    public List<Integer> getAndaresSelecionados() {
        return andaresSelecionados;
    }

    Sentido getSentido() {
        return sentido;
    }

    public int getAndarAtual() {
		return andarAtual;
	}
    
    public static int QUANTIDADE_ELEVADORES() {
        return QUANTIDADE_ELEVADORES;
    }
    
    @Override
    public String toString() {
        return "Número de andares = " + qtdeAndares + ", andar atual = " + andarAtual + ", sentido = " + sentido;
    }
}