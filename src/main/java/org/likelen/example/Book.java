/**
 * Created by joenggyu0@gmail.com on 5/10/20
 * Github : http://github.com/lenkim
 */

package org.likelen.example;

@MyAnnotation(number = 200, value = 300)
public class Book {

    @MyAnnotation
    public static String a = "A";
    private String b = "B";

    public Book() {
    }

    public Book(String b) {
        this.b = b;
    }

    private void c(){
        System.out.println("invoke method c");
    }

    public void d(){
        System.out.println("invoke method d");
    }

    public int sum(int left, int right){
        return left + right;
    }

}
