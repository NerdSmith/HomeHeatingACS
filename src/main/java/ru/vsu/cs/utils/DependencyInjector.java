package ru.vsu.cs.utils;

import ru.vsu.cs.annotations.Autowired;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Objects;

public class DependencyInjector {
    private static final String basePkg = "ru.vsu.cs";

    public static void inject() throws Exception {
        inject(basePkg);
    }

    public static void inject(String targetPkg) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = targetPkg.replace('.', '/');
        URL resource = classLoader.getResource(path);
        if (resource == null) {
            throw new Exception("Can't get resource");
        }
        File resFile = new File(resource.toURI());

        for (File clsFile : Objects.requireNonNull(resFile.listFiles())) {
            String normalizedName = targetPkg + "." + clsFile.getName().replace(".class", "");
            if (clsFile.isDirectory()) {
                inject(normalizedName);
            }
            else {
                Class<?> clazz = Class.forName(normalizedName);
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Object dependency = getDependency(field.getType());
                        field.setAccessible(true);
                        field.set(null, dependency);
                    }
                }
            }
        }
    }

    private static Object getDependency(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
