package yandexdisk.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;
import yandexdisk.steps.YandexDiskSteps;

public class BaseTest {
    protected static YandexDiskSteps steps;
    protected String testRootFolder;

    static {
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
        steps = new YandexDiskSteps();
    }

    @Before
    public void createTestRoot() {
        testRootFolder = "/qa_root_" + System.currentTimeMillis();
        steps.createFolder(testRootFolder);
    }

    @After
    public void deleteTestRoot() {
        if (testRootFolder != null) {
            steps.deleteResource(testRootFolder, true);
        }
    }

    protected String generateTestPath(String suffix) {
        return testRootFolder + suffix;
    }
}