# 리플랙션 이해하기.

해당 세미나는 외부교육으로 들은 더자바를 수강하고 시작하는 강의입니다.

*DI 를 통해 리플랙션 이해하기.*



## 1. 스프링의 Depedency Injection은 어떻게 동작할까?

... Live Coding

1. spring init 으로 프로젝트 만들기
2. book, bookSerivce, bookRepository 생성후 DI 하기.



## 2. 리플랙션 API - 클래스 정보 조회

리플렉션의 시작은 Class\<T>

- https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html


Class란 무엇인가?

![image-20200412164811722](https://tva1.sinaimg.cn/large/007S8ZIlgy1gdr16qho35j31ae0ceac7.jpg)



Instances of the class Class represent classes and interfaces in a running Java application. An enum is a kind of class and an annotation is a kind of interface. Every array also belongs to a class that is reflected as a Class object that is shared by all arrays with the same element type and number of dimensions. The primitive Java types (boolean, byte, char, short, int, long, float, and double), and the keyword void are also represented as Class objects.



Class 클래스의 인스턴스는 실행중인 Java 응용 프로그램의 클래스와 인터페이스를 나타냅니다. **Enum**은 일종의 클래스이고 **Annotation**은 일종의 인터페이스입니다. 또한, 모든 배열은 동일한 요소 유형 및 차원 수를 가진 모든 배열에서 공유하는 Class 객체로 반영되는 클래스에 속합니다. 기본 Java 유형 (boolean, bytes, char, short, int, long, float 및 double)과 키워드 void도 Class 객체로 표시됩니다.



 *Class* has no public constructor. Instead Class objects are constructed automatically by the Java Virtual Machine as classes are loaded and by calls to the defineClass method in the class loader.



 클래스에는 공개 생성자가 없습니다. 대신에 클래스가 로드 될 때 Java Virtual Machine과 Class loader에서 defineClass 메소드를 호출하여 클래스 오브젝트가 자동으로 구성됩니다.



```java
void printClassName(Object obj) {
  System.out.println("The class of " + obj +
                     " is " + obj.getClass().getName());
}
```





https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/package-summary.html



Provides classes and interfaces for obtaining reflective information about classes and objects. Reflection allows programmatic access to information about the fields, methods and constructors of loaded classes, and the use of reflected fields, methods, and constructors to operate on their underlying counterparts, within security restrictions.



 클래스와 객체에 대한 reflective information를 얻기위한 클래스와 인터페이스를 제공합니다. 리플렉션을 사용하면로드 된 클래스의 필드, 메서드 및 생성자에 대한 정보에 프로그래밍 방식으로 액세스하고 보안 제한 내에서 기본 필드에서 작동하는 반사 된 필드, 메서드 및 생성자를 사용할 수 있습니다.



![image-20200412170013707](https://tva1.sinaimg.cn/large/007S8ZIlgy1gdr1j81wptj315q0u0jy9.jpg)



이제 라이브 코딩으로...


1. `Class<T>`에 접근하는 방법

   - 타입.class
   - 인스턴스.getClass()
   - Class.forName("FQCN") if not exist, ClassNotFoundException


```java
// 타입을 통해
Class<Book> bookClass = Book.class;
// 인스턴스를 통해서
Book book = new Book();
Class<? extends Book> aClass = book.getClass();
// 이름을 통해서
Class<?> aClass1 = Class.forName("org.example.Book");
//클래스 로딩이 끝나면 타입을 만들어서 힙에 넣는다.
```

   

2. `Class<T>` 


- 필드(목록) 가져오기

```java
// only get public
Arrays.stream(bookClass.getFields()).forEach(System.out::println);
// all
Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);

```

   - 메소드 (목록) 가져오기

   ```java
// Get Method
Arrays.stream(bookClass.getMethods()).forEach(a -> {
  System.out.println(a);
});
   ```

   - 상위 클래스 가져오기

   ```java
// Get parent Class
Class<? super MyBook> superclass = MyBook.class.getSuperclass();
System.out.println(superclass);
   ```

   - 인터페이스(목록) 가져오기

   ```java
Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);
   ```

   - 접근제어자 분류 통해 가져오기

   ```java
// Get Modifier
Arrays.stream(Book.class.getDeclaredFields()).forEach(field {
  int modifiers = field.getModifiers();
  System.out.println(field);
  System.out.println(Modifier.isPrivate(modifiers));
  System.out.println(Modifier.isStatic(modifiers));
});
   ```

   - 애노테이션 가져오기

   ```java
Arrays.stream(Book.class.getDeclaredAnnotations()).forEach(System.out::println);
   ```

   - 생성자 가져오기

   ```java
Class<?> aClass = Class.forName("org.example.Book");
Constructor<?> constructor = aClass.getConstructor(String.class);

Book book = (Book) constructor.newInstance("myBook");
System.out.println(book);
   ```

   

## 3. 애노테이션과 리플랙션

```java
public @interface MyAnnotation {
}

// getAnnotation() 으로 하면 가져오지 못한다. 왜?
```

이렇게 하면 자바의 컴파일러는 주석과 비슷하게 가져온다.

클래스영역까지는 데이터가 남지만, 바이트 코드를 로딩했을 때 애노테이션 정보는 빼고 가져온다. 그러므로. 런타임까지 같이 가져오고 싶다면. > `@Retention(RetentionPolicy.RUNTIME)`

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
}

// 붙여줌.
```

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Retention(RetentionPolicy.CLASS)
@Retention(RetentionPolicy.RUNTIME)  --- 1
@Target({ElementType.FIELD, ElementType.TYPE}) -- 2
@Inherited --- 3
public @interface MyAnnotation {

    //값을 가질 수 있는데, 제약적으로 가질 수 있음.
    String name() default "len"; -- 4
    int number() default 100;
		int value() default 100;
}

// ----

--- 1  
@Retention(RetentionPolicy.RUNTIME) // 컴파일 이후에도 JVM에 의해서 참조가 가능합니다.
(default) @Retention(RetentionPolicy.CLASS) // 컴파일러가 클래스를 참조할 때까지 유효합니다. 
@Retention(RetentionPolicy.SOURCE) // 어노테이션 정보는 컴파일 이후 없어집니다.
--- 2
@Target({
        ElementType.PACKAGE, // 패키지 선언시
        ElementType.TYPE, // 타입 선언시
        ElementType.CONSTRUCTOR, // 생성자 선언시
        ElementType.FIELD, // 멤버 변수 선언시
        ElementType.METHOD, // 메소드 선언시
        ElementType.ANNOTATION_TYPE, // 어노테이션 타입 선언시
        ElementType.LOCAL_VARIABLE, // 지역 변수 선언시
        ElementType.PARAMETER, // 매개 변수 선언시
        ElementType.TYPE_PARAMETER, // 매개 변수 타입 선언시
        ElementType.TYPE_USE // 타입 사용시
})
  
--- 3
@Inherit: 해당 애노테이션을 하위 클래스까지 전달할 것인가?
  
  
--- 4
```

![image-20200409133620692](https://tva1.sinaimg.cn/large/00831rSTgy1gdnes7id7bj30ge0a6755.jpg)



```java
getAnnotations()
: 상속받은 (@Inherit) 애노테이션까지 조회
getDeclaredAnnotations()
: 자기 자신에만 붙어있는 애노테이션 조회
```

![image-20200510170813065](https://tva1.sinaimg.cn/large/007S8ZIlgy1genf49i6rvj30u00vt7cf.jpg)



```java
// 특정 필드에 붙은 어노테이션을 확인
Arrays.stream(Book.class.getDeclaredFields()).forEach(f -> {
  Arrays.stream(f.getAnnotations()).forEach(a -> {
    if(a instanceof MyAnnotation){
      MyAnnotation myAnnotation = (MyAnnotation) a;
      System.out.println(myAnnotation.value());
      System.out.println(myAnnotation.number());
    }
  });
});
```

## 4. 리플랙션 API - 클래스 정보 수정 또는 실행

리플랙션을 활용해서 클래스의 정보를 수정하거나 메소드를 실행시켜봅시다.

```java
//Book.java
public Book() {
    }

    public Book(String b) {
        B = b;
    }

    @MyAnnotation
    public static String A = "A";
    private String B = "B";

    private void c(){
        System.out.println("C");
    }

    public void d(){
        System.out.println("D");
    }

    public int sum(int left, int right){
        return left + right;
    }
}

//App.java
public App() {

  Class<?> aClass = Class.forName("org.example.Book");
  // 권장하는 인스턴스를 만드는 방법은...
  Constructor<?> constructor = aClass.getConstructor(null);
  Book book = (Book) constructor.newInstance();
  
  // 어떤 파라미터를 가진 생성자를 만들고 싶다면?
  Constructor<?> constructor = aClass.getConstructor(String.class);
  Book book = (Book) constructor.newInstance("myBook");
  	
  System.out.println(book); // 존재 유무 확인 가능

  Field a = Book.class.getDeclaredField("A"); // static 이기 때문에 누구나 다 가능.
  System.out.println(a.get(null)); 
  a.set(null, "AAAAAAAA");
  System.out.println(a.get(null)); // "AAAAAAA"

  Field b = Book.class.getDeclaredField("B");
  b.setAccessible(true); // private 
  System.out.println(b.get(book)); // 특정 인스턴스를 거쳐서 가져와야 한다.
  b.set(book, "BBBBBB");
  System.out.println(b.get(book));

  Method c = Book.class.getDeclaredMethod("c"); //method도 동일.
  c.setAccessible(true);
  c.invoke(book);

  Method d = Book.class.getDeclaredMethod("sum", int.class, int.class);
  int invoke = (int) d.invoke(book, 1, 2);
  System.out.println(invoke);
}
```



## 5. 나만의 DI 프레임워크 만들기.

```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

}

// ---

```

... make Test Case 

```java
public class ContainerServiceTest {

    @Test
    public void getObject() {
      return null;
    }
}
```

... make all fail



// 테스트 패키지에서 `BookService, BookRepository.java` 를 만들고, BookService 에 BookRepository 에 `@inject`  활용



```java
 //ContainerService.java
public class ContainerService {
	public static <T> T getObject(Class<T> classType){
    return null;
  }
}
```



태스트 패키지에 있는 BookSerive, BookRepository.java 접근이 불가하기 때문에 리플렉션을 활용해야 한다.

classType.getConstructor(null).newInstance();

```java
private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor(null).newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
```

```java
public class ContainerServiceTest {

    @Test
    public void getObject_BookRepository() {
//        ContainerService.getObject(BookRepository.class);
        BookRepository object = ContainerService.getObject(BookRepository.class);
        Assert.assertNotNull(object);
    }

    @Test
    public void getObject_BookService() {
//        ContainerService.getObject(BookRepository.class);
        BookService object = ContainerService.getObject(BookService.class);
        Assert.assertNotNull(object);
       // 여기서 실패하게 될 것. 여기를 처리해야 함.
        Assert.assertNotNull(object.bookRepository);
      
    }
}
```



그 다음 타입이 `Inject.class` 타입에 대한 주입을 처리한다.

```java
public static <T> T getObject(Class<T> classType){
        T instance = createInstance(classType);
        Arrays.stream(classType.getDeclaredFields()).forEach( f -> {
            if (f.getAnnotation(Inject.class) != null){
                Object fieldInstance = createInstance(f.getType());
                f.setAccessible(true);
                try {
                    f.set(instance, fieldInstance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return instance;
    }
```



위 테스트 코드를 다시 동작 시키면?



끝!

