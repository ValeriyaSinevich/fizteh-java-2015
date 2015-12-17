package ru.fizteh.fivt.students.ValeriyaSinevich.miniORM;


import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class TypeConverter {
    private static HashMap<Class, String> classTypes;
    private static HashMap<String, String> primeTypes;

    public TypeConverter() {
        primeTypes = new HashMap<>();
        primeTypes.put("int", "INTEGER");
        primeTypes.put("boolean", "BOOLEAN");
        primeTypes.put("byte", "INTEGER");
        primeTypes.put("short", "INTEGER");
        primeTypes.put("long", "BIGINT");
        primeTypes.put("float", "FLOAT");
        primeTypes.put("double", "DOUBLE");

        classTypes = new HashMap<>();
        classTypes.put(Integer.class, "INTEGER");
        classTypes.put(Boolean.class, "BOOLEAN");
        classTypes.put(Byte.class, "TINYINT");
        classTypes.put(Short.class, "SMALLINT");
        classTypes.put(Long.class, "BIGINT");
        classTypes.put(Double.class, "DOUBLE");
        classTypes.put(Float.class, "FLOAT");
        classTypes.put(Time.class, "TIME");
        classTypes.put(Date.class, "DATE");
        classTypes.put(Timestamp.class, "TIMESTAMP");
        classTypes.put(Character.class, "CHAR");
        classTypes.put(String.class, "VARCHAR(2000)");
        classTypes.put(UUID.class, "UUID");
    }

    public static String convertType(Class<?> type) {
        if (primeTypes.containsKey(type.toString())) {
            return primeTypes.get(type.toString());
        } else if (classTypes.containsKey(type)) {
            return classTypes.get(type);
        }
        return null;
    }
}
