package ep1;

//Classe pai tabela de simbolos
//A principio, Item sera String e Chave sera int
public abstract class TS <Chave, Item>{

	protected int N = 0;
	
	public boolean leitura = true;
	
	public abstract void insere (Chave chave, Item valor);
	
	public abstract Item devolve (Chave chave);
	
	public abstract void remove (Chave chave);
	
	public abstract int rank (Chave chave);
	
	public abstract Chave seleciona(int k);
	
	public abstract void mostraTabela();

	public int getN() {
		return N;
	}

}
