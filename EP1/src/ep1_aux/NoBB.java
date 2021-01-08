package ep1_aux;

public class NoBB <Chave extends Comparable<Chave>, Item> {

	public NoBB<Chave, Item> esq;
	public NoBB<Chave, Item> dir;
	private Chave chave;
	private Item valor;
	
	public NoBB(Chave chave, Item valor) {
		this.chave = chave;
		this.setValor(valor);
		this.esq = null;
		this.dir = null;
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
	
	
	
	
	
	
}
