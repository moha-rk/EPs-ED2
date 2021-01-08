package ep1;

import ep1_aux.NoBB;

public class ArvoreBuscaBinaria <Chave extends Comparable<Chave>, Item> extends TS<Chave, Item>{

	private NoBB<Chave, Item> raiz;
	
	public ArvoreBuscaBinaria() {
		this.raiz = null;
	}
	
	@SuppressWarnings("unchecked")
	public void insere(Chave chave, Item valor) {
		NoBB<Chave, Item> novo = new NoBB<Chave, Item>(chave, valor); // cria um novo Nó
		
		N++; //Aumenta o numero de nos
		if (raiz == null) raiz = novo;
		else  { // se nao for a raiz
			NoBB<Chave, Item> atual = raiz;
			NoBB<Chave, Item> anterior;
			while(true) {
				anterior = atual;
			    if(chave.compareTo(atual.getChave()) == 0) {
			    	
			    	if(this.leitura) {
			    		int num = (int) (atual.getValor());
			    		num++;
			    		//Casting de int para integer, e de integer para item.
			    		//Nao é algo bom a se fazer, mas esse codigo sera apenas executado na leitura do texto
			    		//e, portanto, é garantia de que o Item sera um numero, ou a frequencia de ocorrencia da palavra
			    		atual.setValor((Item)(Integer)(num));
			    	}
					else atual.setValor(valor);			    	
			    	N--; //Decrementa o no que foi incrementado caso essa chave ja estivesse na arvore
			    	return;
			    }
			    if (chave.compareTo(atual.getChave()) < 0) { // ir para esquerda
			    	atual = atual.esq;
			    	if (atual == null) {
			    		anterior.esq = novo;
			    		return;
			    	} 
			    }else { // ir para direita
			    	atual = atual.dir;
			    	if (atual == null) {
			    		anterior.dir = novo;
			    		return;
			    	}
			    } 
			} 
		}  

	}
	
	public Item devolve(Chave chave) {
		if (raiz == null) return null; // arvore vazia
	    NoBB<Chave, Item> atual = raiz;
	    while (atual.getChave().compareTo(chave) != 0) {
	    	if(chave.compareTo(atual.getChave()) < 0) atual = atual.esq; // ir para esquerda
	    	else atual = atual.dir; // ir para direita
	    	if (atual == null) return null; // encontrou uma folha -> sai
	    }
	    return atual.getValor();
	}
	
	
	public void remove(Chave chave) {
		if (raiz == null) return; // se arvore vazia

		NoBB<Chave, Item> atual = raiz;
		NoBB<Chave, Item> pai = atual;
	    boolean filho_esq = true;    //Checa se o filho atual é o filho a esquerda de seu pai

	    while (atual.getChave().compareTo(chave) != 0) {
	    	pai = atual;
	    	if(chave.compareTo(atual.getChave()) < 0) { // ir para esquerda
	    		atual = atual.esq;
	    		filho_esq = true;
	    	}
	    	else { // ir para direita
	    		atual = atual.dir; 
	    		filho_esq = false;
	    	}
	    	if (atual == null) return; // encontrou uma folha -> sai
	    }
	    
	    
	    //atual = no a ser removido
	    
	    if (atual.esq == null && atual.dir == null) { // É folha
	    	if (atual == raiz ) raiz = null;
	    	else if (filho_esq) pai.esq = null;
	    	else pai.dir = null;
	    }

	    else if (atual.dir == null) { //Tem filho só na esquerda
	    	if (atual == raiz) raiz = atual.esq;
	    	else if (filho_esq) pai.esq = atual.esq;
	    	else pai.dir = atual.esq;
	    }
	    
	    else if (atual.esq == null) { //Tem filho só na direita
	    	if (atual == raiz) raiz = atual.dir;
	    	else if (filho_esq) pai.esq = atual.dir;
	    	else pai.dir = atual.dir;
	    }

	   
	    else { //Tem dois filho
	      NoBB<Chave, Item> sucessor = noSucessor(atual);

	      if (atual == raiz) raiz = sucessor;
	      else if(filho_esq) pai.esq = sucessor;
	      else pai.dir = sucessor;
	      sucessor.esq = atual.esq; // sucessor passa a apontar (esquerda) para a esquerda do no apagado
	    }
	    
	}
	
	//Funcao que vai pegar o no que sera o sucessor do no apagado. Para a escolha desse no, pegarei
	//a folha mais a esquerda da subarvore direita do NoBB a ser apagado
	private NoBB<Chave, Item> noSucessor(NoBB<Chave, Item> apagado){
		
		NoBB<Chave, Item> pai = apagado;
		NoBB<Chave, Item> sucessor = apagado.dir; //ja começa com a subarvore direita
		
		//while ate encontrar no mais a esquerda
		while(sucessor.esq != null) {
			pai = sucessor;
			sucessor = sucessor.esq;
		}
		
		if (sucessor != apagado.dir) {
			pai.esq = sucessor.dir; // redireciona filhos do sucessor para o pai dele

			sucessor.dir = apagado.dir; // sucessor herda filhos a direita do no apagado 
		}
		
		return sucessor;
	}
	
	
	public int contarNos(NoBB<Chave, Item> atual) {
		if(atual == null)  return 0;
		else return ( 1 + contarNos(atual.esq) + contarNos(atual.dir));
	}
	
	public int rank(Chave chave) {

		return contaRank(raiz, chave);
	
	}
	
	//Voltar para conferir essa funcao, nao sei se esta correta
	public int contaRank(NoBB<Chave, Item> atual, Chave chave) {
		
		int rank = 0;
		
		if (atual == null) return rank;
		
		if(chave.compareTo(atual.getChave()) < 0) { //Se entrar aqui, devo ignorar todos os nos a direita do no atual
			rank += contaRank(atual.esq, chave);
		}
		
		else if (chave.compareTo(atual.getChave()) >= 0) {
			rank += contarNos(atual.esq) + 1;
			if (chave.compareTo(atual.getChave()) == 0) rank--;
			else rank += contaRank(atual.dir, chave); //Else porque se o no atual for igual a chave que procuro, a direita com certeza sera maior
			
		}
		
		
		return rank;
	}
	
	public Chave seleciona(int k) { //k == rank
		
		return selecionaRecursivo(raiz, k).getChave();
		
	}
	
	public NoBB<Chave, Item> selecionaRecursivo(NoBB<Chave, Item> atual, int rank){
		
		int rankAtual = rank(atual.getChave());
		
		if(rankAtual == rank) return atual;
		else if (rankAtual > rank) {
			return selecionaRecursivo(atual.esq, rank);
		}
		else {
			return selecionaRecursivo(atual.dir, rank);
		}
		
	}

	 //Mostratabela em ordem
    public void mostraTabela()
    {
        inorder(raiz);
    }
    
    /** Function for inorder traversal **/
    private void inorder(NoBB<Chave, Item> r)
    {
        if (r != null)
        {
            inorder(r.esq);
            System.out.println(r.getChave() + ": " + r.getValor());
            inorder(r.dir);
        }
    }
    
	
}
