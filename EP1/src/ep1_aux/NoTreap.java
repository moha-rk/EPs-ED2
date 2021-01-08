package ep1_aux;

import java.util.Random;

public class NoTreap <Chave, Item>{

	public NoTreap<Chave, Item> esq;
	public NoTreap<Chave, Item> dir;
    private int priority;
    private Chave chave;
    private Item valor;    

    public NoTreap(Chave chave, Item valor, NoTreap<Chave, Item> esq, NoTreap<Chave, Item> dir)
    {
        this.chave = chave;
        this.valor = valor;
        this.esq = esq;
        this.dir = dir;
        this.priority = new Random().nextInt( );
    }

	public Chave getChave() {
		return chave;
	}

	public Item getValor() {
		return valor;
	}

	public void setValor(Item valor) {
		this.valor = valor;
	}

	public int getPriority() {
		return priority;
	}
	
}
