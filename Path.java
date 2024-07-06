package application;

public class Path {
	   private final String path;
	    private final int cost;

	    public Path(String path, int cost) {
	        this.path = path;
	        this.cost = cost;
	    }

	    public String getPath() {
	        return path;
	    }

	    public int getCost() {
	        return cost;
	    }
}
