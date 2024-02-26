package com.ccs.megadavirada;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //lista de numeros que o usuario informa
        List<Integer> numerosDoUsuario = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Quantos números que deseja informar:");

        int qtdNumerosParaInformar = scanner.nextInt();

        System.out.println("Informe a qtd de números por cartão:");
        int qtdNumerosPorCartao = scanner.nextInt();

        System.out.println("Quantos cartões deseja jogar?");
        int qtdCartoes = scanner.nextInt();

        long combinacoes = calcularCombinacao(qtdNumerosParaInformar, qtdNumerosPorCartao);

        while (combinacoes < qtdCartoes) {
            System.out.println("Número de máximo de combinações possíveis é " + combinacoes
                    + " informe uma qtd menor de cartões.");
            System.out.println("Quantos cartões deseja jogar?");
            qtdCartoes = scanner.nextInt();
            combinacoes = calcularCombinacao(qtdNumerosParaInformar, qtdNumerosPorCartao);
        }
        
        lerNumerosDoUsuario(qtdNumerosParaInformar, numerosDoUsuario, scanner);

        List<List<Integer>> cartoesGerados = new ArrayList<>(qtdCartoes);

        for (int i = 0; i < qtdCartoes; i++) {

            List<Integer> cartaoGerado = new ArrayList<>(qtdNumerosPorCartao);
            boolean isCartaoRepetido = false;

            do {
                cartaoGerado = gerarCartao(numerosDoUsuario, qtdNumerosPorCartao);
                isCartaoRepetido = verificarCartaoRepetido(cartoesGerados, cartaoGerado);

                if (isCartaoRepetido) {
                    System.out.println("Cartão repetido detectado gerando outro...");
                }

            } while (isCartaoRepetido);

            cartoesGerados.add(cartaoGerado);
        }
        imprimir(cartoesGerados);
    }

    private static boolean verificarCartaoRepetido(List<List<Integer>> cartoesGerados, List<Integer> cartaoGerado) {
        return cartoesGerados
                .stream()
                .anyMatch(cartao -> cartao.containsAll(cartaoGerado));
    }

    private static List<Integer> gerarCartao(List<Integer> numerosDisponiveis, int qtdNumerosPorCartao) {

        List<Integer> temp = new ArrayList<>(numerosDisponiveis);

        List<Integer> dezenasEscolhidas = new ArrayList<>(6);

        Random random = new Random();

        for (int i = 0; i < qtdNumerosPorCartao; i++) {
            int indiceAleatorio = random.nextInt(temp.size());
            int numeroAleatorio = temp.get(indiceAleatorio);

            // Remover o número escolhido para evitar repetição
            temp.remove(indiceAleatorio);

            if (dezenasEscolhidas.contains(numeroAleatorio)) {
                throw new RuntimeException("Erro numero repetido no cartão, isto não deveria acontecer :(");
            }

            dezenasEscolhidas.add(numeroAleatorio);
        }

        return dezenasEscolhidas.stream().sorted().collect(Collectors.toList());
    }

    private static void lerNumerosDoUsuario(int qtdNumerosParaInformar, List<Integer> numerosDoUsuario, Scanner scanner) {
        for (int i = 1; i <= qtdNumerosParaInformar; i++) {
            System.out.println("Informe o numero " + i + "º da sua lista:");
            int numero = scanner.nextInt();

            while (numero < 1 || numero > 60) {
                System.out.println("numero inválido informe entre 1 e 60");
                numero = scanner.nextInt();
            }

            while (numerosDoUsuario.contains(numero)) {
                System.out.println("numero ja informado, informe outro entre 1 e 60");
                numero = scanner.nextInt();
            }
            numerosDoUsuario.add(numero);
        }
    }

    private static void imprimir(List<List<Integer>> todas) {

        System.out.println("### Imprimindo seus cartões ###");
        todas.forEach(integers ->
                System.out.println(Arrays.toString(integers.toArray())));
    }

    // Método para calcular combinações (n choose r)
    public static long calcularCombinacao(int n, int r) {
        // Verificar se n e r são não negativos e r não é maior que n
        if (n < 0 || r < 0 || r > n) {
            throw new IllegalArgumentException("Argumentos inválidos. Certifique-se de que n e r sejam não negativos e r não seja maior que n.");
        }

        // Inicializar variáveis para calcular o fatorial
        long numerador = 1;
        long denominador = 1;

        // Calcular o numerador (n!)
        for (int i = n; i > 0; i--) {
            numerador *= i;
        }

        // Calcular o denominador (r! * (n-r)!)
        for (int i = r; i > 0; i--) {
            denominador *= i;
        }

        for (int i = n - r; i > 0; i--) {
            denominador *= i;
        }

        // Calcular a combinação
        return numerador / denominador;
    }
}
