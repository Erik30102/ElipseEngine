package Elipse.Core.Scripting;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import Elipse.Core.Logger;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;

public class ScriptEngine {

	private List<Class<? extends BaseComponent>> components = new ArrayList<Class<? extends BaseComponent>>();
	private URLClassLoader classLoader;

	private static ScriptEngine INSTANCE;

	public ScriptEngine() {
		INSTANCE = this;
	}

	@SuppressWarnings("unchecked")
	public void LoadJar(String jar) {
		components.clear();

		try {
			URL jarFile = new File(jar).toURI().toURL();
			classLoader = new URLClassLoader(new URL[] { jarFile }, this.getClass().getClassLoader());

			JarFile jarFileContent = new JarFile(new File(jar));

			Enumeration<JarEntry> entries = jarFileContent.entries();

			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.getName().endsWith(".class")) {
					String className = entry.getName().replace("/", ".");
					className = className.substring(0, className.length() - 6);

					Class<?> clazz = classLoader.loadClass(className);

					if (BaseComponent.class.isAssignableFrom(clazz)) {
						components.add((Class<? extends BaseComponent>) clazz);
					}

				}
			}

			jarFileContent.close();

		} catch (Exception e) {
			Logger.c_error("Failed to load Content script: " + jar);
			e.printStackTrace();
		}
	}

	public List<Class<? extends BaseComponent>> GetComponents() {
		return components;
	}

	public URLClassLoader GetClassLoader() {
		return classLoader;
	}

	public void Dispose() {
		try {
			classLoader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ScriptEngine GetInstance() {
		return INSTANCE;
	}
}
