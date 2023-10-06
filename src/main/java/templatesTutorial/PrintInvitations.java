package templatesTutorial;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.StringWriter;
import java.io.FileWriter;

public class PrintInvitations {

    public static void main(String[] args) {
        try {
            // Initialize Velocity
            Velocity.init();

            // Parse friends.xml
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("friends.xml");
            NodeList personList = document.getElementsByTagName("object");

            // Prepare the output file
            FileWriter writer = new FileWriter("invitations.txt");

            for (int i = 0; i < personList.getLength(); i++) {
                // Cast the node to an Element
                org.w3c.dom.Element personElement = (org.w3c.dom.Element) personList.item(i);

                // Ensure the node is a Person object
                if (!"templatesTutorial.Person".equals(personElement.getAttribute("class"))) {
                    continue;
                }

                // Extract data using the Element with null checks
                NodeList firstNameList = personElement.getElementsByTagName("string");
                if (firstNameList.getLength() < 3) {
                    continue; // Not enough data for a person
                }

                String firstName = firstNameList.item(0).getTextContent();
                String name = firstNameList.item(1).getTextContent();
                String street = firstNameList.item(2).getTextContent();

                NodeList numberList = personElement.getElementsByTagName("int");
                if (numberList.getLength() == 0) {
                    continue;
                }
                int number = Integer.parseInt(numberList.item(0).getTextContent());

                String town = "Palmerston North";

                // Instantiate classes
                Address address = new Address(number, street, town);
                Person person = new Person(firstName, name, Gender.MALE, address); // Assuming gender as MALE for simplicity

                // Create Velocity context
                VelocityContext context = new VelocityContext();
                context.put("person", person);

                // Define the template
                StringWriter sw = new StringWriter();
                String template = "To\n" +
                        "$person.getFirstName() $person.getName()\n" +
                        "$person.getAddress().getNumber() $person.getAddress().getStreet()\n" +
                        "$person.getAddress().getTown()\n" +
                        "Dear $person.getFirstName(),\n" +
                        "I would like to invite you to a party this Saturday, 8PM at my place.\n" +
                        "Cheers, me\n" +
                        "----------------------------------------------------------------\n";

                // Merge data with template
                Velocity.evaluate(context, sw, "InvitationTemplate", template);

                // Write to file
                writer.write(sw.toString());
            }

            // Close the writer
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
