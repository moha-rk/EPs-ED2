package ep1;

import ep1_aux.NoTreap;

public class Treap  <Chave extends Comparable<Chave>, Item> extends TS<Chave, Item>{
	
	private NoTreap<Chave, Item> raiz;

    public Treap()
    {
        raiz = null;
    }
    
    private NoTreap<Chave, Item> rotateLeft(NoTreap<Chave, Item> T){
    	NoTreap<Chave, Item> R = T.dir;
        T.dir = R.esq;
        R.esq = T;
        return R;
    }
    
    private NoTreap<Chave, Item> rotateRight(NoTreap<Chave, Item> T){
    	NoTreap<Chave, Item> L = T.esq;
        T.esq = L.dir;
        L.dir = T;
        return L;
    }
    
    

    /** Functions to insert getChave() **/
    public void insere(Chave chave, Item valor)
    {
        raiz = insere(chave, valor, raiz);
    }
    
    //MIN HEAP
    @SuppressWarnings("unchecked")
	private NoTreap<Chave, Item> insere(Chave chave, Item valor, NoTreap<Chave, Item> T)
    {
        if (T == null) {
        	N++;
        	return new NoTreap<Chave, Item>(chave, valor, null, null);
        }
        
        //O metodo compareTo retornara valor positivo caso a chave parametro seja maior que a chave do no atual
        else if (chave.compareTo(T.getChave()) < 0)
        {
        	//Funcao insere será chamada recursivamente ate atingir o nó correto para posicionar o novo nó
            T.esq = insere(chave, valor, T.esq);
            if (T.esq.getPriority() < T.getPriority())
            {
            	//Rodar a treap para a direita
                 return rotateRight(T);
             }    
        }
        else if (chave.compareTo(T.getChave()) > 0)
        {
            T.dir = insere(chave, valor, T.dir);
            if (T.dir.getPriority() < T.getPriority())
            {
            	//Rodar a treap para a esquerda
            	return rotateLeft(T);
            }
        }
        else //Se chegar aqui é porque a chave é igual
        {
        	if(this.leitura) {
	    		int num = (int) (T.getValor());
	    		num++;
	    		//Casting de int para integer, e de integer para item.
	    		//Nao é algo bom a se fazer, mas esse codigo sera apenas executado na leitura do texto
	    		//e, portanto, é garantia de que o Item sera um numero, ou a frequencia de ocorrencia da palavra
	    		T.setValor((Item)(Integer)(num));
	    	}
			else T.setValor(valor);
        }
        return T;
    }

    /** Functions to search for an element **/
    public Item devolve(Chave chave)
    {
    	NoTreap<Chave, Item> atual = raiz;
        while ((atual != null))
        {
            Chave chaveVal = atual.getChave();
            if (chave.compareTo(chaveVal) < 0)
                atual = atual.esq;
            else if (chave.compareTo(chaveVal) > 0)
                atual = atual.dir;
            else
                break;
        }
        if (atual != null)
        	return atual.getValor();
        else
        	return null;
    }

    //Mostratabela em ordem
    public void mostraTabela()
    {
        inorder(raiz);
    }
    
    /** Function for inorder traversal **/
    private void inorder(NoTreap<Chave, Item> r)
    {
        if (r != null)
        {
            inorder(r.esq);
            System.out.println(r.getChave() + ": " + r.getValor());
            inorder(r.dir);
        }
    }
    
    public void remove(Chave chave)
	{
    	NoTreap<Chave, Item> atual = raiz;
    	NoTreap<Chave, Item> anterior = atual;
    	boolean direita = false;
    	
    	//arvore vazia
    	if (atual == null) {
			return;
		}

    	while(atual != null) {
			if (chave.compareTo(atual.getChave()) < 0) {
				anterior = atual;
				atual = atual.esq;
				direita = false;
			}
			else if (chave.compareTo(atual.getChave()) > 0) {
				anterior = atual;
				atual = atual.dir;
				direita = true;
			}
			//Chegou nesse else é pq encontrou
			else
			{				
				//No tem dois filhos. Caso isso ocorra, deve-se rotacionar a arvore ate que o no tenha um e 0 filhos
				if(atual.esq != null && atual.dir != null)
				{
					while(atual.esq != null && atual.dir != null) {
						if (atual.esq.getPriority() < atual.dir.getPriority()) {
							atual = rotateLeft(atual);
							anterior = atual;
							direita = false;
							atual = atual.esq;
						}
						else {
							atual = rotateRight(atual);
							anterior = atual;
							direita = true;
							atual = atual.dir;
						}
					}
					removeAux(atual, anterior, direita);
				}else {
					removeAux(atual, anterior, direita);
				}
			return;
			}
    	}
	}

    private void removeAux(NoTreap<Chave, Item> atual, NoTreap<Chave, Item> anterior, boolean direita) {
    	
    	//No sem filhos
    	if (atual.esq == null && atual.dir == null)
		{
			if(atual == raiz) raiz = null;
			else if (direita) anterior.dir = null;
			else anterior.esq = null;
		}
		
		//No tem filho na esquerda apenas
		else if(atual.dir == null) {
			if(direita) anterior.dir = atual.esq;
			else anterior.esq = atual.esq;
			atual = null;
		}
		
		//No tem filho na direita apenas
		else if(atual.esq == null) {
			if(direita) anterior.dir = atual.dir;
			else anterior.esq = atual.dir;
			atual = null;
		}
    	
    }
    
	public int rank(Chave chave) {

		return contaRank(raiz, chave);
	
	}
	
	//Funcao copiada da implementacao da ABB, pois é o mesmo principio
	public int contaRank(NoTreap<Chave, Item> atual, Chave chave) {
		
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
	
	public int contarNos(NoTreap<Chave, Item> atual) {
		if(atual == null)  return 0;
		else return ( 1 + contarNos(atual.esq) + contarNos(atual.dir));
	}

	public Chave seleciona(int k) { //k == rank
		
		return selecionaRecursivo(raiz, k).getChave();
		
	}
	
	//Funcoa tambem copiada da implementacao da ABB
	public NoTreap<Chave, Item> selecionaRecursivo(NoTreap<Chave, Item> atual, int rank){
		
		int rankAtual = rank(atual.getChave());
		
		if(rankAtual == rank) return atual;
		else if (rankAtual > rank) {
			return selecionaRecursivo(atual.esq, rank);
		}
		else {
			return selecionaRecursivo(atual.dir, rank);
		}
		
	}

}
