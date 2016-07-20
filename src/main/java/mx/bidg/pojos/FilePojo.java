package mx.bidg.pojos;

/**
 * Created by gerardo8 on 18/07/16.
 */
public class FilePojo {
    private String name;
    private long size;
    private String type;
    private String dataUrl;

    public FilePojo() {
    }

    public FilePojo(String name, long size, String type, String dataUrl) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.dataUrl = dataUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilePojo filePojo = (FilePojo) o;

        if (size != filePojo.size) return false;
        if (name != null ? !name.equals(filePojo.name) : filePojo.name != null) return false;
        if (type != null ? !type.equals(filePojo.type) : filePojo.type != null) return false;
        return dataUrl != null ? dataUrl.equals(filePojo.dataUrl) : filePojo.dataUrl == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (int) (size ^ (size >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (dataUrl != null ? dataUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FilePojo{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", dataUrl='" + dataUrl + '\'' +
                '}';
    }
}
