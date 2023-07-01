import java.util.Scanner;


import classes.EscondeEscondeGame;

class Main {

    public static void main(String args[]) {
        EscondeEscondeGame game = new EscondeEscondeGame();
        Scanner scanner = new Scanner(System.in);
        boolean gameFinished = false;
        while (!gameFinished) {
            game.printBoard();

            System.out.println("Jogador " + game.getCurrentPlayer() + ", faça sua jogada:");
            System.out.println("1. Jogar Quero-Quero (exemplo: quero-quero 2 4)");
            System.out.println("2. Jogar Zorrilho (exemplo: zorrilho 2 4)");
            System.out.println("3. Salvar jogo (exemplo: salvar game.txt)");
            System.out.println("4. Carregar jogo (exemplo: carregar game.txt)");
            System.out.println("5. Sair do jogo");

            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String command = parts[0];

            switch (command) {
                case "quero-quero":
                    if (parts.length == 3) {
                        int row = Integer.parseInt(parts[1]);
                        int col = Integer.parseInt(parts[2]);
                        game.playQueroQuero(row, col);
                    } else {
                        System.out.println("Comando inválido!");
                    }
                    break;
                case "zorrilho":
                    if (parts.length == 3) {
                        int row = Integer.parseInt(parts[1]);
                        int col = Integer.parseInt(parts[2]);
                        game.playZorrilho(row, col);
                    } else {
                        System.out.println("Comando inválido!");
                    }
                    break;
                case "salvar":
                    if (parts.length == 2) {
                        String filename = parts[1];
                        game.saveGame(filename);
                        System.out.println("Jogo salvo com sucesso!");
                    } else {
                        System.out.println("Comando inválido!");
                    }
                    break;
                case "carregar":
                    if (parts.length == 2) {
                        String filename = parts[1];
                        game.loadGame(filename);
                        System.out.println("Jogo carregado com sucesso!");
                    } else {
                        System.out.println("Comando inválido!");
                    }
                    break;
                case "sair":
                    gameFinished = true;
                    break;
                default:
                    System.out.println("Comando inválido!");
                    break;
            }
        }

        System.out.println("Obrigado por jogar! Até mais!");
        scanner.close();
    }

    
}