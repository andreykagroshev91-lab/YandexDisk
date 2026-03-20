package yandexdisk.constants;

public class ApiEndpoints {
    public static final String BASE_URL = "https://cloud-api.yandex.net/v1/disk";

    public static final String DISK_INFO = "/";
    public static final String RESOURCES = "/resources";
    public static final String RESOURCES_UPLOAD = "/resources/upload";
    public static final String RESOURCES_DOWNLOAD = "/resources/download";
    public static final String RESOURCES_COPY = "/resources/copy";
    public static final String RESOURCES_MOVE = "/resources/move";
    public static final String RESOURCES_PUBLISH = "/resources/publish";
    public static final String RESOURCES_UNPUBLISH = "/resources/unpublish";
    public static final String RESOURCES_FILES = "/resources/files";
    public static final String RESOURCES_LAST_UPLOADED = "/resources/last-uploaded";
    public static final String TRASH_RESOURCES = "/trash/resources";
    public static final String TRASH_RESTORE = "/trash/resources/restore";

    public static final String PARAM_PATH = "path";
    public static final String PARAM_FROM = "from";
    public static final String PARAM_OVERWRITE = "overwrite";
    public static final String PARAM_PERMANENTLY = "permanently";
    public static final String PARAM_LIMIT = "limit";
    public static final String PARAM_OFFSET = "offset";
}