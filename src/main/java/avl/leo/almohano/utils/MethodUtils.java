package avl.leo.almohano.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MethodUtils {

    // Updated method that correctly extracts the 'name' field from enum instances
    public static <E extends Enum<E>> List<String> getEnumNames(Class<E> enumClass) {
        List<String> names = new ArrayList<>();

        // Iterate over enum constants
        for (E enumConstant : enumClass.getEnumConstants()) {
            try {
                // Access the 'name' field via reflection
                Field nameField = enumClass.getDeclaredField("name");
                nameField.setAccessible(true);  // Allow access to private fields

                // Get the value of 'name' for the current enum constant
                String nameValue = (String) nameField.get(enumConstant);
                names.add(nameValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return names;
    }

}
