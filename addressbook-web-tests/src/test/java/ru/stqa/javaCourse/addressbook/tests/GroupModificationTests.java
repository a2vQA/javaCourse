package ru.stqa.javaCourse.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.javaCourse.addressbook.model.GroupData;
import ru.stqa.javaCourse.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends BaseTest {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData groupData = new GroupData()
                .withName("test1").withHeader("test2").withFooter("test3").withId(modifiedGroup.getId());
        app.goTo().groupPage();
        app.group().modify(groupData);

        assertThat(app.group().count(), equalTo(before.size()));

        Groups after = app.db().groups();

        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(groupData)));
        verifyGroupListInUI();
    }
}
