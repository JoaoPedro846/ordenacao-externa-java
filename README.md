# Ordenação Externa em Java

Esse projeto foi desenvolvido para uma atividade da matéria de **Planejamento e Análise de Algoritmos**, com o objetivo de estudar e implementar o conceito de **ordenação externa**, utilizando arquivos para armazenar os dados e o **Merge Sort** para ordenar os registros.

## Sobre o projeto

A ordenação externa é utilizada quando a quantidade de dados é grande demais para ser carregada inteiramente na memória.

Neste projeto, os números são armazenados em um arquivo e processados em **grupos de 20 registros**.

O processo funciona da seguinte forma:

1. Os dados do arquivo principal são divididos em blocos.
2. Cada bloco é carregado na memória e ordenado utilizando **Merge Sort**.
3. Os blocos ordenados são intercalados em pares.
4. A cada etapa, o tamanho dos grupos é duplicado.
5. O processo continua até que todos os registros estejam ordenados em um único arquivo.

## Objetivo

O objetivo do projeto é compreender na prática como algoritmos de ordenação podem trabalhar com grandes quantidades de dados que não cabem simultaneamente na memória principal.

Além disso, o projeto permite praticar:

* Manipulação de arquivos em Java;
* Leitura e escrita de dados;
* Arrays;
* Merge Sort;
* Intercalação de dados;
* Organização de algoritmos de ordenação externa.

## Execução

### Pré-requisitos

É necessário ter o **JDK (Java Development Kit)** instalado.

Para verificar a instalação:

```bash
java -version
```

```bash
javac -version
```

### Compilação

Na raiz do projeto, execute:

```bash
javac -d out src/main/java/gerador/GeradorNumerico.java src/main/java/ordenacao/MergeSort.java src/main/java/ordenacao/OrdenacaoExterna.java
```

### Execução

Execute a classe que contém o método `main`:

```bash
java -cp out NomeDaClasse
```
