package org.likelen.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

//        accessClassInfo();

        Class<Book> bookClass = Book.class;
//        accessPropertyOrFields(bookClass);
//        accessMethods(bookClass);
//        accessParentClass();
//        accessInterfaces();
//        accessModifier();
//        accessAnnotation();
//        accessConstructor();

        accessAnnotationValue();
    }

    private static void accessAnnotationValue() {
        // 특정 필드에 붙은 어노테이션을 확인
        Arrays.stream(Book.class.getDeclaredFields()).forEach(f -> {
            Arrays.stream(f.getAnnotations()).forEach(a -> {
                if (a instanceof MyAnnotation) {
                    MyAnnotation myAnnotation = (MyAnnotation) a;
                    System.out.println(myAnnotation.value());
                    System.out.println(myAnnotation.number());
                }
            });
        });
    }

    private static void accessConstructor() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> aClass = Class.forName("org.likelen.example.Book");
        Constructor<?> constructor = aClass.getConstructor(String.class);

        Book book = (Book) constructor.newInstance("myBook");
        System.out.println(book);
        System.out.println(book.getTitle());
    }

    private static void accessAnnotation() {
        Arrays.stream(Book.class.getDeclaredAnnotations()).forEach(System.out::println);
    }

    private static void accessModifier() {
        // Get Modifier
        Arrays.stream(Book.class.getDeclaredFields()).forEach(field -> {
            int modifiers = field.getModifiers();
            System.out.println(field);
            System.out.println(Modifier.isPrivate(modifiers));
            System.out.println(Modifier.isStatic(modifiers));
        });
    }

    private static void accessInterfaces() {
        Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);
    }

    private static void accessParentClass() {

        // Get parent Class
        Class<? super MyBook> superclass = MyBook.class.getSuperclass();
        System.out.println(superclass);
    }

    private static void accessMethods(Class<Book> bookClass) {

        // Get Method
        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);
    }

    private static void accessPropertyOrFields(Class<Book> bookClass) {

        // only get public
        Arrays.stream(bookClass.getFields()).forEach(System.out::println);

        // all
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);

    }

    private static Class<Book> accessClassInfo() throws ClassNotFoundException {
        // By Type
        Class<Book> bookClass = Book.class;
        // By Instance
        Book book = new Book();
        Class<? extends Book> aClass = book.getClass();
        // by name
        Class<?> aClassByName = Class.forName("org.likelen.example.Book");
        return bookClass;
        //클래스 로딩이 끝나면 타입을 만들어서 힙 영역에 넣는다.
    }
}
