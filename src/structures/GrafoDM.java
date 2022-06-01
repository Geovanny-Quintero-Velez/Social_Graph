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


public class GrafoDM<E> {
	private Hashtable<E,Node> table;
	private Hashtable<Node,Hashtable<Node,List<Arista>>> matrix;
	private int numNodes;
	private int numAristas;
	
	public GrafoDM() {
		matrix=new Hashtable<>();
		table=new Hashtable<>();
		numNodes=0;
	}
	
	public int verticesAmount() {
		return numNodes;
	}
	
	public int aristasAmount() {
		return numAristas;
	}
	
	public int size() {
		return table.size();
	}
	
	public void addV(E e1) {
		Node newNode=new Node(e1);
		table.put(e1, newNode);
		Hashtable<Node,List<Arista>> a=new Hashtable<Node,List<Arista>>();
		matrix.put(newNode, a);
		numNodes++;
	}
	
	public boolean addA(E e1,E e2) {
		Node n1=table.get(e1);
		Node n2=table.get(e2);
		if(n1!=null&&n2!=null) {
			Arista newArista=new Arista(n1,n2);
			Hashtable<Node,List<Arista>> column=matrix.get(n1);
			List<Arista> list=column.get(n2);
			if(list==null) {
				list=new ArrayList<>();
			}
			list.add(newArista);
			column.put(n2, list);
			numAristas++;
			return true;
		}
		return false;
	}
	
	public Node getVert(E e1) {
		return table.get(e1);
	}
	
	public List<E> getAdyacentes(E e1) {
		Node n1=table.get(e1);
		List<E> list=new ArrayList<>();
		if(n1!=null) {
			Hashtable<Node,List<Arista>> column=matrix.get(n1);
			Set<Node> keys=column.keySet();
			for(Node node:keys) {
				List<Arista> aristas=column.get(node);
				for(Arista arista:aristas) {
					list.add(arista.getB().get());
				}
			}
			
		}
		return list;
	}
	
	public List<Arista> getAdyacentes(Node n1) {
		List<Arista> list=new ArrayList<>();
		if(n1!=null) {
			Hashtable<Node,List<Arista>> column=matrix.get(n1);
			Set<Node> keys=column.keySet();
			for(Node node:keys) {
				list.addAll(column.get(node));
			}
		}
		return list;
	}
	
	public boolean addA(E e1,E e2,int value) {
		Node n1=table.get(e1);
		Node n2=table.get(e2);
		if(n1!=null&&n2!=null) {
			Arista newArista=new Arista(n1,n2,value);
			Hashtable<Node,List<Arista>> column=matrix.get(n1);
			List<Arista> list=column.get(n2);
			if(list==null) {
				list=new ArrayList<>();
			}
			list.add(newArista);
			column.put(n2, list);
			numAristas++;
			return true;
		}
		return false;
	}
	
	public boolean removeA(E e1,E e2) {
		Node n1=table.get(e1);
		Node n2=table.get(e2);
		if(n1!=null&&n2!=null) {
			Hashtable<Node,List<Arista>> column=matrix.get(n1);
			Arista arista=new Arista(n1,n2);
			List<Arista> list=column.get(n2);
			numAristas--;
			return list.remove(arista);
		}
		return false;
	}
	
	public Set<E> getVertices() {
		return table.keySet();
	}
	
	public boolean isVert(E e1) {
		return matrix.get(e1)!=null;
	}
	
	public boolean isArista(E n1,E n2) {
		Node node1=table.get(n1);
		Node node2=table.get(n2);
		if(node1!=null&&node2!=null) {
			List<Arista> l1=getAdyacentes(node1);
			Arista ar=new Arista(node1,node2);
			if(l1!=null) {
				return l1.contains(ar);
			}
		}
		return false;
	}
	
	public boolean isArista(E n1,E n2,int value) {
		Node node1=table.get(n1);
		Node node2=table.get(n2);
		if(node1!=null&&node2!=null) {
			List<Arista> l1=getAdyacentes(node1);
			Arista ar=new Arista(node1,node2,value);
			if(l1!=null) {
				for(int i=0;i<l1.size();i++) {
					Arista arista2=l1.get(i);
					if(arista2.equals(ar)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean removeA(E e1,E e2,int value) {
		Node n1=table.get(e1);
		Node n2=table.get(e2);
		if(n1!=null&&n2!=null) {
			Hashtable<Node,List<Arista>> column=matrix.get(n1);
			Arista arista=new Arista(n1,n2,value);
			List<Arista> list=column.get(n2);
			for(int i=0;i<list.size();i++) {
				Arista arista2=list.get(i);
				if(arista2.equals(arista)) {
					list.remove(i);
					numAristas--;
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	public int getNumNodes() {
		return numNodes;
	}


	public int getNumAristas() {
		return numAristas;
	}
	
	public boolean removeV(E e1) {
		Node n1=table.get(e1);
		if(n1!=null) {
			int num=matrix.get(n1).keySet().size();
			numAristas-=num;
			table.remove(e1);
			matrix.remove(n1);
			Set<Node> nodes=matrix.keySet();
			for(Node node:nodes) {
				Hashtable<Node,List<Arista>> column=matrix.get(node);
				if(column.remove(n1) != null) {
					numAristas--;
				}
			}
			numNodes--;
			return true;
		}
		return false;
	}
	
	public Queue<E> BFS(E s)
    {
		
		Node root=table.get(s);
        LinkedList<Node> queue = new LinkedList<>();
        Queue<E> out = new LinkedList<>();
        root.setColor(Color.BLACK);
        queue.add(root);
        
        while (queue.size() != 0)
        {
            root = queue.poll();
            out.add(root.get());
            List<Arista> list=getAdyacentes(root);
            
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
        
        Collection<Node> a=table.values();
        for(Node i:a) {
        	i.setColor(Color.WHITE);
        }
        return out;
    }
	
	public Queue<Node> DFS(E s) {
		Set<E>setNodes=table.keySet();
		for(E key:setNodes) {
			Node node=table.get(key);
			node.setColor(Color.WHITE);
			node.setDistance(0);
			node.setPrev(null);
		}
		Node root=table.get(s);
		Queue<Node>out=new LinkedList<>();
		int time=-1;
		out=DFS_Visite(root,time,out);
		return out;
	}
	
	public Queue<Node> DFS_Visite(Node n,int time,Queue<Node>out) {
		n.setColor(Color.GRAY);
		time+=1;
		n.setDistance(time);
		List<Arista>list=getAdyacentes(n);
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
		Node root = table.get(source);
		PriorityQueue<Node> queue = new PriorityQueue<Node>(new CompareToDistance());
		Collection<Node> v = table.values();
		for(Node i:v) {
			i.setDistance(Integer.MAX_VALUE);
			i.setPrev(null);
		}
		root.setDistance(0);
		queue.add(root);
		while(queue.isEmpty() == false) {
			Node u = queue.poll();
			out.add(u);
			List<Arista> ad =getAdyacentes(u);
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
	
	public GrafoDM<E> prim(E s) {
		Node root=table.get(s);
		if(root!=null) {
			GrafoDM<E>g=new GrafoDM<>();
			g.addV(root.get());
			Set<E> keys=table.keySet();
			for(E ar:keys) {
				g.addV(table.get(ar).get());
			}
			Comp comp=new Comp();
			PriorityQueue<Arista>prQA=new PriorityQueue<>(comp);
			int i=1;
			while(i<numNodes) {
				List<Arista>aristas=getAdyacentes(root);
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
						g.addA(ar.getA().get(), ar.getB().get(), ar.getW());
						g.addA(ar.getB().get(), ar.getA().get(), ar.getW());
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
			if(arista2.weight==0) {
				if(a==arista2.a&&b==arista2.b) {
					return true;
				}else return false;
			}else if(a==arista2.a&&b==arista2.b&&arista2.weight==this.weight) {
				return true;
			}else return false;	
		}

		@Override
		public int compareTo(Arista a2) {
			return this.weight-a2.weight;
		}
	}
	
	private enum Color{
		WHITE,BLACK,GRAY;
	}
	public class Node{
		private E element;
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
	
	private class Comp implements Comparator<Arista>{

		@Override
		public int compare(Arista ar0, Arista ar1) {
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
