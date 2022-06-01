package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Queue;
import org.junit.jupiter.api.Test;

import structures.Grafo;
import structures.GrafoDM;

class GrafoDMTest {
	public GrafoDM<String> g;

	void scenario1() {
		g=new GrafoDM<>();
	}
	
	void scenario2() {
		String a="1";
		String b="2";
		String c="3";
		String d="4";
		String e="5";
		
		g=new GrafoDM<>();
		g.addV(a);
		g.addV(b);
		g.addV(c);
		g.addV(d);
		g.addV(e);
	}
	
	void scenario3() {
		String a="1";
		String b="2";
		String c="3";
		String d="4";
		String e="5";
		g=new GrafoDM<>();
		g.addV(a);
		g.addV(b);
		g.addV(c);
		g.addV(d);
		g.addV(e);
		g.addA(a, b);
		g.addA(b, c);
		g.addA(b, a);
	}


	@Test
	void testRemoveAEE() {
		scenario3();
		assertTrue(g.removeA("1", "2"));
		assertFalse(g.removeA("1", "2"));
		assertFalse(g.isArista("1", "2"));
		assertTrue(g.isArista("2", "1"));
		assertTrue(g.isArista("2", "3"));
	}


	@Test
	void testRemoveAEEInt() {
		scenario1();
		String a="1";
		String b="2";
		String c="3";
		g.addV(a);
		g.addV(b);
		g.addV(c);
		g.addA(b, c,2);
		g.addA(b, c,1);
		g.addA(a, c,1);
		g.addA(c, b,2);
		assertTrue(g.aristasAmount()==4);
		assertTrue(g.isArista(b, c,2));
		assertTrue(g.isArista(b, c,1));
		assertTrue(g.isArista(a, c,1));
		assertTrue(g.isArista(c, b,2));
		assertTrue(g.removeA(b, c, 2));
		assertTrue(g.isArista(b, c,1));
		assertFalse(g.isArista(b, c,2));
		assertTrue(g.aristasAmount()==3);
		assertTrue(g.removeA(b, c,1));
		assertTrue(g.removeA(a, c,1));
		assertTrue(g.removeA(c, b, 2));
		assertTrue(g.aristasAmount()==0);
	}

	@Test
	void testRemoveV() {
		scenario3();
		assertTrue(g.removeV("1"));
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
	void testDFS() {
		scenario3();
		Queue<String> toTest=g.BFS("1");
		assertEquals(toTest.size(),3);
	}

	@Test
	void testDijkstra() {
		scenario1();
		String a="1";
		String b="2";
		String c="3";
		String d="4";
		String e="5";
		g.addV(a);
		g.addV(b);
		g.addV(c);
		g.addV(d);
		g.addV(e);
		g.addA(a, b);
		g.addA(b, c);
		g.addA(c, d);
		g.addA(d, e);
		ArrayList<String>expectedOut=new ArrayList<>();
		expectedOut.add("1");
		expectedOut.add("2");
		expectedOut.add("3");
		expectedOut.add("4");
		expectedOut.add("5");
		Queue<GrafoDM<String>.Node> nodes=g.dijkstra(a);
		ArrayList<String>toTest=new ArrayList<>();
		GrafoDM<String>.Node found=null;
		for(GrafoDM<String>.Node node:nodes) {
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
				node=node.getPrev();
			}
			assertEquals("1",node.get());
		}
		assertEquals(5,toTest.size());
	}

}
