package yandexdisk.tests;

import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class DeleteRequestsTest extends BaseTest {

    @Test
    public void testDeleteToTrash() {
        String testFolderPath = generateTestPath("/delete_to_trash");
        steps.createFolder(testFolderPath);

        Response response = steps.deleteResource(testFolderPath, false);
        steps.validateDeleted(response);
        steps.validateResourceNotFound(steps.getResourceInfo(testFolderPath));
    }

    @Test
    public void testDeletePermanently() {
        String testFolderPath = generateTestPath("/delete_permanent");
        steps.createFolder(testFolderPath);

        Response response = steps.deleteResource(testFolderPath, true);
        steps.validateDeleted(response);
        steps.validateResourceNotFound(steps.getResourceInfo(testFolderPath));
    }

    @Test
    public void testDeleteNonExistent() {
        String fakePath = "/non_existent_" + System.currentTimeMillis();
        Response response = steps.deleteResource(fakePath, false);
        steps.validateResourceNotFound(response);
    }

    @Test
    public void testClearTrash() {
        String testFolderPath = generateTestPath("/clear_trash_test");
        steps.createFolder(testFolderPath);
        steps.deleteResource(testFolderPath, false);

        Response response = steps.clearTrash();
        response.then().statusCode(anyOf(is(202), is(204)));
    }
}