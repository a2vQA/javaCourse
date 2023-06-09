package ru.stqa.javaCourse.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.javaCourse.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.AssertJUnit.assertTrue;

public class LoginTests extends BaseTest {

    @Test
    public void testLogin() throws IOException {
        HttpSession session = app.newSession();
        assertTrue(session.login("administrator", "root"));
        assertTrue(session.isLoggedInAs("administrator"));
    }
}
