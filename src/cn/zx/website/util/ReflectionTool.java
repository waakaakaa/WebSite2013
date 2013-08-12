package cn.zx.website.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import cn.zx.website.annotation.ActionUrl;

public class ReflectionTool {
	public static Set<Class<?>> getClasses(String packageName) {
		Set<Class<?>> classes = new HashSet<>();
		Enumeration<URL> e;
		try {
			e = Thread.currentThread().getContextClassLoader()
					.getResources(packageName.replace(".", "/"));
			while (e.hasMoreElements()) {
				URL url = e.nextElement();
				String packagePath = URLDecoder.decode(url.getFile(), "UTF-8");
				findAndAddClassesInPackageByFile(packageName, packagePath,
						true, classes);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return classes;
	}

	private static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] dirFiles = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		for (File file : dirFiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					classes.add(Thread.currentThread().getContextClassLoader()
							.loadClass(packageName + "." + className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Set<Class<?>> classes = getClasses("cn.zx.test.action");
		for (Class<?> c : classes) {
			System.out.println(c.getName());
			Method[] methods = c.getDeclaredMethods();
			for (Method m : methods) {
				System.out.println("  " + m.getName());
				ActionUrl url = m.getAnnotation(ActionUrl.class);
				System.out.println("    " + url.path());
			}
		}
	}
}