package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Queue;

import org.junit.jupiter.api.Test;

import structures.Grafo;

class GrafoTest {
	static Grafo<String> g;
	
	void scenario1() {
		g=new Grafo<>();
	}
	
	void scenario2() {
		String a="1";
		String b="2";
		String c="3";
		String d="4";
		String e="5";
		
		g=new Grafo<>();
		g.addVertice(a);
		g.addVertice(b);
		g.addVertice(c);
		g.addVertice(d);
		g.addVertice(e);
	}
	
	void scenario3() {
		String a="1";
		String b="2";
		String c="3";
		String d="4";
		String e="5";
		g=new Grafo<>();
		g.addVertice(a);
		g.addVertice(b);
		g.addVertice(c);
		g.addVertice(d);
		g.addVertice(e);
		g.addArista(a, b);
		g.addArista(b, c);
		g.addArista(b, a);
	}
	
	

	@Test
	void testAddVertice() {
		String a="1";
		String b="2";
		String c="3";
		String d="4";
		String e="5";
		scenario1();
		g.addVertice(a);
		g.addVertice(b);
		g.addVertice(c);
		g.addVertice(d);
		g.addVertice(e);
		assertTrue(g.verticesAmount()==5);
		assertTrue(g.isVert(a));
		assertTrue(g.isVert(b));
		assertTrue(g.isVert(c));
		assertTrue(g.isVert(d));
		assertTrue(g.isVert(e));
	}

	@Test
	void testAristasSize() {
		scenario2();
		assertTrue(g.aristasAmount()==0);
		scenario3();
		assertTrue(g.aristasAmount()==3);
	}

	@Test
	void testVerticesAmount() {
		scenario2();
		assertTrue(g.verticesAmount()==5);
	}

	@Test
	void testIsVert() {
		scenario3();
		assertTrue(g.isVert("1"));
		assertFalse(g.isVert("6"));
	}

	@Test
	void testIsArista() {
		scenario3();
		assertTrue(g.isArista("1","2"));
		assertTrue(g.isArista("2","1"));
		assertTrue(g.isArista("2","3"));
		assertFalse(g.isArista("3","2"));
		g.addArista("3", "1", 2);
		assertTrue(g.isArista("3","1",2));
	}

	@Test
	void testDeleteAEE() {
		scenario3();
		assertTrue(g.deleteA("1", "2"));
		assertFalse(g.deleteA("1", "2"));
		assertFalse(g.isArista("1", "2"));
		assertTrue(g.isArista("2", "1"));
		assertTrue(g.isArista("2", "3"));
	}

	@Test
	void testAEEInt() {
		scenario1();
		String a="1";
		String b="2";
		String c="3";
		g.addVertice(a);
		g.addVertice(b);
		g.addVertice(c);
		g.addArista(b, c,2);
		g.addArista(b, c,1);
		g.addArista(a, c,1);
		g.addArista(c, b,2);
		assertTrue(g.aristasAmount()==4);
		assertTrue(g.isArista(b, c,2));
		assertTrue(g.isArista(b, c,1));
		assertTrue(g.isArista(a, c,1));
		assertTrue(g.isArista(c, b,2));
		assertTrue(g.deleteA(b, c, 2));
		assertTrue(g.isArista(b, c,1));
		assertFalse(g.isArista(b, c,2));
		assertTrue(g.aristasAmount()==3);
		assertTrue(g.deleteA(b, c,1));
		assertTrue(g.deleteA(a, c,1));
		assertTrue(g.deleteA(c, b, 2));
		assertTrue(g.aristasAmount()==0);
	}

	@Test
	void testDeleteV() {
		scenario3();
		assertTrue(g.deleteV("1"));
		assertFalse(g.isArista("1", "2"));
		assertFalse(g.isArista("2","1"));
		assertFalse(g.isVert("1"));
		assertEquals(4,g.verticesAmount());
		assertEquals(1,g.aristasAmount());
	}

	@Test
	void testBFS() {
		scenario3();
		Queue<String> toTest=g.BFS("1");
		assertEquals(toTest.size(),3);
	}
	@Test
	void testDijstre() {
		scenario1();
		String a="1";
		String b="2";
		String c="3";
		String d="4";
		String e="5";
		g.addVertice(a);
		g.addVertice(b);
		g.addVertice(c);
		g.addVertice(d);
		g.addVertice(e);
		g.addArista(a, b);
		g.addArista(b, c);
		g.addArista(c, d);
		g.addArista(d, e);
		ArrayList<String>expectedOut=new ArrayList<>();
		expectedOut.add("1");
		expectedOut.add("2");
		expectedOut.add("3");
		expectedOut.add("4");
		expectedOut.add("5");
		Queue<Grafo<String>.Node> nodes=g.dijkstra(a);
		ArrayList<String>toTest=new ArrayList<>();
		Grafo<String>.Node found=null;
		for(Grafo<String>.Node node:nodes) {
			if(node.get().equals(e)) {
				found=node;
				break;
			}
		}
		while(found.getPrev()!=null) {
			toTest.add(0,found.get());
			found=found.getPrev();
		}
		toTest.add(0,found.get());
		for(int i=0;i<toTest.size();i++) {
			assertEquals(expectedOut.get(i),toTest.get(i));
		}
	}
	
	@Test
	void testPrim() {
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
		g.addArista(c, e, 1);
		g.addArista(a, e, 3);
		g.addArista(e, a, 3);
		Grafo<String> toTest = g.prim("1");
		for(int i=1;i<6;i++) {
			Grafo<String>.Node node=toTest.getVert(i+"");
			while(node.getPrev()!=null) {
				node=node.getPrev();
			}
			assertEquals("1",node.get());
		}
		assertEquals(5,toTest.size());
	}

}
