package tests;

import model.Contacts;
import model.GroupData;
import org.testng.annotations.*;
import model.ContactData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;


public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    if (! app.group().isThereAGroup()) {
      app.group().create(new GroupData().withName("test1"));
    }
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData().withFirstname("Михаил").withLastname("Голик")
            .withAddress("fgfj").withHomePhone("89600267885").withEmail( "golikmisha1@mail.ru").withPhoto(photo);

    app.contact().createContact(contact);
    app.goTo().gotoHomePage();
    assertThat(app.contact().count(), equalTo(before.size()+1));
    Contacts after = app.contact().all();
    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    assertThat(after, equalTo(before.withAdded(contact)));

  }


}
