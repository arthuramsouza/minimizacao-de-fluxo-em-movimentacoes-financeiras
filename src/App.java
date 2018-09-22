package pucrs.alpro3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.xml.bind.ParseConversionEvent;

public class App {

	
	public static void main(String[] args) {
		DirectedLabeledGraphMatrix grafo = readGraph("C:\\Users\\Arthur\\Documents\\workspace\\TrabalhoFinal\\src\\pucrs\\alpro3\\GrafoTeste.txt");
		System.out.println("Size: "+grafo.size());
		grafo.showInfo();
		System.out.println("Adjacentes de 1: ");
		for(String s : grafo.getAllAdjacents("1"))
			System.out.println(s);
		System.out.println("");
		System.out.println("Adjacentes de 2: ");
		for(String s : grafo.getAllAdjacents("2"))
			System.out.println(s);
		System.out.println("");
		System.out.println("Sinks: ");
		for (String s : grafo.getSinks())
			System.out.println(s);
		System.out.println("");
		System.out.println("Show Matrix: ");
		grafo.showMatrix();
		double before = grafo.getTaxes();
		grafo.createFile("grafo1", "gv");
		if(grafo.ajustesSobrando()){
			System.out.println("Sobrando");
		}
		else {
			System.out.println("Completo");
		}
		grafo.minimizar();
		if(grafo.ajustesSobrando()){
			System.out.println("Sobrando");
		}
		else {
			System.out.println("Completo");
		}
		System.out.println("");
		System.out.println("Show Matrix: ");
		grafo.showMatrix();
		System.out.println("Antes: "+before);
		System.out.println("Depois: "+grafo.getTaxes());
		System.out.println("Economia: "+(before-grafo.getTaxes()));
		System.out.println("");
		grafo.createFile("grafo2", "gv");
		
	}
	
    private static DirectedLabeledGraphMatrix readGraph(String arq) {

        DirectedLabeledGraphMatrix g = null;
        try {
            BufferedReader buff = new BufferedReader(new FileReader(arq));
            String line = null;
            try {
            	line = buff.readLine();
                line = line.trim();
                String[] first = line.split(" ");
                g = new DirectedLabeledGraphMatrix(Integer.parseInt(first[0]));
                line = buff.readLine();
               
                while (line != null) {
                    line = line.trim();
                    String[] vert = line.split(" ");
                    

                    if(!g.contains(vert[0]))
                    	g.addVertice(vert[0]);
                    if(!g.contains(vert[1]))
                    	g.addVertice(vert[1]);
                    if(!vert[0].equals(vert[1])) {
                    	if(!g.getAllAdjacents(vert[0]).contains(vert[1])) {
                    		g.addEdge(vert[0], vert[1], Integer.parseInt(vert[2]));
                    	}
                    	else {
                    		g.addEdge(vert[0], vert[1], Integer.parseInt(vert[2]) + g.getWeight(vert[0], vert[1]));
                    	}
                    }

                    line = buff.readLine();   
                }
                
                g.adjustMax();
                
            } finally {
                buff.close();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return g;
    }
    
}
