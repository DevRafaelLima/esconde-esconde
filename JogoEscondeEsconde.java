import java.util.Scanner;

public class JogoEscondeEsconde {
    private char[][] tabuleiro;
    private boolean[][] jogador1Conhecido;
    private boolean[][] jogador2Conhecido;
    private int tamanhoTabuleiro;
    private int lancamentosJogador1;
    private int lancamentosJogador2;

    public JogoEscondeEsconde(int tamanhoTabuleiro) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
        this.tabuleiro = new char[tamanhoTabuleiro][tamanhoTabuleiro];
        this.jogador1Conhecido = new boolean[tamanhoTabuleiro][tamanhoTabuleiro];
        this.jogador2Conhecido = new boolean[tamanhoTabuleiro][tamanhoTabuleiro];
        this.lancamentosJogador1 = 0;
        this.lancamentosJogador2 = 0;
    }

    public void iniciarJogo() {
        inicializarTabuleiro();
        System.out.println("Jogador 1, esconda as pessoas:");
        posicionarPessoas(1);
        System.out.println("Jogador 2, esconda as pessoas:");
        posicionarPessoas(2);
        System.out.println("O jogo começou!");

        while (true) {
            exibirTabuleiro(jogador2Conhecido);
            realizarLance(2);
            exibirTabuleiro(jogador1Conhecido);
            realizarLance(1);

            if (verificarVitoria(jogador1Conhecido)) {
                System.out.println("Jogador 1 venceu!");
                break;
            } else if (verificarVitoria(jogador2Conhecido)) {
                System.out.println("Jogador 2 venceu!");
                break;
            }
        }
    }

    private void inicializarTabuleiro() {
        for (int i = 0; i < tamanhoTabuleiro; i++) {
            for (int j = 0; j < tamanhoTabuleiro; j++) {
                tabuleiro[i][j] = '.';
            }
        }
    }

    private void posicionarPessoas(int jogador) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            System.out.println("Posicione a pessoa " + (i + 1) + " do Jogador " + jogador);
            System.out.print("Informe a linha: ");
            int linha = scanner.nextInt();
            System.out.print("Informe a coluna: ");
            int coluna = scanner.nextInt();
            if (posicaoValida(linha, coluna) && tabuleiro[linha][coluna] == '.') {
                tabuleiro[linha][coluna] = (char) (jogador + '0');
                ocuparPosicoesAdjacentes(linha, coluna, jogador);
            } else {
                System.out.println("Posição inválida ou já ocupada. Tente novamente.");
                i--;
            }
        }
    }

    private void ocuparPosicoesAdjacentes(int linha, int coluna, int jogador) {
        if (posicaoValida(linha + 1, coluna)) {
            tabuleiro[linha + 1][coluna] = (char) (jogador + '0');
        }
        if (posicaoValida(linha - 1, coluna)) {
            tabuleiro[linha - 1][coluna] = (char) (jogador + '0');
        }
        if (posicaoValida(linha, coluna + 1)) {
            tabuleiro[linha][coluna + 1] = (char) (jogador + '0');
        }
        if (posicaoValida(linha, coluna - 1)) {
            tabuleiro[linha][coluna - 1] = (char) (jogador + '0');
        }
    }

    private boolean posicaoValida(int linha, int coluna) {
        return linha >= 0 && linha < tamanhoTabuleiro && coluna >= 0 && coluna < tamanhoTabuleiro;
    }

    private void exibirTabuleiro(boolean[][] jogadorConhecido) {
        System.out.println("Tabuleiro:");
        for (int i = 0; i < tamanhoTabuleiro; i++) {
            for (int j = 0; j < tamanhoTabuleiro; j++) {
                if (jogadorConhecido[i][j]) {
                    if (tabuleiro[i][j] == '3') {
                        System.out.print("3 ");
                    } else {
                        System.out.print(tabuleiro[i][j] + " ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void realizarLance(int jogador) {
        System.out.println("Jogador " + jogador + ", faça seu lance:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha uma opção:");
        System.out.println("1. Jogar um Quero-Quero");
        System.out.println("2. Jogar um Zorrilho");

        int opcao = scanner.nextInt();
        if (opcao == 1) {
            realizarLanceQueroQuero(jogador);
        } else if (opcao == 2) {
            if (jogador == 1 && lancamentosJogador1 >= 3) {
                System.out.println("Você não pode mais jogar um Zorrilho. Escolha outra opção.");
                realizarLance(jogador);
                return;
            } else if (jogador == 2 && lancamentosJogador2 >= 3) {
                System.out.println("Você não pode mais jogar um Zorrilho. Escolha outra opção.");
                realizarLance(jogador);
                return;
            }

            realizarLanceZorrilho(jogador);
            if (jogador == 1) {
                lancamentosJogador1++;
            } else {
                lancamentosJogador2++;
            }
        } else {
            System.out.println("Opção inválida. Escolha outra opção.");
            realizarLance(jogador);
        }
    }

    private void realizarLanceQueroQuero(int jogador) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe a linha: ");
        int linha = scanner.nextInt();
        System.out.print("Informe a coluna: ");
        int coluna = scanner.nextInt();

        if (posicaoValida(linha, coluna) && !jogador1Conhecido[linha][coluna] && !jogador2Conhecido[linha][coluna]) {
            revelarPosicao(linha, coluna, jogador);
        } else {
            System.out.println("Posição inválida ou já revelada. Tente novamente.");
            realizarLanceQueroQuero(jogador);
        }
    }

    private void realizarLanceZorrilho(int jogador) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe a linha: ");
        int linha = scanner.nextInt();
        System.out.print("Informe a coluna: ");
        int coluna = scanner.nextInt();

        if (posicaoValida(linha, coluna) && !jogador1Conhecido[linha][coluna] && !jogador2Conhecido[linha][coluna]) {
            revelarPosicao(linha, coluna, jogador);

            // Formato de +
            revelarPosicao(linha - 1, coluna, jogador);
            revelarPosicao(linha + 1, coluna, jogador);
            revelarPosicao(linha, coluna - 1, jogador);
            revelarPosicao(linha, coluna + 1, jogador);
        } else {
            System.out.println("Posição inválida ou já revelada. Tente novamente.");
            realizarLanceZorrilho(jogador);
        }
    }

    private void revelarPosicao(int linha, int coluna, int jogador) {
        if (jogador == 1) {
            jogador1Conhecido[linha][coluna] = true;
        } else {
            jogador2Conhecido[linha][coluna] = true;
        }

        if (tabuleiro[linha][coluna] == '.') {
            System.out.println("Não há nenhuma pessoa escondida nessa posição.");
            tabuleiro[linha][coluna] = 'x';
        } else {
            int valor = Character.getNumericValue(tabuleiro[linha][coluna]);
            if (valor != jogador) {
                System.out.println("Acertou uma parte de uma pessoa do oponente!");
            } else {
                if (verificarPosicaoOcupada(tabuleiro, linha, coluna)) {
                    tabuleiro[linha][coluna] = '3';
                    System.out.println("Acertou um esconderijo com duas pessoas!");
                } else {
                    System.out.println("Acertou um esconderijo!");
                }
            }
        }
    }

    private boolean verificarPosicaoOcupada(char[][] tabuleiro, int linha, int coluna) {
        if (posicaoValida(linha + 1, coluna) && posicaoValida(linha + 2, coluna) && posicaoValida(linha + 2, coluna + 1)) {
            char pessoa = tabuleiro[linha][coluna];
            return tabuleiro[linha + 1][coluna] == pessoa && tabuleiro[linha + 2][coluna] == pessoa && tabuleiro[linha + 2][coluna + 1] == pessoa;
        } else if (posicaoValida(linha - 1, coluna - 1) && posicaoValida(linha - 1, coluna) && posicaoValida(linha, coluna - 1)) {
            char pessoa = tabuleiro[linha][coluna];
            return tabuleiro[linha - 1][coluna - 1] == pessoa && tabuleiro[linha - 1][coluna] == pessoa && tabuleiro[linha][coluna - 1] == pessoa;
        }
        return false;
    }

    private boolean verificarVitoria(boolean[][] jogadorConhecido) {
        int contador1 = 0;
        int contador2 = 0;
        for (int i = 0; i < tamanhoTabuleiro; i++) {
            for (int j = 0; j < tamanhoTabuleiro; j++) {
                if (jogadorConhecido[i][j] && tabuleiro[i][j] == '1') {
                    contador1++;
                } else if (jogadorConhecido[i][j] && tabuleiro[i][j] == '2') {
                    contador2++;
                }
            }
        }
        return contador1 == 3 || contador2 == 3;
    }
}
