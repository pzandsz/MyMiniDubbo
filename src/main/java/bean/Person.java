package bean;

/**
 * @Author: zengpeng
 * @Date: 2019/10/915:59
 */

public class Person {
    String name;
    Integer age;

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Person(String name,Integer age){
        this.name=name;
        this.age=age;
    }

    public Person(){

    }
}
