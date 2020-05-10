/**
 * Created by joenggyu0@gmail.com on 4/9/20
 * Github : http://github.com/lenkim
 */

package org.likelen.example;


import org.likelen.example.di.Inject;

public class BookService {

    @Inject
    BookRepository bookRepository;
}
