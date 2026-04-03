package ph.com.project01.webportal.form;

public class ImageForm {

    private String name;
    private String path;

    public ImageForm(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
    
}
