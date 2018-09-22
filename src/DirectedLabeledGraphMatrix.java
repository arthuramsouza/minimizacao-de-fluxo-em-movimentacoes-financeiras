package pucrs.alpro3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class DirectedLabeledGraphMatrix
{
	private class Vertice {
		private String item;
		
		public boolean Visitado;
		public int VisitaStatus;
		
		public Vertice(String item){
  		  this.item = item;
		}
		public String getItem() {
		  return this.item;
		}
		@SuppressWarnings("unused")
		public void setItem(String item) {
		  this.item = item;
		}
	}

	private int max;
	private int[][] matrix;
	private ArrayList<Vertice> vert;

	public DirectedLabeledGraphMatrix(int n)
	{
		if (n <= 0)
			throw new IllegalArgumentException("Numero de nodos invalido!");

		max = n;
		matrix = new int[max][max];
		vert = new ArrayList<Vertice>(max);
		// inicializacao da matriz
		for (int i = 0; i < max; i++)
			for (int j = 0; j < max; j++)
				matrix[i][j] = 0;
	}
	
	public int size() {
		return vert.size();
	}
	
	public boolean contains(String item) {
		for (Vertice v : vert)
			if (v.getItem().equals(item))
				return true;
		return false;
	}
	
	public void adjustMax() {
		if (max > vert.size()) {

			max = vert.size();
			int[][] aux = new int[max][max];
			for(int i = 0; i < vert.size(); i++) {
				for(int j = 0; j < vert.size(); j++) {
					aux[i][j] = matrix[i][j];
				}
			}
			matrix = new int[max][max];
			for(int i = 0; i < vert.size(); i++) {
				for(int j = 0; j < vert.size(); j++) {
					matrix[i][j] = aux[i][j];
				}
			}
		}
	}

	private int indexItem(String item)
	{
		int i, res = -1;
	    // percorre a lista de vertices para achar o elemento
	    for (i=0; ((i < vert.size()) && !item.equals(vert.get(i).getItem())); i++);

	    // se o indice em â€˜iâ€™ for vÃ¡lido, retorna-o
	    if (i < vert.size())
	    	res = i;

	    return res;
	}

	public void addVertice(String item)
	{
		// se tem espaco pra adicionar ainda
		if (vert.size() < max)
		{
			// se o vertice ainda nao foi adicionado, entao adiciona-o
			if (indexItem(item) == -1)
			{
				Vertice v = new Vertice(item);
				vert.add(v);				
			}
		}
		else
			throw new IllegalArgumentException("Capacidade do grafo atingida: " + max);
	}

	public void addEdge(String strOrig, String strDest, int weight)
	{
		int orig, dest;

		orig = indexItem(strOrig);
		dest = indexItem(strDest);

		if (orig == -1)
			throw new IllegalArgumentException("Aresta origem invalida: " + strOrig);
		else if (dest == -1)
			throw new IllegalArgumentException("Aresta destino invalida: " + strDest);
		else
		{
			matrix[orig][dest] = weight;
		}
	}
	
	public int getWeight(String origem, String destino) {
		return matrix[indexItem(origem)][indexItem(destino)];
	}

	private String indice2name(int i)
	{
		if (i < max)
			return vert.get(i).getItem();
		return null;
	}

	public int getNumVertices()
	{
		return max;
	}

	public int getDegree(String vertice)
	{
		int c = 0;
		int idx = indexItem(vertice);
		if (idx != -1)
		{
			// grau de saida
			for (int j = 0; j < max; j++)
				if (matrix[idx][j] != 0) c++;
			// grau de entrada
			for (int i = 0; i < max; i++)
				if (matrix[i][idx] != 0) c++;
			
		}
		return c;
	}

	public ArrayList<String> getAllAdjacents(String vertice)
	{
		ArrayList<String> res = null;
		int idx = indexItem(vertice);
		if (idx != -1)
		{
			res = new ArrayList<String>();
			for (int j = 0; j < max; j++)
				if (matrix[idx][j] != 0)
					res.add(vert.get(j).getItem());
		}
		return res;
	}

	public ArrayList<String> getSources()
	{
		ArrayList<String> res = new ArrayList<String>();
		boolean checked;

		for (int i=0; i<max; i++)
		{
			checked = false;
			for (int j=0; j<max; j++)
			{
				if (matrix[j][i] != 0)
				{
					checked = true;
					break;
				}
			}

			if (!checked)
				res.add(indice2name(i));
		}
		return res;
	}

	public ArrayList<String> getSinks()
	{
		ArrayList<String> res = new ArrayList<String>();
		boolean checked;
		for (int i=0; i<max; i++)
		{
			checked = false;
			for (int j=0; j<max; j++)
				if (matrix[i][j] != 0)
				{
					checked = true;
					break;
				}

			if (!checked)
				res.add(indice2name(i));
		}
		return res;
	}

	public void showInfo()
	{
		System.out.print("V = { ");
		for (int i = 0; i < vert.size()-1; i++)
			System.out.printf("%s, ", indice2name(i));
		System.out.printf("%s }\n", indice2name(max-1));

		ArrayList<String> arestas = new ArrayList<String>();
		for (int i = 0; i < vert.size(); i++)
			for (int j = 0; j < vert.size(); j++)
				if (matrix[i][j] != 0)
					arestas.add(String.format("(%s, %s, %d)", indice2name(i), indice2name(j), matrix[i][j]));

		System.out.print("E = {\n");
		if (!arestas.isEmpty())
		{
			System.out.printf("      %s", arestas.get(0));

			for (int i = 1; i < arestas.size(); i++)
				System.out.printf(",\n      %s", arestas.get(i));
		}
		System.out.println("\n    }");
	}

	public void showMatrix()
	{
		int i;
		System.out.printf("       ");
		for (i = 0; i < max; i++)
			System.out.printf("%5s", indice2name(i));
		for (i = 0; i < max; i++)
		{
			System.out.printf("\n%5s: ", indice2name(i));
			for (int j = 0; j < max; j++)
				System.out.printf("%5d", matrix[i][j]);
		}
		System.out.println();
	}
	
	public boolean caminho(String o, String d){
        int origem = indexItem(o);
        int destino = indexItem(d);
        
        if (origem == -1 || destino== -1) return false;
        clearVisitados();
        return caminho(origem,destino);
    }
    
    private boolean caminho(int origem, int destino) {
        if (matrix[origem][destino]!=0) return true;
        
        vert.get(origem).Visitado = true;
        for(int temp=0; temp<max; temp++) {
            // é adjacente e não visitado
            if (matrix[origem][temp]!=0  && !vert.get(temp).Visitado )  
                if (caminho(temp,destino)) return true;
        }
        
        return false;
    }
    
    private void clearVisitados() {
        for(Vertice v : vert) {
            v.Visitado = false;
            v.VisitaStatus = 0;
        }
    }
    
    public double getTaxes() {
    	double res = 0;
    	for(int i = 0; i < max; i++)
    		for(int j = 0; j < max; j++)
    			res += matrix[i][j];
    	return res/100;
    }
    
    public boolean ajustesSobrando() {
    	for (int i = 0; i < max; i++) {
    		for (int j = 0; j < max; j++) {
    			if(matrix[i][j] != 0) {
    				for (int k = 0; k < max; k++) {
    					if(matrix[j][k] != 0) {
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
    
    public void minimizar() {
    	while(ajustesSobrando()) {
    		simplificarTransferencias();
    	}
    }
    
    public void simplificarTransferencias() {
    	clearVisitados();
    	for(int i = 0; i < max; i++) {
    		vert.get(i).VisitaStatus = 1;
    		for(int j = 0; j < max; j++) {
    			if(matrix[i][j] != 0) {
     				for(int k = 0; k < max; k++) {
    					if(matrix[j][k] != 0 && vert.get(k).VisitaStatus != 1) {
    						if(matrix[i][j] >= matrix[j][k]) {
    							matrix[i][j] -= matrix[j][k];
    							matrix[i][k] += matrix[j][k];
    							matrix[j][k] = 0;
    						}
    						else {
    							matrix[j][k] -= matrix[i][j];
    							matrix[i][k] += matrix[i][j];
    							matrix[i][j] = 0;
    						}
    					}
    					else if(matrix[j][k] != 0 && vert.get(k).VisitaStatus == 1) {
    	    				if(matrix[i][j] > matrix[j][i]) {
    	    					matrix[i][j] -= matrix[j][i];
    	    					matrix[j][i] = 0;
    	    				}
    	    				else if(matrix[j][i] > matrix[i][j]) {
    	    					matrix[j][i] -= matrix[i][j];
    	    					matrix[i][j] = 0;
    	    				}
    	    				else {
    	    					matrix[i][j] = 0;
    	    					matrix[j][i] = 0;
    	    				}
    					}
    				}
    			}
    			else if(matrix[i][j] != 0 && vert.get(j).VisitaStatus == 1) {
    				if(matrix[i][j] > matrix[j][i]) {
    					matrix[i][j] -= matrix[j][i];
    					matrix[j][i] = 0;
    				}
    				else if(matrix[j][i] > matrix[i][j]) {
    					matrix[j][i] -= matrix[i][j];
    					matrix[i][j] = 0;
    				}
    				else {
    					matrix[j][i] = 0;
    				}
    			}
    		}
    		vert.get(i).VisitaStatus = 2;
    	}
    }
    
    public void createFile(String nome, String extensao) {
		Writer writer = null;

        try {
        	
            String text = "digraph{ \n";
            for (int i = 0; i < max; i++) {
            	for(int j = 0; j < max; j++) {
            		if(matrix[i][j] != 0) {
            			text = text + indice2name(i) + " -> " + indice2name(j) + " [label=\"" + matrix[i][j] + "\",weight=\"" + matrix[i][j] + "\"];\n";
            		}
            	}
            }
            text = text + "}";

            File file = new File(nome+"."+extensao);
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                System.out.println("Arquivo criado com sucesso!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
    
}
