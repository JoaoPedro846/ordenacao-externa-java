package gerador;

import java.io.*;
import java.util.Random;

public class GeradorNumerico {

    private static Random rd = new Random();

    public static void gerador(File arquivo, int registros){

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(arquivo))){
            for(int i = 0; i < registros; i++){
                wr.write(Integer.toString(rd.nextInt(1, 100)));
                wr.newLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}