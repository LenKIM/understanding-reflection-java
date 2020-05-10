package org.likelen.example;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {

//        accessClassInfo();

        Class<Book> bookClass = Book.class;
//        accessPropertyOrFields(bookClass);
//        accessMethods(bookClass);
//        accessParentClass();
//        accessInterfaces();
//        accessModifier();
//        accessAnnotation();
//        accessConstructor();

//        accessAnnotationValue();

        Class<?> aClass = Class.forName("org.likelen.example.Book");
        // 권장하는 인스턴스를 만드는 방법은...
        Constructor<?> constructor = aClass.getConstructor( null);
        Book book = (Book) constructor.newInstance();

        changeStaticField();
        changePublicField(book);
        executePrivateMethod(book);
        executePublicMethod(book);
        executeMethodWithParameters(book);
    }

    private static void executeMethodWithParameters(Book book) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method sum = Book.class.getDeclaredMethod("sum", int.class, int.class);
        int invoke = (int) sum.invoke(book, 1, 2);
        System.out.println(invoke);
    }

    private static void executePublicMethod(Book book) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method d = Book.class.getDeclaredMethod("d"); //Method 동일.
        d.invoke(book);
    }

    private static void executePrivateMethod(Book book) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method c = Book.class.getDeclaredMethod("c"); //Method 동일.
        c.setAccessible(true);
        c.invoke(book);
    }

    private static void changePublicField(Book book) throws NoSuchFieldException, IllegalAccessException {
        Field b = Book.class.getDeclaredField("b");
        b.setAccessible(true); // private
        System.out.println(b.get(book)); // 특정 인스턴스를 거쳐서 가져와야 한다.
        b.set(book, "change-b-to-B");
        System.out.println(b.get(book)); // "change-b-to-B"
    }

    // static 이기 때문에 누구나 다 가능.
    private static void changeStaticField() throws NoSuchFieldException, IllegalAccessException {
        Field a = Book.class.getDeclaredField("a");
        System.out.println(a.get(null));
        a.set(null, "change-a-to-A");
        System.out.println(a.get(null)); // "change-a-to-A"
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

//    private static Class<Book> accessClassInfo() throws ClassNotFoundException {
//        // By Type
//        Class<Book> bookClass = Book.class;
//        // By Instance
//        Book book = new Book();
//        Class<? extends Book> aClass = book.getClass();
//        // by name
//        Class<?> aClassByName = Class.forName("org.likelen.example.Book");
//        return bookClass;
//        //클래스 로딩이 끝나면 타입을 만들어서 힙 영역에 넣는다.
//    }
}
