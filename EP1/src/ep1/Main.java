package ep1;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
	
	static int nPalavras;

	public static void main(String[] args) throws IOException {
		
		
		if (args.length < 2) {
			mostreUso();
		}
			
		//Inicializando variaveis
		char c;
		int aux;
		TS<String, Integer> ts;
		String tipo = args[args.length - 1];
		String palavra = "";
		String titulo = "";
		
		for(int i = 0; i < args.length-1; i++) {
			titulo += args[i];
			if(i != args.length - 2) titulo += " ";
		}
		
		//Inicializando a TS
		if(tipo.equals("VD")) {
			ts = new VetorDesordenado<String, Integer>(3000);  //Usando capacidade maxima inicial 3000
		}else if(tipo.equals("VO")) {
			ts = new VetorOrdenado<String, Integer>(3000);  //Usando capacidade maxima inicial 3000
		}else if(tipo.equals("LD")) {
			ts = new ListaLigadaDesordenada<String, Integer>();
		}else if(tipo.equals("LO")) {
			ts = new ListaLigadaOrdenada<String, Integer>();
		}else if(tipo.equals("AB")) {
			ts = new ArvoreBuscaBinaria<String, Integer>();
		}else if(tipo.equals("TR")) {
			ts = new Treap<String, Integer>();
		}else if (tipo.equals("HS")) {
			ts = new HashTable(4000); //Tamanho maximo vetor
		}else {
			System.out.print("A estrutura" + tipo + "não é válida");
			System.exit(1);
			return;
		}
		
		//Funcionando para arquivos no mesmo diretorio do .jar
		//Abrindo arquivo
		String path = "./" + titulo;
		BufferedReader in;
		FileInputStream fis;
		
		File f = new File(path);
		if (f.isFile() && f.canRead()) {
			fis = new FileInputStream(path);
			in = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		}
		else {
			System.out.println("ERRO: arquivo " + args[0] + " nao pode ser aberto.");
			System.exit(1);
			return;
		}
		
		long timer = System.currentTimeMillis();
		double elapsed;
		
		
		System.out.println("criando ST...");
		nPalavras = 0;
		
		while((aux = in.read()) != -1) {
				//Hifen			//Letras maiusculas			//Letras minusculas			//Todas as letras contadas neste intervalo sao letras com acentos
			if((aux == 45) || (aux >= 65 && aux <= 90) || (aux >= 97 && aux <= 122) || (aux >= 192 && aux <= 252 && aux != 247 && aux != 215)) {
				c = (char) aux;
				palavra += c;
			}else {
				//Fim da palavra
				if(!palavra.equals("") && palavra.charAt(0) != 45 && palavra.charAt(palavra.length()-1) != 45) {
					palavra = palavra.toLowerCase();
					//Adicionar ela na TS
					ts.insere(palavra, 1);
					nPalavras++;
				}
				palavra = ""; //Reiniciar a palavra
			}
				
		}
		//Caso o texto termine com uma letra, essa letra ou palavra a qual a letra pertence seria perdida
		if(!palavra.equals("")) {
			palavra = palavra.toLowerCase();
			//Adicionar ela na TS
			ts.insere(palavra, 1);
		}
		
		in.close(); //Fim do texto, fecha o arquivo
		
		timer = System.currentTimeMillis() - timer;
		elapsed = (double)timer/1000;
		
        System.out.println("Arquivo lido e ST construida em " + elapsed + " segundos.");
        System.out.println("O texto continha " + nPalavras + " palavras, sendo " + ts.getN() + " delas diferentes.");
        ts.leitura = false; //Desliga a funcao de leitura na ts, sendo possivel substituir chaves
        					//com qualquer que seja o valor desejado
		
        testeOperacoes(ts);
        
        System.exit(0);
		
	}
	
	/*-------------------------------------------------------*/
	/* 
	 *  TESTEOPEARCOES(ST) 
	 *
	 *  RECEBE uma tabela de símbolos ST e testa várias operações
	 *  interativamente.
	 *
	 *  A função supõe que as chaves são do tipo String e os
	 *  valores são do tipo Integer (ver util.h).
	 */
	
	private static void testeOperacoes(TS<String, Integer> ts)
	{
	    String linha = null;
	    boolean clock = true;
	    long timer = 0;
	    double elapsed;
	    
	    /* mostre uso */
	    System.out.println("Possiveis operacoes do teste interativo:");
	    System.out.println("minST, delminST, showST, getST <chave>; rankST <chave>, deleteST <chave>, selectST <int>");
	    System.out.println("Para exibir/esconder o tempo de execução de cada operação, digite 'toggle clock' (sem aspas)");
	    System.out.println("exit para encerrar.");
	    System.out.print(">>> ");
	    
	    Scanner in = new Scanner(System.in);
	    
	    while (!(linha = in.next()).equals("exit")) {
	        /* pegue operacao a ser testada */
	        String operacao = linha;
	        if (operacao.equals(null)) {
	        	System.err.println("ERROR: operacao esperada.");
	        }
	        else if (operacao.equals("toggle") && in.next().equals("clock")) {
	        	clock = !clock;
	        }
	        /*---------------------------------*/
	        else if (operacao.equals("minST")) {
	        	
	        	if(clock) timer = System.nanoTime();
	        	
	            String key = ts.seleciona(0);
	            if (key == null) {
	                System.out.println("ST vazia");
	            } else {
	                System.out.println(key);
	            }
	            
	            if(clock) {
	            	timer = System.nanoTime() - timer;
	            	elapsed = (double)timer/1000000;
	            	System.out.println("Operação executada em " + elapsed + " milissegundos");
	            }
	        }
	        /*---------------------------------*/
	        else if (operacao.equals("delminST")) {
	        	
	        	if(clock) timer = System.nanoTime();
	        	
	            String key = ts.seleciona(0);
	            if (key == null) {
	                System.out.println("ST já está vazia");
	            } else {
	                System.out.print("\"" + key);
	                ts.remove(key);
	                System.out.println("\" foi removida.");
	            }
	            
	            if(clock) {
	            	timer = System.nanoTime() - timer;
	            	elapsed = (double)timer/1000000;
	            	System.out.println("Operação executada em " + elapsed + " milissegundos");
	            }
	        }
	        /*---------------------------------*/
	        else if (operacao.equals("showST")) {
	        	ts.mostraTabela();
	        }
	        /*---------------------------------*/
	        else {
	            /* operacao necessita de argumento key */
	            String key = in.next();
	            if (key == null) {
	            	System.err.println("ERROR:operacao necessita uma palavra");
	            } else {
	                /*---------------------------------*/
	                if (operacao.equals("getST")) {
	                	
	                	if(clock) timer = System.nanoTime();
	                	
	                    Integer frequencia = null;
	                    frequencia = ts.devolve(key); /* consulte a ST */
	                    /* mostre o resultado da consulta */
	                    if (frequencia == null) {
	                        System.out.println(key + ": 0\n");
	                    } else {
	                        System.out.println(key + ": " + frequencia);
	                    }
	                    
	                    if(clock) {
	    	            	timer = System.nanoTime() - timer;
	    	            	elapsed = (double)timer/1000000;
	    	            	System.out.println("Operação executada em " + elapsed + " milissegundos");
	                    }
	                }
	                /*---------------------------------*/
	                else if (operacao.equals("rankST")) {
	                	
	                	if(clock) timer = System.nanoTime();
	                	
	                    int r = ts.rank(key);
	                    System.out.println(r);
	                    
	                    if(clock) {
	    	            	timer = System.nanoTime() - timer;
	    	            	elapsed = (double)timer/1000000;
	    	            	System.out.println("Operação executada em " + elapsed + " milissegundos");
	                    }
	                }
	                /*---------------------------------*/
	                else if (operacao.equals("deleteST")) {
	                	
	                	if(clock) timer = System.nanoTime();
	                	
	                    ts.remove(key);
	                    System.out.println("\"" + key + "\" foi removida.");
	                    
	                    if(clock) {
	    	            	timer = System.nanoTime() - timer;
	    	            	elapsed = (double)timer/1000000;
	    	            	System.out.println("Operação executada em " + elapsed + " milissegundos");
	    	            }
	                }
	                /*---------------------------------*/
	                else if (operacao.equals("selectST")) {
	                	
	                	if(clock) timer = System.nanoTime();
	                	
	                    int pos = Integer.parseInt(key);
	                    String chave = ts.seleciona(pos);
	                    System.out.println("Posição " + key + " = " + chave);
	                    
	                    if(clock) {
	    	            	timer = System.nanoTime() - timer;
	    	            	elapsed = (double)timer/1000000;
	    	            	System.out.println("Operação executada em " + elapsed + " milissegundos");
	    	            }
	                }
	                /*---------------------------------*/
	                else {
	                    System.err.println("ERROR: operacao nao reconhecida");
	                }
	            }
	        }
	        System.out.print(">>> ");
	    }
	    in.close();
	}
	
	/*-----------------------------------------------------------*/
	/* 
	 *  I M P L E M E N T A Ç Ã O   D A S   F U N Ç Õ E S
	 *                 A U X I L I A R E S 
	 */
	
	
	private static void mostreUso() {
		//Pegando o nome do programa dessa maneira pois nao consigo acessar pela linha de comando, como no C
		String nomePrograma = new java.io.File(Main.class.getProtectionDomain()
				  .getCodeSource().getLocation().getPath()).getName();
		System.out.print("Uso \n" +
						">>  java -jar " + nomePrograma + " nome-arquivo tipo-tabela\n" +
	    				"    nome-arquivo = nome do arquivo com o texto\n" +
	    				"    tipo-tabela  = sigla de estrutura que deve ser usada\n" +
	    				"    tipos de tabela: VD, VO, LD, LO, AB, TR, HS.\n");
		System.exit(1);

	}
	
	
	
}
