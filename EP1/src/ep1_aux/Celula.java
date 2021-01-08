package ep1_aux;

public class Celula <Chave extends Comparable<Chave>, Item>{

	private Celula<Chave, Item> proxima;	  
	private Chave chave;
	private Item valor;

	public Celula(Celula<Chave, Item> proxima, Chave chave, Item valor) {
		this.proxima = proxima;
	    this.chave = chave;
	    this.valor = valor;
	}

	public void setProxima(Celula<Chave, Item> proxima) {
	    this.proxima = proxima;
	}

	public Celula<Chave, Item> getProxima() {
	    return proxima;
	}

	public Chave getChave() {
		return chave;
	}
	
	//Sem necessidade para um setChave, ja que a chave nao muda, apenas o valor

	public Item getValor() {
		return valor;
	}

	public void setValor(Item valor) {
		this.valor = valor;
	}
	  
}