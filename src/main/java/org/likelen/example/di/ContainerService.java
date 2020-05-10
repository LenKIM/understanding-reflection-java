/**
 * Created by joenggyu0@gmail.com on 5/10/20
 * Github : http://github.com/lenkim
 */

package org.likelen.example.di;

import java.lang.reflect.InvocationTargetException;

public class ContainerService {

    public static <T> T getObject(Class<T> classType) {
        try {
             T instance = classType.getConstructor(null).newInstance();
             return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
