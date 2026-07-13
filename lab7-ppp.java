Aluno: Gabriel Vieira Gama
Matricula: 12421bcc035

Laboratório 7 - PPP

1) Bicicleta.java:

public class Bicicleta extends Veiculo {
    private String cor;

    public Bicicleta(String cor){
        this.cor = cor;
    }

    public void accept(Visitor v){
        v.visit(this);
    }

    public String getCor(){
        return this.cor;
    }
}

------------------------------------------------------------------------------------------------------------------------
    Carro.java:

public class Carro extends Veiculo {
    private String cor;
    private String marca;
    private String modelo;

    public Carro(String cor, String marca, String modelo){
        this.cor = cor;
        this.marca = marca;
        this.modelo = modelo;
    }

    public void accept (Visitor v){
        v.visit(this);
    }

    public String getCor(){
        return this.cor;
    }
    public String getMarca(){
        return this.marca;
    }
    public String getModelo(){
        return this.modelo;
    }

}
------------------------------------------------------------------------------------------------------------------------------------------
     ImprimeVisitor.java:

public class ImprimeVisitor implements Visitor {
    @Override
    public void visit(Carro c){
        System.out.println("Carro: Cor: " + c.getCor() + " Marca: " + c.getMarca() + " Modelo: " + c.getModelo());
    }

    @Override
    public void visit(Bicicleta b){
        System.out.println("Bike: Cor: " + b.getCor());
    }

    @Override
    public void visit(Onibus o){
        System.out.println("Bus: Ano: " + o.getAno_fab() + " Qnt assentos: " + o.getQnt_assentos());
    }

}
---------------------------------------------------------------------------------------------------------------------------------------------
      MensagemVisitor.java:

import java.sql.SQLOutput;

public class MensagemVisitor implements Visitor{
    @Override
    public void visit(Carro c){
        System.out.println("Conferir nome do carro e dono!");
    }

    @Override
    public void visit(Bicicleta b){
        System.out.println("Verifique se não furou o pneu da bike!");
    }

    @Override
    public void visit (Onibus o){
        System.out.println("Atualizar combustível do ônibus!");
    }
}
-------------------------------------------------------------------------------------------------------------------------------------------
      Onibus.java:

public class Onibus extends Veiculo {
    private int qnt_assentos;
    private String ano_fab;

    public Onibus(int qnt_assentos, String ano_fab){
        this.qnt_assentos = qnt_assentos;
        this.ano_fab = ano_fab;
    }

    public void accept (Visitor v){
        v.visit(this);
    }

    public int getQnt_assentos(){
        return this.qnt_assentos;
    }

    public String getAno_fab(){
        return this.ano_fab;
    }
}
-------------------------------------------------------------------------------------------------------------------------------------------
     Veiculo.java:

abstract class Veiculo {
    abstract public void accept(Visitor v);
}
------------------------------------------------------------------------------------------------------------------------------------------
     Interface Visitor:

public interface Visitor {
    public void visit(Carro c);
    public void visit(Bicicleta b);
    public void visit(Onibus o);
}
---------------------------------------------------------------------------------------------------------------------------------------------
     Main.java:

import java.util.*;

public class Main {
    public static void main(String[] args){
        ImprimeVisitor imprime = new ImprimeVisitor();
        MensagemVisitor mensagem = new MensagemVisitor();

        List<Veiculo> veiculos = new ArrayList<>();
        Bicicleta b = new Bicicleta("Verde");
        veiculos.add(b);
        Carro c = new Carro("Preto", "Fiat", "Argo");
        veiculos.add(c);
        Onibus o = new Onibus(50,"2014");

        for (Veiculo veiculo : veiculos){
            veiculo.accept(imprime);
            veiculo.accept(mensagem);
        }
    }
}
--------------------------------------------------------------------------------------------------------------------------------------------------


2) 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface DocumentElement {
    void accept(DocumentVisitor visitor);
}


class Paragraph implements DocumentElement {

    private final String text;

    public Paragraph(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}

class Image implements DocumentElement {

    private final String source;
    private final String description;

    public Image(String source, String description) {
        this.source = source;
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}

class Table implements DocumentElement {

    private final List<List<String>> rows;

    public Table(List<List<String>> rows) {
        this.rows = rows;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}

-------------------------------------------------------------------------------------------------------------
interface DocumentVisitor {

    void visit(Paragraph paragraph);

    void visit(Image image);

    void visit(Table table);
}
-------------------------------------------------------------------------------------------------------------

class HtmlExportVisitor implements DocumentVisitor {

    private final StringBuilder html = new StringBuilder();

    public HtmlExportVisitor() {
        html.append("<html>\n<body>\n");
    }

    @Override
    public void visit(Paragraph paragraph) {
        html.append("<p>")
            .append(paragraph.getText())
            .append("</p>\n");
    }

    @Override
    public void visit(Image image) {
        html.append("<img src=\"")
            .append(image.getSource())
            .append("\" alt=\"")
            .append(image.getDescription())
            .append("\">\n");
    }

    @Override
    public void visit(Table table) {
        html.append("<table>\n");

        for (List<String> row : table.getRows()) {
            html.append("  <tr>\n");

            for (String cell : row) {
                html.append("    <td>")
                    .append(cell)
                    .append("</td>\n");
            }

            html.append("  </tr>\n");
        }

        html.append("</table>\n");
    }

    public String getResult() {
        return html + "</body>\n</html>";
    }
}

---------------------------------------------------------------------------------------------------------
class PdfExportVisitor implements DocumentVisitor {

    private final StringBuilder pdfContent = new StringBuilder();

    @Override
    public void visit(Paragraph paragraph) {
        pdfContent.append("PARÁGRAFO: ")
                  .append(paragraph.getText())
                  .append("\n");
    }

    @Override
    public void visit(Image image) {
        pdfContent.append("IMAGEM: ")
                  .append(image.getSource())
                  .append(" - ")
                  .append(image.getDescription())
                  .append("\n");
    }

    @Override
    public void visit(Table table) {
        pdfContent.append("TABELA:\n");

        for (List<String> row : table.getRows()) {
            pdfContent.append("| ");

            for (String cell : row) {
                pdfContent.append(cell).append(" | ");
            }

            pdfContent.append("\n");
        }
    }

    public String getResult() {
        return pdfContent.toString();
    }
}

--------------------------------------------------------------------------------------------
class WordCountVisitor implements DocumentVisitor {

    private int wordCount;

    @Override
    public void visit(Paragraph paragraph) {
        wordCount += countWords(paragraph.getText());
    }

    @Override
    public void visit(Image image) {
        // Neste exemplo, contamos as palavras da descrição.
        wordCount += countWords(image.getDescription());
    }

    @Override
    public void visit(Table table) {
        for (List<String> row : table.getRows()) {
            for (String cell : row) {
                wordCount += countWords(cell);
            }
        }
    }

    private int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }

        return text.trim().split("\\s+").length;
    }

    public int getWordCount() {
        return wordCount;
    }
}

--------------------------------------------------------------------------------------------------
public class Main {

    public static void main(String[] args) {

        List<DocumentElement> document = new ArrayList<>();

        document.add(
            new Paragraph(
                "O padrão Visitor separa os algoritmos dos elementos visitados."
            )
        );

        document.add(
            new Image(
                "diagrama-visitor.png",
                "Diagrama do padrão Visitor"
            )
        );

        document.add(
            new Table(
                Arrays.asList(
                    Arrays.asList("Elemento", "Responsabilidade"),
                    Arrays.asList("Visitor", "Executar operações"),
                    Arrays.asList("Element", "Aceitar visitantes")
                )
            )
        );

                HtmlExportVisitor htmlVisitor = new HtmlExportVisitor();

        for (DocumentElement element : document) {
            element.accept(htmlVisitor);
        }

        System.out.println("===== EXPORTAÇÃO HTML =====");
        System.out.println(htmlVisitor.getResult());

        // ----------------------------------------------
        // Exportação para PDF
        // ----------------------------------------------

        PdfExportVisitor pdfVisitor = new PdfExportVisitor();

        for (DocumentElement element : document) {
            element.accept(pdfVisitor);
        }

        System.out.println("\n===== EXPORTAÇÃO PDF =====");
        System.out.println(pdfVisitor.getResult());

        
        WordCountVisitor wordCountVisitor = new WordCountVisitor();

        for (DocumentElement element : document) {
            element.accept(wordCountVisitor);
        }

        System.out.println("===== CONTAGEM DE PALAVRAS =====");
        System.out.println(
            "Quantidade total: " + wordCountVisitor.getWordCount()
        );
    }
}
