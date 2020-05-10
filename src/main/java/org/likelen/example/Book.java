/**
 * Created by joenggyu0@gmail.com on 5/10/20
 * Github : http://github.com/lenkim
 */

package org.likelen.example;

@MyAnnotation(number = 200, value = 300)
public class Book {

    @MyAnnotation(number = 100, value = 200)
    private String a = "a";
    private static String B = "Book";
    private static final String C = "Book";
    public String d = "d";
    protected String e = "e";

    private String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Book() {
    }

    public Book(String a, String d, String e) {
        this.a = a;
        this.d = d;
        this.e = e;
    }

    private void f() {
        System.out.println("F");
    }

    public void g() {
        System.out.println("g");
    }

    public int h() {
        return 100;
    }


}
