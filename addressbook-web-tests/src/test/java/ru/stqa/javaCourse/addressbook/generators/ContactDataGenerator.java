package ru.stqa.javaCourse.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.javaCourse.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try{
            jCommander.parse(args);
        } catch (ParameterException ex){
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if (format.equals("csv")) {
            saveAsCsv(contacts, new File(file));
            System.out.println("Writing in CSV");
        } else if (format.equals("xml")){
            saveAsXml(contacts, new File(file));
            System.out.println("Writing in XML");
        } else if (format.equals("json")) {
            saveAsJson(contacts, new File(file));
            System.out.println("Writing in JSON");
        } else {
            System.out.println("Unrecognized format " + format);
        }

    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

    private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        String xml = xstream.toXML(contacts);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }

    private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts){
            writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getAddress(),
                    contact.getMobilePhone(),
                    contact.getWorkPhone(),
                    contact.getHomePhone(),
                    contact.getPrimaryEmail(),
                    contact.getSecondaryEmail(),
                    contact.getThirdEmail(),
                    contact.getPhotoPath()));
        }
        writer.close();
    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<>();
        for (int i = 0; i < count; i++){
            contacts.add(new ContactData()
                    .withFirstName(String.format("VladislavG %d", i))
                    .withLastName(String.format("ArtyomenkoG %d", i))
                    .withAddress(String.format("MoscowG %d", i))
                    .withMobilePhone(String.format("+7999999999%d", i))
                    .withWorkPhone(String.format("99999%d", i))
                    .withHomePhone(String.format("88888%d", i))
                    .withPrimaryEmail(String.format("javaCourse@test.ruG %d", i))
                    .withSecondaryEmail(String.format("javaCourse2@test.ruG %d", i))
                    .withThirdEmail(String.format("javaCourse3@test.ruG %d", i))
                    .withPhotoPath());
        }
        return contacts;
    }
}
