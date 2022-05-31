package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Queue;

import org.junit.jupiter.api.Test;

import structures.Grafo;
import structures.Grafo.Node;

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
	
	void scenario4() {
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
		g.addArista(a, b, 4);
		g.addArista(b, c, 1);
		g.addArista(c, d, 2);
		g.addArista(e, c, 1);
		g.addArista(a, e, 3);
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
	void testDijkstra() {
		scenario4();
		Queue<Grafo<String>.Node> toTest = g.dijkstra("1");
		assertEquals(toTest.size(),5);
	}

}
