package ep1;

public class VetorOrdenado<Chave extends Comparable<Chave>, Item> extends TS<Chave, Item>{

	private Chave[] chaves;
	private Item[] itens;
	private int capacity;

	@SuppressWarnings("unchecked")
	public VetorOrdenado(int capacity) { // construtor
		
		this.capacity = capacity;
		chaves = (Chave[]) new Comparable[capacity];
		itens = (Item[]) new Object[capacity];
	}
	
	
	@SuppressWarnings("unchecked")
	public void insere(Chave chave, Item valor) {
		
		int i = rank(chave);
	    if (i < N && chaves[i].compareTo(chave) == 0 && N < capacity) { 
	    	// acerto de busca
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
	    else if (N >= capacity) this.redimensionar();
	    
	    // falha de busca
	    for (int j = N; j > i; j--) { 
	    	chaves[j] = chaves[j-1]; 
	        itens[j] = itens[j-1]; 
	    }
	    chaves[i] = chave; 
	    itens[i] = valor;
	    N++;
	}

	public Item devolve(Chave chave) {
		
		int i = rank(chave);
		if (i < N && chaves[i].compareTo(chave) == 0) 
	    	return itens[i];
	    else return null;
	}

	public void remove(Chave chave) {
		int i = rank(chave);
		while(i < N-1) {
			chaves[i] = chaves[i+1]; 
	        itens[i] = itens[i+1];
	        i++;
		}
		N--;
	}

	public int rank(Chave chave) {
		
		//a é lado esquerdo da busca binaria, b é o lado direito
		int a = 0;
		int b = N-1;
	      while (a <= b) {
	         int mid = a + (b - a) / 2;
	         int cmp = chave.compareTo(chaves[mid]);
	         if (cmp < 0) b = mid - 1;
	         else if (cmp > 0) a = mid + 1;
	         else return mid;
	      }
	      return a;
	}

	public Chave seleciona(int k) {
		
		if (k < N) {
			return chaves[k];
		}
		//Null caso o rank seja maior que Numero de itens da tabela
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
