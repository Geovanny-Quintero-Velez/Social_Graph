package ui;

import java.util.ArrayList;
import java.util.Scanner;

import model.Model;

public class Main {
	static Model model;
	static Scanner sc;
	public static void main(String[] args) {
		model=new Model();
		sc=new Scanner(System.in);
		boolean flag=false;
		int grafo=0;
		do {
			System.out.println("Seleccione");
			System.out.println("1. Encontrar persona con más seguidores");
			System.out.println("2. Buscar camino desde una persona a otra");
			System.out.println("3. cambiar grafo");
			System.out.println("0. Terminar");
			int i=sc.nextInt();
			sc.nextLine();
			switch(i) {
			case 1:
				String greate=model.getGreater(grafo);
				System.out.println(greate);
				break;
			case 2:
				searchPath(grafo);
				break;
			case 3:
				if(grafo==1) {
					grafo=0;
				}else if(grafo==0) {
					grafo=1;
				}
				break;
			case 0:
				flag=true;
				break;
			}
			
		}while(!flag);
		
	}
	
	public static void searchPath(int graph) {
		ArrayList<String>names=model.getNames();
		for(int i=0;i<names.size();i++) {
			System.out.println(i+" "+names.get(i));
		}
		System.out.println("Digite indice del origen");
		int i1=sc.nextInt();
		sc.nextLine();
		System.out.println("Digite indice del Objetivo");
		int i2=sc.nextInt();
		sc.nextLine();
		ArrayList<String>path=model.namesRoad(names.get(i1), names.get(i2), graph);
		for(String name:path) {
			System.out.print(name+" ");
		}
	}
	

}
