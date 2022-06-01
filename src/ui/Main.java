package ui;



import java.util.ArrayList;

import model.Model;


public class Main {
	static Model model;
	public static void main(String[] args) {
		String a="1";
		String b="2";
		String c="3";
		String d="4";
		String e="5";
		model=new Model();
		model.addPerson(a);
		model.addPerson(b);
		model.addPerson(c);
		model.addPerson(d);
		model.addPerson(e);
		model.addFollower(a, e);
		model.addFollower(b, e);
		model.addFollower(c, e);
		model.addFollower(d, e);
		model.addFollower(c, a);
		model.addFollower(d, a);
		model.addFollower(a, b);
		model.addFollower(b, a);
		model.addFollower(c, a);
		String greatL=model.getGreater(0);
		String greatM=model.getGreater(1);
		System.out.println(greatL);
		System.out.println(greatM);
		ArrayList<String>names=model.namesRoad(b, c, 1);
		for(String name:names) {
			System.out.println(name);
		}
		//ArrayList<String>list=
		
	}
}
