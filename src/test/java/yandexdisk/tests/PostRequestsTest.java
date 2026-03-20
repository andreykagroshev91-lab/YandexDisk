package yandexdisk.tests;

import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class PostRequestsTest extends BaseTest {

    @Test
    public void testCopyFolder() {
        String sourceFolder = generateTestPath("/source");
        String targetFolder = generateTestPath("/target");
        steps.createFolder(sourceFolder);

        Response response = steps.copyResource(sourceFolder, targetFolder, false);
        steps.validateCreated(response);

        Response check = steps.getResourceInfo(targetFolder);
        steps.validateResourceExists(check);
        check.then().body("type", equalTo("dir"));
    }

    @Test
    public void testMoveFolder() {
        String sourceFolder = generateTestPath("/source");
        String targetFolder = generateTestPath("/target");
        steps.createFolder(sourceFolder);

        Response response = steps.moveResource(sourceFolder, targetFolder, false);
        steps.validateCreated(response);

        steps.validateResourceNotFound(steps.getResourceInfo(sourceFolder));

        Response check = steps.getResourceInfo(targetFolder);
        steps.validateResourceExists(check);
        check.then().body("type", equalTo("dir"));
    }

    @Test
    public void testUploadFile() {
        String testFolderPath = generateTestPath("/upload_test");
        steps.createFolder(testFolderPath);

        String filePath = testFolderPath + "/upload_test.txt";
        Response uploadLinkResp = steps.getUploadLink(filePath, false);
        String uploadUrl = steps.extractHref(uploadLinkResp);

        byte[] content = "Hello, Yandex Disk!".getBytes();
        Response uploadResponse = steps.uploadFile(uploadUrl, content);
        uploadResponse.then().statusCode(201);

        Response check = steps.getResourceInfo(filePath);
        steps.validateResourceExists(check);
        check.then()
                .body("type", equalTo("file"))
                .body("size", equalTo(content.length));
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