/**
 * Created by joenggyu0@gmail.com on 4/9/20
 * Github : http://github.com/lenkim
 */

package org.likelen.example;

import org.junit.Assert;
import org.junit.Test;
import org.likelen.example.di.ContainerService;

public class ContainerServiceTest {

    @Test
    public void getObject_BookRepository() {
        BookRepository object = ContainerService.getObject(BookRepository.class);
        Assert.assertNotNull(object);
    }

    @Test
    public void getObject_BookService(){
        BookService object = ContainerService.getObject(BookService.class);
        Assert.assertNotNull(object);
        Assert.assertNotNull(object.bookRepository);
    }
}
