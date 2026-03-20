package yandexdisk.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class Resource {
    private String path;
    private String name;
    private String type;
    private long size;
    private String created;
    private String modified;
    private String md5;
    private String file;
    private String preview;
    @SerializedName("public_key")
    private String publicKey;
    @SerializedName("public_url")
    private String publicUrl;
    @SerializedName("custom_properties")
    private Map<String, String> customProperties;
    @SerializedName("_embedded")
    private Embedded embedded;

    public static class Embedded {
        private List<Resource> items;
        public List<Resource> getItems() { return items; }
    }

    public String getPath() { return path; }
    public String getName() { return name; }
    public String getType() { return type; }
    public long getSize() { return size; }
    public String getMd5() { return md5; }
    public String getFile() { return file; }
    public String getPreview() { return preview; }
    public String getPublicKey() { return publicKey; }
    public String getPublicUrl() { return publicUrl; }
    public Map<String, String> getCustomProperties() { return customProperties; }
    public Embedded getEmbedded() { return embedded; }
}