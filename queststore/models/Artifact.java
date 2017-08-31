package queststore.models;

public class Artifact {

    private String name;
    private Integer price;
    private ArtifactCategory artifactCategory;

    public void setName (String name) {
        this.name = name;
    }

    public void setPrice (Integer price) {
        this.price = price;
    }

    public void setArtifactCategory(ArtifactCategory artifactCategory) {
        this.artifactCategory = artifactCategory;
    }

    public String getName() {
        return this.name;
    }

    public Integer getPrice() {
        return this.price;
    }

    public ArtifactCategory getArtifactCategory() {
        return this.getArtifactCategory();
    }
}
