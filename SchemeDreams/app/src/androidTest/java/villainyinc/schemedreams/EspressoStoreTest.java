package villainyinc.schemedreams;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by joshuagoldberg on 8/1/16.
 */

@RunWith(AndroidJUnit4.class)
public class EspressoStoreTest {

    @Rule
    public ActivityTestRule<StoreHomeActivity> mStoreRule =
            new ActivityTestRule<StoreHomeActivity>(StoreHomeActivity.class);

// user story: As a user, I want to add items to my cart
    @Test
    public void testAddToCartFromHome() throws Exception {
        onView(withId(R.id.homeRecycler_1))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.action_cart)).perform(click());
        onView(withId(R.id.cart_count)).check(matches(withText("1")));
        onView(withId(R.id.cart_decrease)).perform(click());
    }

// user story: as a user, I want to be able to increase and/or descrease/remove items in my cart
    @Test
    public void testIncrementItemsInCart() throws Exception {
        onView(withId(R.id.homeRecycler_1))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.action_cart)).perform(click());
        onView(withId(R.id.cart_increase)).perform(click());
        onView(withId(R.id.cart_increase)).perform(click());
        onView(withId(R.id.cart_increase)).perform(click());
        onView(withId(R.id.cart_increase)).perform(click());
        onView(withId(R.id.cart_increase)).perform(click());
        onView(withText("Unfortunately, that item is out of stock.")).check(matches(isDisplayed()));
        onView(withId(R.id.cart_decrease)).perform(click());
        onView(withId(R.id.cart_decrease)).perform(click());
        onView(withId(R.id.cart_decrease)).perform(click());
        onView(withId(R.id.cart_decrease)).perform(click());
        onView(withId(R.id.cart_decrease)).perform(click());
        onView(withId(R.id.cart_count)).check(doesNotExist());
    }

// user story: as a user, I want to be able to search for items I'm interested in
    @Test
    public void testSearch() throws Exception {
        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("mountain"))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.long_card_name)).check(matches(withText("Mountain Hideout")));
    }

// user story: as a user, I want to buy the items in my cart
    @Test
    public void testCheckOut() throws Exception {
        onView(withId(R.id.homeRecycler_3)).perform(scrollTo());
        onView(withId(R.id.homeRecycler_3))
                .perform(RecyclerViewActions.scrollToPosition(4),
                        RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.action_cart)).perform(click());
        onView(withId(R.id.checkOut)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.cart_count)).check(doesNotExist());
    }

// having trouble clicking on navigation items in the drawer
    @Test
    public void testNavigationBar() throws Exception {
        onView(withId(R.id.drawer_layout)).perform(click());
        onView(withId(R.id.nav_monologues)).perform(click());
        onView(withId(R.id.search_recycler)).check(matches(isDisplayed()));
    }
}
