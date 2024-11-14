package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;
import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
        for (Method method : Address.class.getDeclaredMethods()) {

            // Проверяем, есть ли у метода аннотация @LogExecutionTime
            if (method.isAnnotationPresent(Inspect.class)) {

                var startTime = System.currentTimeMillis();


                System.out.println(String.format("Method %s returns a value of type %s", method.getName(), method.getReturnType().getSimpleName()));
            }
        }
    }
        // END
}

