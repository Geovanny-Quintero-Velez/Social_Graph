package structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import comparator.CompareToDistance;

public class Grafo<E> {
	private Hashtable<E,Node> nodes;
	private int amountVertices;
	private int amountAristas;
	public Grafo() {
		nodes=new Hashtable<>();
		amountVertices=0;
		amountAristas=0;
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
		Node root=nodes.get(s);
        LinkedList<Node> queue = new LinkedList<>();
        Queue<E> out = new LinkedList<>();
        root.setColor(Color.BLACK);
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
        
        Collection<Node> a=nodes.values();
        
        for(Node i:a) {
        	i.setColor(Color.WHITE);
        }
        
        return out;
    }
	
	public Queue<Node> dijkstra (E source) {
		Queue<Node> out = new LinkedList<>();
		Node root = nodes.get(source);
		PriorityQueue<Node> queue = new PriorityQueue<>(50, new CompareToDistance());
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
			if(ad.size()>0) {
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
	
}
