/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextMining;

import dominio.*;
import conexao.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lefoly
 */
public class Comparador {

    public static ArrayList<String> termosTempP = new ArrayList<String>();
    public static ArrayList<String> termosTempR = new ArrayList<String>();
    public static ArrayList<String> conceitosTempP = new ArrayList<String>();
    public static ArrayList<String> conceitosTempR = new ArrayList<String>();
    public static ArrayList<String> stemsRelevantesP = new ArrayList<String>();
    public static ArrayList<String> stemsRelevantesR = new ArrayList<String>();
    public static ArrayList stemsFreqP = new ArrayList();
    public static ArrayList stemsFreqR = new ArrayList();
    public static float resultado;

    public static void obterTermosRelevantes(String origem, int idDisciplina, String sentenca) throws SQLException {
        sentenca = sentenca.toLowerCase();
        TermoDAO dao = new TermoDAOImp();
        List<Termo> termos = dao.getListaFiltrada(idDisciplina);
        for (int i = 0; i <= termos.size() - 1; i++) {
            Termo termo = termos.get(i);
            String termoDesc = Minerador.Radicalizar(termo.getNome().toLowerCase());
            //System.out.println(termo.getNome().toLowerCase() + " -> " + termoDesc);
            if (sentenca.contains(termoDesc)) {
                //if (origem.equals("Pergunta")) {
                //    termosTempP.add(termoDesc);
                //} else if (origem.equals("Resposta")) {
                //    termosTempR.add(termoDesc);
                //}
                obterConceitosRelevantes(origem, termo.getID());
            }
        }
        //System.out.println("Total termosTempP: " + termosTempP.size());
    }

    public static void obterConceitosRelevantes(String origem, int idTermo) throws SQLException {
        ConceitoDAO dao = new ConceitoDAOImp();
        List<Conceito> conceitos = dao.getListaFiltrada(idTermo);
        if (conceitos.size() > 0) {
            String[] conceitosDesc = Minerador.processar(conceitos.get(0).getDescricao()).split(" ");
            if (origem.equals("Pergunta")) {
                for (int i = 0; i <= conceitosDesc.length - 1; i++) {
                    conceitosTempP.add(conceitosDesc[i]);
                }
            } else if (origem.equals("Resposta")) {
                for (int i = 0; i <= conceitosDesc.length - 1; i++) {
                    conceitosTempR.add(conceitosDesc[i]);
                }
            }
        }
    }

    public static void obterFrequenciaTermos(String origem) {
        int frequencia, i, tamanho;
        if (origem.equals("Pergunta")) {
            Collections.sort(termosTempP);
            i = 0;
            String termo;
            tamanho = termosTempP.size();
            //System.out.println("Tamanho do termosTempP: " + tamanho);
            while (i < tamanho) {
                frequencia = 0;
                termo = termosTempP.get(i);
                stemsRelevantesP.add(termo);
                while ((i < tamanho) && (termo.equals(termosTempP.get(i)))) {
                    frequencia++;
                    i++;
                }
                stemsFreqP.add(frequencia);
            }
        } else if (origem.equals("Resposta")) {
            Collections.sort(termosTempR);
            i = 0;
            String termo;
            tamanho = termosTempR.size();
            //System.out.println("Tamanho do termosTempR: " + tamanho);
            while (i < tamanho) {
                frequencia = 0;
                termo = termosTempR.get(i);
                stemsRelevantesR.add(termo);
                while ((i < tamanho) && (termo.equals(termosTempR.get(i)))) {
                    frequencia++;
                    i++;
                }
                stemsFreqR.add(frequencia);
            }
        }
    }

    public static void obterFrequenciaConceitos(String origem) {
        int frequencia, i, tamanho;
        if (origem.equals("Pergunta")) {
            Collections.sort(conceitosTempP);
            i = 0;
            String conceito;
            tamanho = conceitosTempP.size();
            while (i < tamanho) {
                frequencia = 0;
                conceito = conceitosTempP.get(i);
                stemsRelevantesP.add(conceito);
                while ((i < tamanho) && (conceito.equals(conceitosTempP.get(i)))) {
                    frequencia++;
                    i++;
                }
                stemsFreqP.add(frequencia);
            }
        } else if (origem.equals("Resposta")) {
            Collections.sort(conceitosTempR);
            i = 0;
            String conceito;
            tamanho = conceitosTempR.size();
            while (i < tamanho) {
                frequencia = 0;
                conceito = conceitosTempR.get(i);
                stemsRelevantesR.add(conceito);
                while ((i < tamanho) && (conceito.equals(conceitosTempR.get(i)))) {
                    frequencia++;
                    i++;
                }
                stemsFreqR.add(frequencia);
            }
        }
    }

    public static void main(String args[]) throws SQLException {
        String pergTeste = "Conceitue: objetos, atributo, diagrama de componentes e caso de uso:";
        String respTeste = "Objeto é a instância de uma classe. " +
                           "Atributo é uma característica que os objetos possuem. " +
                           "Diagrama de Componentes mostra a interação entre os componentes em um sistema. " +
                           "E o caso de uso mostra a ação de um ator dentro de um sistema.";
        String pergTeste2 = "O que é um objeto?";
        String respTeste2 = "É a instância de uma classe.";

        String pergTeste3 = "Explique a utilidade do Diagrama de Classes no processo de Projeto:";
        String respTeste3 = "O diagrama de classes mostra as classes, não somente conceituais, mas também as do software.";
        //String respTeste3 = "O diagrama de classes.";

        String pergTeste4 = "Conceitue Requisitos de Domínio:";
        String respTeste4 = "São características que refletem um dado domínio da aplicação. Considere um sistema de solução de sistemas lineares, pode haver o requisito de solução do sistema através de um método específico, como Gauss-Seidel.";

        Processar(pergTeste2, respTeste2, 1);
        ListarTudo();
        System.out.printf("\nO aluno acertou %.2f%% da questão.\n", resultado);
        System.out.printf("A nota sugerida é: %.1f\n\n", resultado/10);
    }

    public static float Processar(String pergunta, String resposta, int disciplina) throws SQLException {
        termosTempP.clear();
        termosTempR.clear();
        conceitosTempP.clear();
        conceitosTempR.clear();
        stemsRelevantesP.clear();
        stemsRelevantesR.clear();
        stemsFreqP.clear();
        stemsFreqR.clear();
        resultado = 0;

        //int disciplina = 2;
        obterTermosRelevantes("Pergunta", disciplina, pergunta);
        obterFrequenciaTermos("Pergunta");
        obterFrequenciaConceitos("Pergunta");

        //obterTermosRelevantes("Resposta", disciplina, resposta);

        //esse é o novo obterTErmosRelevantes("Resposta")
        String respostaNova = Minerador.processar(resposta);
        String respostaNovaV[] = respostaNova.split("\\s");
        for (int i = 0; i <= respostaNovaV.length-1; i++) {
            //stemsRelevantesR.add(respostaNovaV[i]);
            termosTempR.add(respostaNovaV[i]);
        }
        obterFrequenciaTermos("Resposta");
        //obterFrequenciaConceitos("Resposta");

        Comparar();

        return resultado;
    }

    private static void ListarTudo() {
        System.out.println("Pergunta");
        for (int i = 0; i < stemsRelevantesP.size(); i++) {
            System.out.println("      " + stemsRelevantesP.get(i) + " = " + stemsFreqP.get(i));
        }
        System.out.println("-----------------------------------");
        System.out.println("Resposta");
        for (int i = 0; i < stemsRelevantesR.size(); i++) {
            System.out.println("      " + stemsRelevantesR.get(i) + " = " + stemsFreqR.get(i));
        }
    }

    private static void Comparar() {

        ArrayList<String> termosEspelho = new ArrayList<String>();
        ArrayList<String> conceitosEspelho = new ArrayList<String>();
        ArrayList termosFreqEspelho = new ArrayList();
        ArrayList conceitosFreqEspelho = new ArrayList();
        int tamanhoP, tamanhoR, tamanhoE;

        tamanhoP = stemsRelevantesP.size();
        tamanhoR = stemsRelevantesR.size();

        //System.out.println("tamanhoP = " + tamanhoP);
        //System.out.println("tamanhoR = " + tamanhoR);

        for (int i = 0; i <= tamanhoP - 1; i++) {
            String termo = stemsRelevantesP.get(i);
            Object freq = stemsFreqP.get(i);
            int j = 0;
            if (tamanhoR > 0) {
                while ((j < tamanhoR - 1) && (! stemsRelevantesR.get(j).equals(termo))) {
                    j++;
                }
                if (stemsRelevantesR.get(j).equals(termo)) {
                    termosEspelho.add(termo);
                    termosFreqEspelho.add(freq);
                }
            } 
        }
        tamanhoE = termosEspelho.size();
        float somaPergunta = 0, somaEspelho = 0;
        for (int i = 0; i <= tamanhoP - 1; i++) {
            somaPergunta = somaPergunta + Integer.parseInt(stemsFreqP.get(i).toString());
        }
        for (int i = 0; i <= tamanhoE - 1; i++) {
            somaEspelho = somaEspelho + Integer.parseInt(termosFreqEspelho.get(i).toString());
        }
        System.out.println(somaEspelho);
        System.out.println(somaPergunta);
        resultado = (somaEspelho / somaPergunta)  * 100;
    }
}

