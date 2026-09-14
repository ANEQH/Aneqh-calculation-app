package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.util.SmartMathAssistant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Anek Calculation", appName)
  }

  @Test
  fun `verify smart math square breakdown`() {
    val analysis = SmartMathAssistant.analyze("75^2")
    assertNotNull(analysis)
    assertEquals("5625", analysis?.result)
  }

  @Test
  fun `verify digital root engine`() {
    assertEquals(9, SmartMathAssistant.digitalRoot(5625L))
    assertEquals(3, SmartMathAssistant.digitalRoot(12L))
  }
}
