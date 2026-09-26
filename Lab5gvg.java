Aluno: Gabriel Vieira Gama 12421bcc035

1) import java.util.*;

interface PagamentoStrategy {
    void pagar(double valor);
}

class PixPagamento implements PagamentoStrategy {
    public void pagar(double valor) {
        System.out.println("Pagamento via Pix: R$ " + valor);
    }
}

class CartaoPagamento implements PagamentoStrategy {
    public void pagar(double valor) {
        System.out.println("Pagamento via Cartão: R$ " + valor);
    }
}

class BoletoPagamento implements PagamentoStrategy {
    public void pagar(double valor) {
        System.out.println("Pagamento via Boleto: R$ " + valor);
    }
}

class Item {
    String nome;
    double preco;

    Item(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }
}

class CarrinhoCompras {
    List<Item> itens = new ArrayList<>();

    void adicionaItem(Item item) {
        itens.add(item);
    }

    void removeItem(Item item) {
        itens.remove(item);
    }

    double calculaTotal() {
        double total = 0;
        for (Item item : itens) {
            total += item.preco;
        }
        return total;
    }

    void realizaPagamento(PagamentoStrategy pagamento) {
        pagamento.pagar(calculaTotal());
    }
}

public class Main {
    public static void main(String[] args) {
        CarrinhoCompras carrinho = new CarrinhoCompras();

        carrinho.adicionaItem(new Item("Teclado", 100));
        carrinho.adicionaItem(new Item("Mouse", 50));

        carrinho.realizaPagamento(new PixPagamento());
        carrinho.realizaPagamento(new CartaoPagamento());
        carrinho.realizaPagamento(new BoletoPagamento());
    }
}

--------------------------------------------------------------------------------------------------------------------------
2) import java.util.*;

interface PagamentoStrategy {
    void pagar(double valor);
}

interface FreteStrategy {
    double calcular(double valor);
}

class PixPagamento implements PagamentoStrategy {
    public void pagar(double valor) {
        System.out.println("Pagamento via Pix: R$ " + valor);
    }
}

class CartaoPagamento implements PagamentoStrategy {
    public void pagar(double valor) {
        System.out.println("Pagamento via Cartão: R$ " + valor);
    }
}

class BoletoPagamento implements PagamentoStrategy {
    public void pagar(double valor) {
        System.out.println("Pagamento via Boleto: R$ " + valor);
    }
}

class SedexFrete implements FreteStrategy {
    public double calcular(double valor) {
        return 30;
    }
}

class NormalFrete implements FreteStrategy {
    public double calcular(double valor) {
        return 15;
    }
}

class Item {
    String nome;
    double preco;

    Item(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }
}

class CarrinhoCompras {
    List<Item> itens = new ArrayList<>();

    void adicionaItem(Item item) {
        itens.add(item);
    }

    void removeItem(Item item) {
        itens.remove(item);
    }

    double calculaTotal() {
        double total = 0;
        for (Item item : itens) {
            total += item.preco;
        }
        return total;
    }

    double calculaFrete(FreteStrategy frete) {
        return frete.calcular(calculaTotal());
    }

    void realizaPagamento(PagamentoStrategy pagamento, FreteStrategy frete) {
        double total = calculaTotal() + calculaFrete(frete);
        pagamento.pagar(total);
    }
}

public class Main {
    public static void main(String[] args) {
        CarrinhoCompras carrinho = new CarrinhoCompras();

        carrinho.adicionaItem(new Item("Notebook", 3000));
        carrinho.adicionaItem(new Item("Mouse", 50));

        carrinho.realizaPagamento(new PixPagamento(), new SedexFrete());
        carrinho.realizaPagamento(new CartaoPagamento(), new NormalFrete());
    }
}


3) import java.util.*;

interface Observador {
    void atualizar(double temperatura, double umidade, double vento);
}

class CET {
    List<Observador> observadores = new ArrayList<>();
    double temperatura;
    double umidade;
    double vento;

    void adicionarObservador(Observador observador) {
        observadores.add(observador);
    }

    void removerObservador(Observador observador) {
        observadores.remove(observador);
    }

    void setDados(double temperatura, double umidade, double vento) {
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.vento = vento;
        notificar();
    }

    void notificar() {
        for (Observador observador : observadores) {
            observador.atualizar(temperatura, umidade, vento);
        }
    }
}

class PrefeituraUberlandia implements Observador {
    public void atualizar(double temperatura, double umidade, double vento) {
        System.out.println("Prefeitura recebeu umidade: " + umidade + "%");
    }
}

class AeroportoUberlandia implements Observador {
    public void atualizar(double temperatura, double umidade, double vento) {
        System.out.println("Aeroporto recebeu vento: " + vento + " km/h");
    }
}

public class Main {
    public static void main(String[] args) {
        CET cet = new CET();

        cet.adicionarObservador(new PrefeituraUberlandia());
        cet.adicionarObservador(new AeroportoUberlandia());

        cet.setDados(30, 20, 65);
    }
}

--------------------------------------------------------------------------------------------------------

4) import java.util.*;

class AcaoBroker {
    void comprar(Acao acao, Investidor investidor) {
        System.out.println(investidor.nome + " comprou ação " + acao.nome);
    }

    void vender(Acao acao, Investidor investidor) {
        System.out.println(investidor.nome + " vendeu ação " + acao.nome);
    }
}

class Investidor {
    String nome;
    double limiteMinimo;
    double limiteMaximo;
    AcaoBroker broker;

    Investidor(String nome, double limiteMinimo, double limiteMaximo, AcaoBroker broker) {
        this.nome = nome;
        this.limiteMinimo = limiteMinimo;
        this.limiteMaximo = limiteMaximo;
        this.broker = broker;
    }

    void atualizar(Acao acao) {
        if (acao.preco >= limiteMaximo) {
            broker.vender(acao, this);
        } else if (acao.preco <= limiteMinimo) {
            broker.comprar(acao, this);
        } else {
            System.out.println(nome + " apenas observou ação " + acao.nome);
        }
    }
}

class Acao {
    String nome;
    double preco;
    List<Investidor> investidores = new ArrayList<>();

    Acao(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    void adicionarInvestidor(Investidor investidor) {
        investidores.add(investidor);
    }

    void removerInvestidor(Investidor investidor) {
        investidores.remove(investidor);
    }

    void setPreco(double preco) {
        this.preco = preco;
        notificar();
    }

    void notificar() {
        for (Investidor investidor : investidores) {
            investidor.atualizar(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        AcaoBroker broker = new AcaoBroker();

        Acao acao = new Acao("PETR4", 30);

        Investidor joao = new Investidor("João", 25, 40, broker);
        Investidor maria = new Investidor("Maria", 20, 35, broker);

        acao.adicionarInvestidor(joao);
        acao.adicionarInvestidor(maria);

        acao.setPreco(42);
        acao.setPreco(18);
        acao.setPreco(30);
    }
}