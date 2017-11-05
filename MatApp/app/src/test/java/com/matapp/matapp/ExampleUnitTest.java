package com.matapp.matapp;

import org.junit.Test;

import static org.junit.Assert.*;

// TODO some ideas for Testcases:
// add/delete Material & check size of Materials after adding/deleting
// check that "btn_loan" is only displayed when status is 0 and "btn_return" when status is 1
// check that both buttons are hidden when status is 2

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}