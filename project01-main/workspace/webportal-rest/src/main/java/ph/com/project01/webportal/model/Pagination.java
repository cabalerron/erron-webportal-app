package ph.com.project01.webportal.model;

public class Pagination {

    private int page;
    private String sort;
    private int totalItems;

    private int size;

    // Getter for page
    public int getPage() {
        return page;
    }

    // Setter for page
    public void setPage(int page) {
        this.page = page;
    }

    //Setter for size
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    // Getter for sort
    public String getSort() {
        return sort;
    }

    // Setter for sort
    public void setSort(String sort) {
        this.sort = sort;
    }

    // Getter for totalItems
    public int getTotalItems() {
        return totalItems;
    }

    // Setter for totalItems
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}

