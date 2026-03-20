package yandexdisk.tests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class PutRequestsTest extends BaseTest {

    @Test
    public void testCreateFolder() {
        String testFolderPath = generateTestPath("/new_folder");
        Response response = steps.createFolder(testFolderPath);
        steps.validateCreated(response);

        Response check = steps.getResourceInfo(testFolderPath);
        steps.validateResourceExists(check);
        check.then().body("type", equalTo("dir"));
    }

    @Test
    public void testCreateExistingFolder() {
        String testFolderPath = generateTestPath("/existing_folder");
        steps.createFolder(testFolderPath);

        Response response = steps.createFolder(testFolderPath);
        response.then().statusCode(409);
    }

    @Test
    public void testPublishFolder() {
        String testFolderPath = generateTestPath("/publish_test");
        steps.createFolder(testFolderPath);

        Response response = steps.publishResource(testFolderPath);
        steps.validateSuccess(response);

        Response check = steps.getResourceInfo(testFolderPath);
        check.then()
                .body("public_url", notNullValue())
                .body("public_key", notNullValue());
    }

    @Test
    public void testUnpublishResource() {
        String testFolderPath = generateTestPath("/unpublish_test");
        steps.createFolder(testFolderPath);
        steps.publishResource(testFolderPath);

        Response response = steps.unpublishResource(testFolderPath);
        steps.validateSuccess(response);

        Response check = steps.getResourceInfo(testFolderPath);
        check.then()
                .body("public_url", nullValue())
                .body("public_key", nullValue());
    }

    @Test
    public void testRestoreFromTrash() {
        String testFolderPath = generateTestPath("/restore_test");
        steps.createFolder(testFolderPath);

        String originalPath = "disk:" + testFolderPath;

        steps.deleteResource(testFolderPath, false);

        Response trashResponse = steps.getTrashContent();

        List<String> originPaths = trashResponse.jsonPath().getList("_embedded.items.origin_path");
        List<String> trashPaths = trashResponse.jsonPath().getList("_embedded.items.path");

        String trashPath = null;
        for (int i = 0; i < originPaths.size(); i++) {
            if (originalPath.equals(originPaths.get(i))) {
                trashPath = trashPaths.get(i);
                break;
            }
        }

        Response restoreResponse = steps.restoreFromTrash(trashPath, false);
        restoreResponse.then().statusCode(anyOf(is(200), is(201), is(202)));

        Response checkResponse = steps.getResourceInfo(testFolderPath);
        steps.validateResourceExists(checkResponse);
        checkResponse.then().body("type", equalTo("dir"));
    }
}