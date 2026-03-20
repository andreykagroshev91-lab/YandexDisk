package yandexdisk.tests;

import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GetRequestsTest extends BaseTest {

    @Test
    public void testGetDiskInfo() {
        Response response = steps.getDiskInfo();
        steps.validateSuccess(response);
        response.then()
                .body("total_space", notNullValue())
                .body("used_space", notNullValue());
    }

    @Test
    public void testGetFolderInfo() {
        String testFolderPath = generateTestPath("/folder");
        steps.createFolder(testFolderPath);

        Response response = steps.getResourceInfo(testFolderPath);
        steps.validateSuccess(response);
        response.then()
                .body("type", equalTo("dir"))
                .body("name", notNullValue());
    }

    @Test
    public void testGetNonExistentResource() {
        Response response = steps.getResourceInfo("/non_existent_" + System.currentTimeMillis());
        steps.validateResourceNotFound(response);
    }

    @Test
    public void testGetFilesList() {
        Response response = steps.getFilesList(10, 0);
        steps.validateSuccess(response);
        response.then()
                .body("limit", equalTo(10))
                .body("items", notNullValue());
    }

    @Test
    public void testGetLastUploaded() {
        Response response = steps.getLastUploaded(5);
        steps.validateSuccess(response);
        response.then()
                .body("limit", equalTo(5))
                .body("items", notNullValue());
    }

    @Test
    public void testGetTrashContent() {
        String testFolderPath = generateTestPath("/trash_test");
        steps.createFolder(testFolderPath);
        steps.deleteResource(testFolderPath, false);

        Response response = steps.getTrashContent();
        steps.validateSuccess(response);
        response.then()
                .body("_embedded.items", notNullValue());
    }

    @Test
    public void testGetDownloadLink() {
        String testFolderPath = generateTestPath("/download_test");
        steps.createFolder(testFolderPath);

        String filePath = testFolderPath + "/test.txt";
        Response uploadLinkResp = steps.getUploadLink(filePath, false);
        String uploadUrl = steps.extractHref(uploadLinkResp);
        steps.uploadFile(uploadUrl, "test content".getBytes());

        Response response = steps.getDownloadLink(filePath);
        steps.validateSuccess(response);
        response.then()
                .body("href", notNullValue())
                .body("method", equalTo("GET"));
    }

    @Test
    public void testGetUploadLink() {
        String testFolderPath = generateTestPath("/upload_test");
        steps.createFolder(testFolderPath);

        String filePath = testFolderPath + "/test.txt";
        Response response = steps.getUploadLink(filePath, false);
        steps.validateSuccess(response);
        response.then()
                .body("href", notNullValue())
                .body("method", equalTo("PUT"));
    }
}