package bean;

public class Car {
    private String name;
    private String length;
    private String width;
    private String height;
    private Wheel wheel;

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", wheel=" + wheel +
                '}';
    }
}
