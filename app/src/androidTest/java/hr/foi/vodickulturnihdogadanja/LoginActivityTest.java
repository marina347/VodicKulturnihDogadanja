package hr.foi.vodickulturnihdogadanja;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hr.foi.vodickulturnihdogadanja.activity.LoginActivity;

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
public class LoginActivityTest {

    private String username;
    private String password;

    @Rule
    public ActivityTestRule<LoginActivity> mInputLoginTextActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Before
    public void initValues(){
        username="test";
        password="test";
    }
    @Test
    public void inputText() throws Exception{
        onView(withId(R.id.log_username_et))
                .perform(typeText(username), closeSoftKeyboard());

        onView(withId(R.id.log_password_et))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.log_username_et))
                .check(matches(withText(username)));

        onView(withId(R.id.log_password_et))
                .check(matches(withText(password)));

        onView(withId(R.id.btnLogin))
                .perform(click());

    }
}
