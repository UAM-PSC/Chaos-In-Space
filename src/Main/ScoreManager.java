package Main;

import Entities.Player;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class ScoreManager {
    public static String fileName = "res/scoreboard.txt";
    public static void saveScoreboard(){
        try {
            FileWriter writer = new FileWriter(fileName);


            for (int i = 0; i < Game.scoreboard.length; i++) { // Escrever os elementos do vetor no arquivo
                writer.write(Integer.toString(Game.scoreboard[i]));
                writer.write(System.lineSeparator()); // Adiciona quebra de linha
            }


            writer.close(); // Fechar o FileWriter
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar o Scoreboard em: " + e.getMessage());
        }
    }

    public static void readScoreboard(){
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            // Ler o vetor do arquivo
            int[] vetor = new int[Game.scoreboard.length]; // Tamanho do vetor
            int index = 0;

            while (scanner.hasNextInt() && index < vetor.length) {
                vetor[index] = scanner.nextInt();
                Game.scoreboard[index] = vetor[index];
                index++;
            }

            scanner.close(); // Fechar o scanner
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo não foi encontrado: " + e.getMessage());
        }
    }

}
