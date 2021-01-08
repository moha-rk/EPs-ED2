package ep1;

import ep1_aux.Celula;

//A hash table sera implementada apenas para String e Integer, pois caso contrario, diferentes funcoes de hash seriam necessarias
public class HashTable extends TS<String, Integer>{

	private int tamanho;
	public Celula<String, Integer> vetor[];
	
	@SuppressWarnings("unchecked")
	public HashTable(int tamanho) {
		
		this.tamanho = tamanho;
		vetor = new Celula[tamanho];
		
	}

	public void insere(String chave, Integer valor) {
		
		int indice = hashFunc(chave);
		Celula<String, Integer> proximo, atual;
		atual = vetor[indice];
		if(atual == null) {
			vetor[indice] = new Celula<String, Integer>(null, chave, valor);
			N = getN() + 1;
			return;
		} 
		//Passou daqui é colisao
		proximo = atual;
		while(proximo != null && atual.getChave().compareTo(chave) != 0) {
			atual = proximo;
			proximo = atual.getProxima();
		}
		if(atual.getChave().compareTo(chave) != 0) {
			atual.setProxima(new Celula<String, Integer>(null, chave, valor));
			N = getN() + 1;
		}
		
		else {  //Se entrar aqui, atual contem a mesma chave
			if(this.leitura) {
		   		int num = (int) (atual.getValor());
		   		num++;
		   		atual.setValor((Integer)(num));
		   	}
			else atual.setValor(valor);
		}
	}
	
	private int hashFunc(String chave) {
		int g = 31; //Primeiro n primo depois de 27
		int hash = 0;
		
		for(int i = 0; i < chave.length(); i++) {
			hash = g*hash + chave.charAt(i);
		}
		if(hash < 0) hash = hash*(-1);
		
		return hash%tamanho;
	}

	public Integer devolve(String chave) {
		
		int indice = hashFunc(chave);
		
		Celula<String, Integer> atual;
		atual = vetor[indice];
		
		while(atual != null && atual.getChave().compareTo(chave) != 0) {
			atual = atual.getProxima();
		}
		if(atual == null) return null;
		return atual.getValor();
	}

	public void remove(String chave) {
		
		int indice = hashFunc(chave);
		
		Celula<String, Integer> atual, anterior;
		atual = vetor[indice];
		anterior = atual;
		
		while(atual != null && atual.getChave().compareTo(chave) != 0) {
			anterior = atual;
			atual = atual.getProxima();
		}
		
		if(atual == null) return; //Nao encontrou
		
		if(atual == anterior) { //Se sao iguais, é o primeiro elemento
			vetor[indice] = null;
			return;
		}
		
		anterior.setProxima(atual.getProxima());
		
	}

	public int rank(String chave) {
		
		int rank = 0;
		Celula<String, Integer> atual;
		
		for(int i = 0; i < tamanho; i++) {
			if(vetor[i] != null) {
				atual = vetor[i];
				while(atual != null) {
					if(atual.getChave().compareTo(chave) < 0)
						rank++;
					atual = atual.getProxima();
				}
			}
		}
		
		
		return rank;
	}

	public String seleciona(int k) {

		int rankAtual;
		Celula<String, Integer> atual;
		for(int i = 0; i < tamanho; i++) {
			if(vetor[i] != null) {
				atual = vetor[i];
				while(atual != null) {
					rankAtual = rank(atual.getChave());
					if(rankAtual == k) return atual.getChave();
					atual = atual.getProxima();
				}
			}
		}
		
		
		return null;
	}

	public void mostraTabela() {
		Celula<String, Integer> atual;
		for(int i = 0; i < tamanho; i++) {
			if(vetor[i] != null) {
				atual = vetor[i];
				while(atual != null) {
					System.out.println(atual.getChave() + ": " + atual.getValor());
					atual = atual.getProxima();
				}
			}
		}
	}
}