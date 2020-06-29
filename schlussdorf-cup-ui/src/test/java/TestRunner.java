import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions( plugin = {"html"}, features = "src/test/resources/features", monochrome = true, glue = "stepDefinitions")
public class TestRunner { }
