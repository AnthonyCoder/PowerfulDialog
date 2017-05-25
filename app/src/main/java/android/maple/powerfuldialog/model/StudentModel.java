package android.maple.powerfuldialog.model;

/**
 * Created by guest on 2017/5/25.
 */

public class StudentModel {
    private String name;
    private int age;
    private String className;
    public StudentModel(String name, String className, int age){
        this.name=name;
        this.className=className;
        this.age=age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
