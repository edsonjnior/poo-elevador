package br.com.edsondev.elevador.enums;

public enum Sentido {
	SUBINDO("subindo"),
    DESCENDO("descendo"),
    PARADO("parado");

    private String valor;

    Sentido(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
