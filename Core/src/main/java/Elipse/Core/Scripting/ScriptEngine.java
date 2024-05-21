package Elipse.Core.Scripting;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import Elipse.Core.Logger;
import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;
import Elipse.Core.Scripting.Scripts.BaseComponentScript;
import Elipse.Core.Scripting.Scripts.ComponentScript;
import Elipse.Core.Scripting.Scripts.ScriptableObjectScript;
import Elipse.Core.Scripting.Scripts.SystemScript;

public class ScriptEngine {

	private Map<String, Script> ScriptDirectory = new HashMap<>();

	private URLClassLoader classLoader;

	private static ScriptEngine INSTANCE;

	public ScriptEngine() {
		INSTANCE = this;
	}

	/**
	 * Loads jar into script engine and makes the compoennts in the game logic jar
	 * available to internal engine use
	 * 
	 * @param jar
	 */
	public void LoadJar(String jar) {
		this.ScriptDirectory.clear();

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

					this.ImplmentClass(clazz);
				}
			}

			jarFileContent.close();

		} catch (Exception e) {
			Logger.c_error("Failed to load Content script: " + jar);
			e.printStackTrace();
		}
	}

	// TODO: refactor this is ass
	private void ImplmentClass(Class<?> clazz) {
		if (BaseComponent.class.isAssignableFrom(clazz)) {
			this.ScriptDirectory.put(clazz.getCanonicalName(), new BaseComponentScript(clazz));
		} else if (ScriptableObject.class.isAssignableFrom(clazz)) {
			this.ScriptDirectory.put(clazz.getCanonicalName(), new ScriptableObjectScript(clazz));
		} else if (System.class.isAssignableFrom(clazz)) {
			this.ScriptDirectory.put(clazz.getCanonicalName(), new SystemScript(clazz));
		} else if (Component.class.isAssignableFrom(clazz)) {
			this.ScriptDirectory.put(clazz.getCanonicalName(), new ComponentScript(clazz));
		} else {
			Logger.c_info("Could not load script: " + clazz.getCanonicalName()
					+ " either because it is helper class or the script type your trying to use is not implmented yet or mabey just so major engine fuckup");
		}
	}

	public URLClassLoader GetClassLoader() {
		return classLoader;
	}

	public Script GetScript(String type) {
		return this.ScriptDirectory.get(type);
	}

	/**
	 * Disposes the class loader used by the script engine to load all of the
	 * compoentns
	 */
	public void Dispose() {
		try {
			classLoader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the current instance of the script engine in a static way
	 */
	public static ScriptEngine GetInstance() {
		return INSTANCE;
	}
}
