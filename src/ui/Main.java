package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Queue;

import structures.Grafo;
import structures.GrafoDM;

public class Main {

	public static void main(String[] args) {
		String a="1";
		String b="2";
		String c="3";
		String d="4";
		String e="5";
		GrafoDM<String> g=new GrafoDM<>();
		g.addV(a);
		g.addV(b);
		g.addV(c);
		g.addV(d);
		g.addV(e);
		g.addA(a, b, 4);
		g.addA(b, a, 4);
		g.addA(b, c, 1);
		g.addA(c, b, 1);
		g.addA(c, d, 2);
		g.addA(d, c, 2);
		g.addA(e, c, 1);
		g.addA(c, e, 1);
		g.addA(a, e, 3);
		g.addA(e, a, 3);
		GrafoDM<String> toTest = g.prim("1");
		for(int i=1;i<6;i++) {
			GrafoDM<String>.Node node=toTest.getVert(i+"");
			while(node.getPrev()!=null) {
				System.out.print(node+" ");
				node=node.getPrev();
			}
			System.out.print("Node:"+node.get());
			System.out.println();
		}
		System.out.println(toTest.size());
		
	}
}
