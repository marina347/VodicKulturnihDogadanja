package hr.foi.vodickulturnihdogadanja;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hr.foi.vodickulturnihdogadanja.activity.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by marbulic on 17.01.18..
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationActivityTest {
    @Rule
    public ActivityTestRule<HomeActivity> homeActivityTestRule =
            new ActivityTestRule<HomeActivity>(HomeActivity.class);
    @Test
    public void inputText() throws Exception{

        Intent intent = new Intent();
        intent.putExtra("a", "b");

        homeActivityTestRule.launchActivity(intent);


        onView(withId(R.id.btn_login))
                .perform(click());


    }
}
