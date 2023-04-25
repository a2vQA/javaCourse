package ru.stqa.javaCourse.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.javaCourse.addressbook.model.GroupData;

public class GroupModificationTests extends BaseTest {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModification();
        GroupData groupData = new GroupData("test3", "test2", "test1");
        app.getGroupHelper().fillGroupForm(groupData);
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
    }
}