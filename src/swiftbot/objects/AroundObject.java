package swiftbot.objects;

public class AroundObject {
    private String name;
    private Coordinate coordinate;

    public AroundObject(String name, Coordinate coordinate) {
        this.name = name;
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getName() {
        return name;
    }
}
