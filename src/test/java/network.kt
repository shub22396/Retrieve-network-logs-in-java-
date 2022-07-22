import org.junit.jupiter.api.Test
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.logging.LogEntry
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.PrintStream
import java.net.URL
import java.util.*
import java.util.logging.Level

class network {

    @Test
    fun `sample test`() {
        val username = "shubhamr"
        val accessToken = ""




        val hub = URL("https://${username}:${accessToken}@hub.lambdatest.com/wd/hub")



        val preferences = LoggingPreferences()
        preferences.enable(LogType.PERFORMANCE, Level.INFO)
        val options = ChromeOptions()
        options.setCapability(CapabilityType.LOGGING_PREFS, preferences)
        options.setCapability("goog:loggingPrefs", preferences)
        val chrome = RemoteWebDriver(hub, ChromeOptions().apply {
            // setProxy(createSeleniumProxy(browsermob))
            setCapability("platform", "Windows 10")
            setCapability("browserName", "Chrome")
            setCapability("version", "latest")
            setCapability("goog:chromeOptions",options)
        })

        chrome.get("https://www.gooogle.com")


        Thread.sleep(4000)

        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString: String = List(10) { alphabet.random() }.joinToString("")

        val fileName = "Path-of-file-"+randomString+".txt"
        var file = File(fileName)
        val isNewFileCreated :Boolean = file.createNewFile()

        val logfile: OutputStream = FileOutputStream(file, true)
        val printlog = PrintStream(logfile)
        val entries: List<LogEntry> = chrome.manage().logs().get(LogType.PERFORMANCE).getAll()
        printlog.append("{")

        for (entry in entries) {

            println("'"+Date(entry.timestamp).toString()+"'"+  "" + entry.message)
            printlog.append("{")
            printlog.append("\""+Date(entry.timestamp).toString()+"\""+ ":" + entry.message.toString() + " " + System.getProperty("line.separator"))
            printlog.append(System.getProperty("line.separator"))
            printlog.append("}")
            printlog.append(System.getProperty("line.separator"))
            printlog.append(System.getProperty("line.separator"))
        }
        printlog.append("}")

        chrome.quit();

    }
}