package ep1;

public class VetorDesordenado<Chave extends Comparable<Chave>, Item> extends TS<Chave, Item>{

	private Chave[] chaves;
	private Item[] itens;
	private int capacity;

	@SuppressWarnings("unchecked")
	public VetorDesordenado(int capacity) { // construtor
		
		this.capacity = capacity;
		chaves = (Chave[]) new Comparable[capacity];
		itens = (Item[]) new Object[capacity];
	}
	
	
	@SuppressWarnings("unchecked")
	public void insere(Chave chave, Item valor) {
		
		//Checar se ja existe a chave no vetor
		for (int i = 0; i < N; i++) {
			if(chaves[i].compareTo(chave) == 0) {
				if(this.leitura) {
		    		int num = (int) (itens[i]);
		    		num++;
		    		//Casting de int para integer, e de integer para item.
		    		//Nao é algo bom a se fazer, mas esse codigo sera apenas executado na leitura do texto
		    		//e, portanto, é garantia de que o Item sera um numero, ou a frequencia de ocorrencia da palavra
		    		itens[i] = (Item)(Integer)(num);
		    	}
				else itens[i] = valor;
				return;
			}
		}
		//Chegou aqui é pq nao ta na tabela
		if (N >= capacity) this.redimensionar();
		
		chaves[N] = chave;
		itens[N] = valor;
		
		N++;
	}

	public Item devolve(Chave chave) {
		
		for (int i = 0; i < N; i++) {
			if(chaves[i].compareTo(chave) == 0) {
				return itens[i];
			}
		}
		
		return null;
	}

	public void remove(Chave chave) {
		
		for (int i = 0; i < N; i++) {
			if(chaves[i].compareTo(chave) == 0) {
				for(int j = i; j < N-1; j++) {
					chaves[j] = chaves[j+1];
					itens[j] = itens[j+1];
				}
				N--;
				return;
			}
		}
		//Se chegou aqui, tentou remover um item que nao esta na tabela
	}

	public int rank(Chave chave) {
		
		int rank = 0;
		int cmp;
		for (int i = 0; i < N; i++) {
			cmp = chave.compareTo(chaves[i]);
			if(cmp > 0) rank++;
		}
		
		return rank;
		
	}

	public Chave seleciona(int k) {
				
		for(int i = 0; i < N; i++) {
			if (rank(chaves[i]) == k)
				return chaves[i];
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void redimensionar() {
		//Se ativou essa funcao é porque acabou o espaço do vetor
		
		this.capacity = this.capacity*2;  //Dobrar tamanho
		Chave[] chavesaux = (Chave[]) new Comparable[capacity];
		Item[] itensaux = (Item[]) new Object[capacity];
		
		for(int i = 0; i < N; i++) {
			chavesaux[i] = this.chaves[i];
			itensaux[i] = this.itens[i];
		}
		this.chaves = chavesaux;
		this.itens = itensaux;
		
		
	}


	public void mostraTabela() {
		
		for (int i = 0; i < N; i++) {
			System.out.println(chaves[i] + ": " + itens[i]);
		}
	}


}
