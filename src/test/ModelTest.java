package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Model;

class ModelTest {
	Model model;
	
	void scenario1() {
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
		model.addFollower(d, a);
		model.addFollower(d, b);
		model.addFriend(a, b);
		model.addFriend(b, c);
		model.addFriend(c, d);
		model.addFriend(d, e);
	}
	
	@Test
	void testGreater() {
		scenario1();
		String gr=model.getGreater(0);
		assertEquals(gr,"5");
		gr=model.getGreater(1);
		assertEquals(gr,"5");
	}
	
	@Test
	void testNamesRoad() {
		scenario1();
		ArrayList<String>toTest=model.namesRoad("1", "5", 0);
		ArrayList<String>expectedOut=new ArrayList<>();
		expectedOut.add("1");
		expectedOut.add("2");
		expectedOut.add("3");
		expectedOut.add("4");
		expectedOut.add("5");
		for(int i=0;i<toTest.size();i++) {
			assertEquals(expectedOut.get(i),toTest.get(i));
		}
		
	}

}
