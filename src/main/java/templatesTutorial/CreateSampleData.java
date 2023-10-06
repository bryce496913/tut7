package templatesTutorial;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateSampleData {

	public static void main(String[] args) throws Exception {
		Person[] persons = new Person[]{
				new Person("Tim","MacDonald",Gender.MALE,new Address(100,"Main Street","Palmerston North")),
				new Person("Tom","Smith",Gender.MALE,new Address(42,"Cuba Street","Palmerston North")),
				new Person("Amy","Turner",Gender.FEMALE,new Address(3,"Worchester Street","Palmerston North")),
				new Person("Anne","O'Brian",Gender.FEMALE,new Address(4,"Manchester Street","Feilding")),
				new Person("Fred","Miller",Gender.MALE,new Address(55,"Salisbury Street","Ashhurst")),
				new Person("Max","Meyer",Gender.MALE,new Address(77,"Main Street","Palmerston North")),
				new Person("Walter","Mueller",Gender.MALE,new Address(22,"Cook Street","Palmerston North")),
				new Person("Kate","Wang",Gender.FEMALE,new Address(12,"Albert Street","Palmerston North"))
		};
		List<Person> list = new ArrayList<Person>();
		for (Person p:persons) list.add(p);
		
		XMLEncoder e = new XMLEncoder(
			    new BufferedOutputStream(
			        new FileOutputStream("friends.xml")));
		e.writeObject(list);
		e.close();
	}

}
