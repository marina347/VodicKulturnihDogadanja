package hr.foi.air;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hr.foi.air.activity.RegistrationActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by marbulic on 17.01.18..
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegistrationActivityTest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;

    @Rule
    public ActivityTestRule<RegistrationActivity> mInputRegistrationTextActivityTestRule =
            new ActivityTestRule<RegistrationActivity>(RegistrationActivity.class);

    @Before
    public void initValues(){
        username="test";
        password="test";
        email="test@test.hr";
        name="name";
        surname="surname";

    }
    @Test
    public void inputText() throws Exception{
        onView(withId(R.id.reg_username_et))
                .perform(typeText(username), closeSoftKeyboard());

        onView(withId(R.id.reg_password_et))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.reg_email_et))
                .perform(typeText(email), closeSoftKeyboard());

        onView(withId(R.id.reg_name_et))
                .perform(typeText(name), closeSoftKeyboard());

        onView(withId(R.id.reg_surname_et))
                .perform(typeText(surname), closeSoftKeyboard());


        onView(withId(R.id.reg_username_et))
                .check(matches(withText(username)));

        onView(withId(R.id.reg_password_et))
                .check(matches(withText(password)));

        onView(withId(R.id.reg_email_et))
                .check(matches(withText(email)));

        onView(withId(R.id.reg_name_et))
                .check(matches(withText(name)));

        onView(withId(R.id.reg_surname_et))
                .check(matches(withText(surname)));

        onView(withId(R.id.btnRegister))
                .perform(click());

    }
}
