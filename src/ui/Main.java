package ui;

import java.util.Queue;

import structures.Grafo;

public class Main {

	public static void main(String[] args) {
		String a="1";
		String b="2";
		String c="3";
		String d="4";
		String e="5";
		Grafo<String> g=new Grafo<>();
		g.addVertice(a);
		g.addVertice(b);
		g.addVertice(c);
		g.addVertice(d);
		g.addVertice(e);
		g.addArista(a, b, 4);
		g.addArista(b, a, 4);
		g.addArista(b, c, 1);
		g.addArista(c, b, 1);
		g.addArista(c, d, 2);
		g.addArista(d, c, 2);
		g.addArista(e, c, 1);
		g.addArista(a, e, 3);
		g.addArista(c, e, 1);
		g.addArista(e, a, 3);
		Grafo<Grafo<String>.Node> toTest = g.prim("1");
		for(int i=1;i<6;i++) {
			Grafo<Grafo<String>.Node>.Node node=toTest.getVert(g.getVert(i+""));
			System.out.println(node.get()+" "+node.getDistance());
		}
	}
}
