package ordenacao;

import java.io.*;

public class OrdenacaoExterna {

    public File arquivoPrincipal;
    public File arquivoFinal;

    public File bloco1;
    public File bloco2;
    public File bloco3;
    public File bloco4;

    public OrdenacaoExterna(File arquivoPrincipal) {

        this.arquivoPrincipal = arquivoPrincipal;

        String local = arquivoPrincipal.getAbsoluteFile().getParent();

        bloco1 = new File(local, "bloco1.txt");
        bloco2 = new File(local, "bloco2.txt");
        bloco3 = new File(local, "bloco3.txt");
        bloco4 = new File(local, "bloco4.txt");
        arquivoFinal = new File(local, "arquivoFinal.txt");

        try {
            bloco1.createNewFile();
            bloco2.createNewFile();
            bloco3.createNewFile();
            bloco4.createNewFile();
            arquivoFinal.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ordenar(int tamanhoBloco) {

        separarBlocos(tamanhoBloco);

        ordenarArquivo(bloco1, tamanhoBloco);
        ordenarArquivo(bloco2, tamanhoBloco);

        int quantidadeDados = quantidadeDados();

        File entrada1 = bloco1;
        File entrada2 = bloco2;

        File saida1 = bloco3;
        File saida2 = bloco4;

        int tamanhoAtual = tamanhoBloco;

        while (tamanhoAtual < quantidadeDados) {

            if (tamanhoAtual * 2 >= quantidadeDados) {

                unirArquivoFinal(
                        entrada1,
                        entrada2,
                        tamanhoAtual
                );

                break;
            }

            unirBlocos(entrada1, entrada2, saida1, saida2, tamanhoAtual);

            tamanhoAtual *= 2;

            File temp;

            temp = entrada1;
            entrada1 = saida1;
            saida1 = temp;

            temp = entrada2;
            entrada2 = saida2;
            saida2 = temp;
        }
    }

    public void separarBlocos(int tamanhoBlocos) {

        try (
                BufferedReader br = new BufferedReader(new FileReader(arquivoPrincipal));
                BufferedWriter bw1 = new BufferedWriter(new FileWriter(bloco1));
                BufferedWriter bw2 = new BufferedWriter(new FileWriter(bloco2))
        ) {

            String linha;

            int dadosLidos = 0;
            int blocoEscolhido = 1;

            while ((linha = br.readLine()) != null) {

                linha = linha.trim();

                if (dadosLidos == tamanhoBlocos) {
                    dadosLidos = 0;
                    blocoEscolhido = proximoBloco(blocoEscolhido);
                }

                if (blocoEscolhido == 1) {
                    bw1.write(linha);
                    bw1.newLine();
                } else {
                    bw2.write(linha);
                    bw2.newLine();
                }

                dadosLidos++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ordenarArquivo(File bloco, int tamanhoBloco) {

        try {File temporario = new File(bloco.getParent(), "temp.txt");

            try (BufferedReader br = new BufferedReader(new FileReader(bloco));
                 BufferedWriter bw = new BufferedWriter(new FileWriter(temporario)))
            {
                String linha;

                int[] dados = new int[tamanhoBloco];
                int quantidade = 0;

                while ((linha = br.readLine()) != null) {

                    dados[quantidade] =
                            Integer.parseInt(linha);

                    quantidade++;

                    if (quantidade == tamanhoBloco) {

                        int[] ordenados =
                                MergeSort.mergeSort(dados);

                        escreverArray(bw, ordenados);

                        dados = new int[tamanhoBloco];
                        quantidade = 0;
                    }
                }

                if (quantidade > 0) {

                    int[] dadosFinais =
                            new int[quantidade];

                    for (int i = 0; i < quantidade; i++) {
                        dadosFinais[i] = dados[i];
                    }

                    int[] ordenados =
                            MergeSort.mergeSort(dadosFinais);

                    escreverArray(bw, ordenados);
                }
            }

            if (!bloco.delete()) {
                throw new IOException(
                        "Não foi possível apagar o bloco."
                );
            }

            if (!temporario.renameTo(bloco)) {
                throw new IOException(
                        "Não foi possível renomear o arquivo."
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void unirBlocos(File entrada1, File entrada2, File saida1, File saida2, int tamanhoBloco) {
        try (
                BufferedReader br1 = new BufferedReader(new FileReader(entrada1));
                BufferedReader br2 = new BufferedReader(new FileReader(entrada2));
                BufferedWriter bw1 = new BufferedWriter(new FileWriter(saida1));
                BufferedWriter bw2 = new BufferedWriter(new FileWriter(saida2)))
        {
            int blocoEscolhido = 1;

            while (true) {

                int[] dadosB1 =
                        lerGrupo(br1, tamanhoBloco);

                int[] dadosB2 =
                        lerGrupo(br2, tamanhoBloco);

                if (dadosB1.length == 0 &&
                        dadosB2.length == 0) {

                    break;
                }

                int[] resultado;

                if (dadosB1.length == 0) {

                    resultado = dadosB2;

                } else if (dadosB2.length == 0) {

                    resultado = dadosB1;

                } else {

                    resultado =
                            MergeSort.merge(
                                    dadosB1,
                                    dadosB2
                            );
                }

                if (blocoEscolhido == 1) {

                    escreverArray(bw1, resultado);

                } else {

                    escreverArray(bw2, resultado);
                }

                blocoEscolhido =
                        proximoBloco(blocoEscolhido);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void unirArquivoFinal(File entrada1, File entrada2, int tamanhoBloco)
    {
        try (BufferedReader br1 = new BufferedReader(new FileReader(entrada1));
             BufferedReader br2 = new BufferedReader(new FileReader(entrada2));
             BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoFinal)))
        {
            while (true) {

                int[] dadosB1 =
                        lerGrupo(br1, tamanhoBloco);

                int[] dadosB2 =
                        lerGrupo(br2, tamanhoBloco);

                if (dadosB1.length == 0 &&
                        dadosB2.length == 0) {

                    break;
                }

                int[] resultado;

                if (dadosB1.length == 0) {

                    resultado = dadosB2;

                } else if (dadosB2.length == 0) {

                    resultado = dadosB1;

                } else {

                    resultado =
                            MergeSort.merge(
                                    dadosB1,
                                    dadosB2
                            );
                }

                escreverArray(bw, resultado);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int[] lerGrupo(BufferedReader br, int tamanho) throws IOException {

        int[] dados = new int[tamanho];

        int quantidade = 0;

        String linha;

        while (
                quantidade < tamanho &&
                        (linha = br.readLine()) != null
        ) {

            dados[quantidade] =
                    Integer.parseInt(linha);

            quantidade++;
        }

        if (quantidade == tamanho) {
            return dados;
        }

        int[] dadosFinais =
                new int[quantidade];

        for (int i = 0; i < quantidade; i++) {
            dadosFinais[i] = dados[i];
        }

        return dadosFinais;
    }

    private void escreverArray(BufferedWriter bw, int[] dados) throws IOException {

        for (int i = 0; i < dados.length; i++) {

            bw.write(String.valueOf(dados[i]));
            bw.newLine();
        }
    }

    private int quantidadeDados() {

        int quantidade = 0;

        try (
                BufferedReader br = new BufferedReader(new FileReader(arquivoPrincipal)))
        {
            while (br.readLine() != null) {
                quantidade++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return quantidade;
    }

    private int proximoBloco(int blocoEscolhido) {

        if (blocoEscolhido == 2) {
            return 1;
        }

        return 2;
    }
}