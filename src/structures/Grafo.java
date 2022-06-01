package structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Grafo<E> {
	private Hashtable<E,Node> nodes;
	private int amountVertices;
	private int amountAristas;
	public Grafo() {
		nodes=new Hashtable<>();
		amountVertices=0;
		amountAristas=0;
	}
	
	public int size() {
		return nodes.size();
	}
	
	public void addVertice(E element) {
		nodes.put(element, new Node(element));
		amountVertices++;
	}
	
	public Set<E> getVertices() {
		return nodes.keySet();
	}
	
	public int aristasAmount() {
		return amountAristas;
	}
	
	public int verticesAmount() {
		return amountVertices;
	}
	
	public boolean isVert(E e1) {
		return nodes.get(e1)!=null;
	}
	
	public Node getVert(E e1) {
		return nodes.get(e1);
	}
	
	public boolean isArista(E n1,E n2) {
		Node node1=nodes.get(n1);
		Node node2=nodes.get(n2);
		if(node1!=null&&node2!=null) {
			List<Arista> l1=node1.getAd();
			Arista ar=new Arista(node1,node2);
			if(l1!=null) {
				return l1.contains(ar);
			}
		}
		return false;
	}
	
	public boolean isArista(E n1,E n2,int w) {
		Node node1=nodes.get(n1);
		Node node2=nodes.get(n2);
		if(node1!=null&&node2!=null) {
			List<Arista> l1=node1.getAd();
			Arista ar=new Arista(node1,node2,w);
			if(l1!=null) {
				return l1.contains(ar);
			}
		}
		return false;
	}
	
	public List<E> getAdyacentes(E element){
		List<Arista> list=nodes.get(element).getAd();
		List<E> out=new ArrayList<>();
		for(Arista i:list) {
			Node b=i.getB();
			out.add(b.get());
		}
		return out;
	}
	
	public boolean addArista(E element1, E element2) {
		Node node1=nodes.get(element1);
		if(node1!=null) {
			Node node2=nodes.get(element2);
			if(node2!=null) {
				Arista arista=new Arista(node1,node2);
				node1.addAdyacente(arista);
				
				amountAristas++;
				return true;
			}else return false;
		}else return false;
	}
	
	
	
	public boolean deleteA(E e1,E e2) {
		Node n1=nodes.get(e1);
		Node n2=nodes.get(e2);
		if(n1!=null&&n2!=null) {
			Arista ar=new Arista(n1,n2);
			List<Arista>l1= n1.getAd();
			amountAristas--;
			return l1.remove(ar);
		}
		return false;
	}
	
	public boolean deleteA(E e1,E e2,int w) {
		Node n1=nodes.get(e1);
		Node n2=nodes.get(e2);
		if(n1!=null&&n2!=null) {
			Arista ar=new Arista(n1,n2,w);
			List<Arista>l1= n1.getAd();
			if(l1.remove(ar)) {
				amountAristas--;
				return true;
			}
		}
		return false;
	}
	
	public boolean deleteV(E e1) {
		Node n1=nodes.get(e1);
		if(n1!=null) {
			nodes.remove(e1);
			amountAristas-=n1.getAd().size();
			Collection<Node> nodes=this.nodes.values();
			for(Node n:nodes) {
				List<Arista>l=n.getAd();
				if(l!=null) {
					for(int i=0;i<l.size();i++) {
						Arista a=l.get(i);
						if(a.getA()==n1||a.getB()==n1) {
							l.remove(a);
							amountAristas--;
						}
					}
				}
				
			}
			amountVertices--;
			return true;
		}
		return false;
	}
	
	
	
	public boolean addArista(E element1, E element2,int w) {
		Node node1=nodes.get(element1);
		Node node2=nodes.get(element2);
		if(node1!=null&&node2!=null) {
			
			Arista arista=new Arista(node1,node2,w);
			node1.addAdyacente(arista);
			amountAristas++;
			return true;
		}else return false;
	}
	
	
	public Queue<E> BFS(E s)
    {
		Collection<Node> a=nodes.values();
        for(Node i:a) {
        	i.setColor(Color.WHITE);
        	i.setDistance(0);
        	i.setPrev(null);
        }
		Node root=nodes.get(s);
        LinkedList<Node> queue = new LinkedList<>();
        Queue<E> out = new LinkedList<>();
        root.setColor(Color.BLACK);
        root.setDistance(0);
        queue.add(root);
        while (queue.size() != 0)
        {
            root = queue.poll();
            out.add(root.get());
            List<Arista> list=root.getAd();
            if(list!=null) {	
            	for(Arista i:list) {
            		Node n=i.getB();
            		if(n.getColor()!=Color.BLACK) {
            			
            			 n.setColor(Color.BLACK);
                         queue.add(n);
            		}
            	}
            }
        }
        return out;
    }
	
	public Grafo<E> prim(E s) {
		Node root=nodes.get(s);
		if(root!=null) {
			for(Node node:nodes.values()) {
				node.setColor(Color.WHITE);
				node.setDistance(0);
				node.setPrev(null);
			}
			Grafo<E>g=new Grafo<>();
			g.addVertice(root.get());
			Set<E> keys=nodes.keySet();
			for(E ar:keys) {
				g.addVertice(nodes.get(ar).get());
			}
			Comp comp=new Comp();
			PriorityQueue<Arista>prQA=new PriorityQueue<>(comp);
			int i=1;
			while(i<amountVertices) {
				List<Arista>aristas=root.getAd();
				for(Arista ar:aristas) {
					Node node=ar.getB();
					if(node.getColor()!=Color.BLACK) {
						prQA.add(ar);
					}
				}
				root.setColor(Color.BLACK);
				Arista ar=prQA.poll();
				if(ar!=null) {
					Node node=ar.getB();
					if(node.getColor()!=Color.BLACK) {
						node.setColor(Color.BLACK);
						g.addArista(ar.getA().get(), ar.getB().get(), ar.getW());
						g.addArista(ar.getB().get(), ar.getA().get(), ar.getW());
						Node a=g.getVert(ar.getA().get());
						Node b=g.getVert(ar.getB().get());
						b.setDistance(a.getDistance()+ar.getW());
						b.setPrev(a);
						i++;
						root=node;
					}
				}
				
			}
			return g;
		}
		return null;
		
	}
	
	public Queue<Node> DFS(E s) {
		Set<E>setNodes=nodes.keySet();
		for(E key:setNodes) {
			Node node=nodes.get(key);
			node.setColor(Color.WHITE);
			node.setDistance(0);
			node.setPrev(null);
		}
		Node root=nodes.get(s);
		Queue<Node>out=new LinkedList<>();
		int time=-1;
		out=DFS_Visite(root,time,out);
		return out;
	}
	
	public Queue<Node> DFS_Visite(Node n,int time,Queue<Node>out) {
		n.setColor(Color.GRAY);
		time+=1;
		n.setDistance(time);
		List<Arista>list=n.getAd();
		out.add(n);
		if(list!=null) {
			for(Arista ar:list) {
				Node node=ar.getB();
				if(node.getColor()!=Color.BLACK) {
					node.setPrev(n);
					out=DFS_Visite(node,time,out);
				}
			}
		}
		n.setColor(Color.BLACK);
		time+=1;
		n.setF(time);
		return out;
	}
	
	public Queue<Node> dijkstra (E source) {
		Queue<Node> out = new LinkedList<>();
		Node root = nodes.get(source);
		PriorityQueue<Node> queue = new PriorityQueue<>(amountVertices, new CompareToDistance());
		Collection<Node> v = nodes.values();
		for(Node i:v) {
			i.setDistance(Integer.MAX_VALUE);
			i.setPrev(null);
		}
		root.setDistance(0);
		queue.add(root);
		while(queue.isEmpty() == false) {
			Node u = queue.poll();
			out.add(u);
			List<Arista> ad = u.getAd();
			if(ad!=null) {
				for(Arista i:ad) {
					Node n = i.getB();
					if (n.getDistance() > u.getDistance() + i.getW()) {
						n.setDistance(u.getDistance() + i.getW());
						n.setPrev(u);
						queue.add(n);
					}
				}
			}
			
		}
		return out;
	}
	
	
	
	public class Node{
		private E element;
		private List<Arista> adyacentes;
		private Color color;
		private int distance;
		private int f;
		private Node previous;
		
		
		public Node() {
			color=Color.WHITE;
		}
		public Node(E element) {
			this.element=element;
		}
		
		public E get() {
			return element;
		}
		
		public void addAdyacente(Arista e) {
			if(adyacentes==null) {
				adyacentes=new ArrayList<>();
			}
			
			adyacentes.add(e);
		}
		
		public List<Arista> getAd(){
			return adyacentes;
		}
		
		public Color getColor() {
			return color;
		}
		
		public void setColor(Color color) {
			this.color = color;
		}
		
		public void setDistance(int distance) {
			this.distance = distance;
		}
		public int getDistance() {
			return distance;
		}
		
		public void setPrev(Node previous) {
			this.previous = previous;
		}
		public Node getPrev() {
			return previous;
		}
		
		public String toString() {
			return "["+element.toString()+"]";
		}
		public int getF() {
			return f;
		}
		public void setF(int f) {
			this.f = f;
		}
		
		
	}
	
	@SuppressWarnings("unused")
	private class Arista implements Comparable<Arista>{
		Node a;
		Node b;
		int weight;
		
		public Arista(Node a,Node b) {
			this.a=a;
			this.b=b;
		}
		
		public Arista(Node a,Node b,int w) {
			this.a=a;
			this.b=b;
			this.weight=w;
		}
		
		public void setW(int w) {
			this.weight=w;
		}
		public int getW() {
			return weight;
		}
		
		public Node getA() {
			return a;
		}
		public Node getB() {
			return b;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object obj) {
			Arista arista2=(Arista)obj;
			System.out.println(arista2.weight);
			System.out.println(this.weight);
			
			if(arista2.weight==0) {
				if(a==arista2.a&&b==arista2.b) {
					return true;
				}else return false;
			}else if(a==arista2.a&&b==arista2.b&&arista2.weight==this.weight) {
				return true;
			}else return false;	
		}

		@Override
		public int compareTo(Grafo<E>.Arista a2) {
			return this.weight-a2.weight;
		}
	}
	
	private enum Color{
		WHITE,BLACK,GRAY;
	}
	
	private class Comp implements Comparator<Arista>{

		@Override
		public int compare(Grafo<E>.Arista ar0, Grafo<E>.Arista ar1) {
			return ar1.getW()-ar0.getW();
		}
		
	}
	
	private class CompareToDistance implements Comparator<Node>{

		@Override
		public int compare(Node o1, Node o2) {
			return o2.getDistance() - o1.getDistance();
		}

		
	}
	
}
