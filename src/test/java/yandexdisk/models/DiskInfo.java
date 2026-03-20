package yandexdisk.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class DiskInfo {
    @SerializedName("total_space")
    private long totalSpace;
    @SerializedName("used_space")
    private long usedSpace;
    @SerializedName("trash_size")
    private long trashSize;
    @SerializedName("system_folders")
    private Map<String, String> systemFolders;

    public long getTotalSpace() { return totalSpace; }
    public long getUsedSpace() { return usedSpace; }
    public long getTrashSize() { return trashSize; }
    public Map<String, String> getSystemFolders() { return systemFolders; }
}