package Elipse.Core.Scripting;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;

public class ScriptEngine {

	private List<BaseComponent> components = new ArrayList<BaseComponent>();

	public ScriptEngine() {

	}

	public void LoadJar(String jar) {
		components.clear();

		try {
			URL jarFile = new File(jar).toURI().toURL();
			URLClassLoader classLoader = new URLClassLoader(new URL[] { jarFile }, this.getClass().getClassLoader());

			Enumeration<JarEntry> entries = new JarFile(new File(jar)).entries();

			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.getName().endsWith(".class")) {
					String className = entry.getName().replace("/", ".");
					className = className.substring(0, className.length() - 6);

					Class<?> clazz = classLoader.loadClass(className);

					if (BaseComponent.class.isAssignableFrom(clazz)) {
						BaseComponent exampleLibClass = (BaseComponent) clazz.newInstance();
						components.add(exampleLibClass);
					}

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public List<BaseComponent> GetComponents() {
		return components;
	}
}
