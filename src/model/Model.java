package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;

import structures.Grafo;
import structures.GrafoDM;

public class Model {
	
	private Grafo<String>gLs;
	private GrafoDM<String>gDs;
	private Grafo<String>gLsAmigo;
	private GrafoDM<String>gDsAmigo;
	private ArrayList<String>names;
	public Model() {
		gLs=new  Grafo<>();
		gDs=new  GrafoDM<>();
		gLsAmigo=new  Grafo<>();
		gDsAmigo=new  GrafoDM<>();
		names=new ArrayList<>();
	}
	
	public Grafo<String> getgLs() {
		return gLs;
	}
	
	public void setgLs(Grafo<String> gLs) {
		this.gLs = gLs;
	}
	
	public GrafoDM<String> getgDs() {
		return gDs;
	}
	
	public void setgDs(GrafoDM<String> gDs) {
		this.gDs = gDs;
	}
	
	public void addPerson(String name) {
		names.add(name);
		gLs.addVertice(name);
		gDs.addV(name);
		gLsAmigo.addVertice(name);
		gDsAmigo.addV(name);
	}
	
	public void addFriend(String name1,String name2) {
		gLsAmigo.addArista(name1,name2);
		gDsAmigo.addA(name1,name2);
		gLsAmigo.addArista(name2,name1);
		gDsAmigo.addA(name2,name1);
	}
	
	public void addFollower(String follower,String followed) {
		gLs.addArista(followed,follower);
		gDs.addA(followed,follower);
	}
	
	public String getGreater(int i) {
		String out="";
		int max=0;
		if(i==0) {
			Set<String>list=gLs.getVertices();
			for(String name:list) {
				Queue<String>followers=gLs.BFS(name);
				if(followers.size()>max) {
					max=followers.size();
					out=name;
				}
				
			}
		}else if(i==1) {
			Set<String>list=gLs.getVertices();
			for(String name:list) {
				Queue<String>followers=gLs.BFS(name);
				if(followers.size()>max) {
					max=followers.size();
					out=name;
				}
				
			}
		}
		return out;
	}
	
	public ArrayList<String> namesRoad(String nameBeginig,String nameTosearch,int i){
		ArrayList<String> out=new ArrayList<>();
		if(i==0) {
			Queue<Grafo<String>.Node> nodes=gLsAmigo.dijkstra(nameBeginig);
			Grafo<String>.Node found=null;
			for(Grafo<String>.Node node:nodes) {
				if(node.get().equals(nameTosearch)) {
					found=node;
					
					break;
				}
			}
			if(found!=null) {
				while(found.getPrev()!=null) {
					out.add(0,found.get());
					found=found.getPrev();
				}
				out.add(0,found.get());
			}
			
		}else if(i==1) {
			Queue<GrafoDM<String>.Node> nodes=gDsAmigo.dijkstra(nameBeginig);
			GrafoDM<String>.Node found=null;
			for(GrafoDM<String>.Node node:nodes) {
				if(node.get()==nameTosearch) {
					found=node;
					break;
				}
			}
			if(found!=null) {
				while(found.getPrev()!=null) {
					out.add(0,found.get());
					found=found.getPrev();
				}
				out.add(0,found.get());
			}
		}
		return out;
	}
	
	public ArrayList<String>getNames(){
		return names;
	}
	
	public void readPeople() {
		try {
			File file = new File(".\\src\\files\\names");
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader input = new BufferedReader(fr);
			while (input.ready()) {
				String name = input.readLine();
				addPerson(name);
			}
			input.close();
			fr.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void readFriends() {
		try {
			File file = new File(".\\src\\files\\Amigos");
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader input = new BufferedReader(fr);
			while (input.ready()) {
				String names = input.readLine();
				String[] friend = names.split(";");
				addFriend(friend[0], friend[1]);
			}
			input.close();
			fr.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void readFollowers() {
		try {
			File file = new File(".\\src\\files\\vertices");
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader input = new BufferedReader(fr);
			while (input.ready()) {
				String names = input.readLine();
				String[] follow = names.split(";");
				addFollower(follow[0], follow[1]);
			}
			input.close();
			fr.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
