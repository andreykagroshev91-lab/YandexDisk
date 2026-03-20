package yandexdisk.steps;

import io.restassured.response.Response;
import yandexdisk.constants.ApiEndpoints;

import static io.restassured.RestAssured.given;
import org.apache.http.HttpStatus;

public class YandexDiskSteps {

    private static final String TOKEN = "y0__xDlpuv6BhiBnT8g687T5xYw9vasqwhiDx5FeRAI-0dDG0OXNN6WWPNsAg";
    private static final String AUTH_HEADER = "OAuth " + TOKEN;

    // ============ GET ============
    public Response getDiskInfo() {
        return given()
                .header("Authorization", AUTH_HEADER)
                .when()
                .get(ApiEndpoints.BASE_URL + ApiEndpoints.DISK_INFO);
    }

    public Response getResourceInfo(String path) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_PATH, path)
                .when()
                .get(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES);
    }

    public Response getDownloadLink(String path) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_PATH, path)
                .when()
                .get(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES_DOWNLOAD);
    }

    public Response getUploadLink(String path, boolean overwrite) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_PATH, path)
                .queryParam(ApiEndpoints.PARAM_OVERWRITE, overwrite)
                .when()
                .get(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES_UPLOAD);
    }

    public Response getFilesList(int limit, int offset) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_LIMIT, limit)
                .queryParam(ApiEndpoints.PARAM_OFFSET, offset)
                .when()
                .get(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES_FILES);
    }

    public Response getLastUploaded(int limit) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_LIMIT, limit)
                .when()
                .get(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES_LAST_UPLOADED);
    }

    public Response getTrashContent() {
        return given()
                .header("Authorization", AUTH_HEADER)
                .when()
                .get(ApiEndpoints.BASE_URL + ApiEndpoints.TRASH_RESOURCES);
    }

    // ============ POST ============
    public Response copyResource(String from, String to, boolean overwrite) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_FROM, from)
                .queryParam(ApiEndpoints.PARAM_PATH, to)
                .queryParam(ApiEndpoints.PARAM_OVERWRITE, overwrite)
                .when()
                .post(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES_COPY);
    }

    public Response moveResource(String from, String to, boolean overwrite) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_FROM, from)
                .queryParam(ApiEndpoints.PARAM_PATH, to)
                .queryParam(ApiEndpoints.PARAM_OVERWRITE, overwrite)
                .when()
                .post(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES_MOVE);
    }

    // ============ PUT ============
    public Response createFolder(String path) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_PATH, path)
                .when()
                .put(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES);
    }

    public Response publishResource(String path) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_PATH, path)
                .when()
                .put(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES_PUBLISH);
    }

    public Response unpublishResource(String path) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_PATH, path)
                .when()
                .put(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES_UNPUBLISH);
    }

    public Response restoreFromTrash(String path, boolean overwrite) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_PATH, path)
                .queryParam(ApiEndpoints.PARAM_OVERWRITE, overwrite)
                .when()
                .put(ApiEndpoints.BASE_URL + ApiEndpoints.TRASH_RESTORE);
    }

    // ============ DELETE ============
    public Response deleteResource(String path, boolean permanently) {
        return given()
                .header("Authorization", AUTH_HEADER)
                .queryParam(ApiEndpoints.PARAM_PATH, path)
                .queryParam(ApiEndpoints.PARAM_PERMANENTLY, permanently)
                .when()
                .delete(ApiEndpoints.BASE_URL + ApiEndpoints.RESOURCES);
    }

    public Response clearTrash() {
        return given()
                .header("Authorization", AUTH_HEADER)
                .when()
                .delete(ApiEndpoints.BASE_URL + ApiEndpoints.TRASH_RESOURCES);
    }

    // ============ Дополнительные ============
    public Response uploadFile(String uploadUrl, byte[] content) {
        return given()
                .header("Content-Type", "text/plain")
                .body(content)
                .when()
                .put(uploadUrl);
    }

    // ============ Валидации ============
    public void validateSuccess(Response response) {
        response.then().statusCode(HttpStatus.SC_OK);
    }

    public void validateCreated(Response response) {
        response.then().statusCode(HttpStatus.SC_CREATED);
    }

    public void validateDeleted(Response response) {
        response.then().statusCode(HttpStatus.SC_NO_CONTENT);
    }

    public void validateResourceExists(Response response) {
        response.then().statusCode(HttpStatus.SC_OK);
    }

    public void validateResourceNotFound(Response response) {
        response.then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    public String extractHref(Response response) {
        return response.jsonPath().getString("href");
    }
}