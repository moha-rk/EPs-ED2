package ep1;

import ep1_aux.Celula;

public class ListaLigadaDesordenada <Chave extends Comparable<Chave>, Item> extends TS<Chave, Item>{

	private Celula<Chave, Item> primeira;
	  
	private Celula<Chave, Item> ultima;
	
	@SuppressWarnings("unchecked")
	public void insere(Chave chave, Item valor) {
		
		//Checar se ja existe a chave no vetor
		
		Celula<Chave, Item> atual = primeira;
		for (int i = 0; i < N; i++) {
			if(atual.getChave().compareTo(chave) == 0) {
				if(this.leitura) {
		    		int num = (int) (atual.getValor());
		    		num++;
		    		//Casting de int para integer, e de integer para item.
		    		//Nao é algo bom a se fazer, mas esse codigo sera apenas executado na leitura do texto
		    		//e, portanto, é garantia de que o Item sera um numero, ou a frequencia de ocorrencia da palavra
		    		atual.setValor((Item)(Integer)(num));
		    	}
				else atual.setValor(valor);
				return;
			}
			atual = atual.getProxima();
		}
		//Chegou aqui é pq nao ta na tabela
		Celula<Chave, Item> nova = new Celula<Chave, Item>(null, chave, valor);
		
		if (this.N == 0) {
			this.primeira = nova;
			this.ultima = this.primeira;
		
		}else{ 
			this.ultima.setProxima(nova);
			this.ultima = nova;
		}
		this.N++;
	}

	public Item devolve(Chave chave) {
		
		Celula<Chave, Item> atual = primeira;
		
		for(int i = 0; i < N; i++) {
			if(atual.getChave().compareTo(chave) == 0) {
				return atual.getValor();
			}
			atual = atual.getProxima();
		}
		
		return null;
	}

	public void remove(Chave chave) {

		Celula<Chave, Item> atual = primeira;
		Celula<Chave, Item> anterior = atual;
		
		for(int i = 0; i < N; i++) {
			if(atual.getChave().compareTo(chave) == 0) {
				N--;
				if(i == 0) {
					primeira = atual.getProxima();
					return;
				}
				anterior.setProxima(atual.getProxima());
				if(atual.getProxima() == null) {
					ultima = anterior;				
				}
				return;
			}
			anterior = atual;
			atual = atual.getProxima();
		}
	}

	public int rank(Chave chave) {
		
		Celula<Chave, Item> atual = primeira;
		int rank = 0;
		int cmp;
		for (int i = 0; i < N; i++) {
			cmp = chave.compareTo(atual.getChave());
			if(cmp > 0) rank++;
			atual = atual.getProxima();
		}
		
		return rank;
		
	}

	public Chave seleciona(int k) {
	
		Celula<Chave, Item> atual = primeira;
		for(int i = 0; i < N; i++) {
			if (rank(atual.getChave()) == k)
				return atual.getChave();
			atual = atual.getProxima();
		}
		return null;
		
	}

	public void mostraTabela() {

		Celula<Chave, Item> atual = primeira;
		for(int i = 0; i < N; i++) {
			System.out.println(atual.getChave() + ": " + atual.getValor());
			atual = atual.getProxima();
		}
	}
	
}
