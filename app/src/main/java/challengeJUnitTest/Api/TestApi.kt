package challengeJUnitTest.Api

import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.youarelaunched.challenge.data.network.models.NetworkVendor
import com.youarelaunched.challenge.data.network.models.NetworkVendorsData
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith


//TODO Some problem with include library
// i thing its because i use for include "implementation"
// instead "testImplementation" but when i use "testImplementation" i
// have problem with build project

//@RunWith(AndroidJUnit4ClassRunner::class)
class TestApp : TestCase() {

    fun getVendors(): List<NetworkVendor> = runBlocking {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val json = appContext.assets
            .open("vendors.json")
            .bufferedReader()
            .use {
                it.readText()
            }

        Gson()
            .fromJson(json, NetworkVendorsData::class.java)
            .vendors
    }

    @Test
    fun getVendorsApiSuccess(){
        val test = runBlocking {
            getVendors()
        }
        assertEquals(test.isNotEmpty(), true)
    }
}