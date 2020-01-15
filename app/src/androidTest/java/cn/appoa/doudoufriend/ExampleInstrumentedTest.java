package cn.appoa.doudoufriend;

import android.content.Context;

import static cn.appoa.aframework.app.AfApplication.appContext;
import static junit.framework.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleInstrumentedTest {

    public void useAppContext() throws Exception {
        // Context of the app under test.


        assertEquals("cn.appoa.doudoufriend", appContext.getPackageName());
    }
}
