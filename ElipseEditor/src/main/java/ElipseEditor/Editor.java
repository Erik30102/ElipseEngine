package ElipseEditor;

import Elipse.Core.Application;

// import java.io.File;
// import java.io.IOException;
// import java.net.MalformedURLException;
// import java.net.URL;
// import java.net.URLClassLoader;
// import java.util.Enumeration;
// import java.util.jar.JarEntry;
// import java.util.jar.JarFile;

public class Editor extends Application {

	public Editor() {
		super(900, 600, "Elipse Editor", true);

		/*
		 * try {
		 * 
		 * URL jarFile = new
		 * File("Sandbox/build/libs/Sandbox-1.0-SNAPSHOT.jar").toURI().toURL();
		 * URLClassLoader classLoader = new URLClassLoader(new URL[] { jarFile },
		 * this.getClass().getClassLoader());
		 * 
		 * Enumeration<JarEntry> entries = new JarFile(new
		 * File("Sandbox/build/libs/Sandbox-1.0-SNAPSHOT.jar")).entries();
		 * 
		 * while (entries.hasMoreElements()) {
		 * JarEntry entry = entries.nextElement();
		 * if (entry.getName().endsWith(".class")) {
		 * String className = entry.getName().replace("/", ".");
		 * className = className.substring(0, className.length() - 6);
		 * 
		 * Class<?> clazz = classLoader.loadClass(className);
		 * 
		 * if (ExampleLibClass.class.isAssignableFrom(clazz)) {
		 * ExampleLibClass exampleLibClass = (ExampleLibClass) clazz.newInstance();
		 * exampleLibClass.OnInteract();
		 * }
		 * 
		 * }
		 * }
		 * } catch (MalformedURLException e) {
		 * e.printStackTrace();
		 * } catch (IOException e) {
		 * e.printStackTrace();
		 * } catch (ClassNotFoundException e) {
		 * e.printStackTrace();
		 * } catch (InstantiationException e) {
		 * e.printStackTrace();
		 * } catch (IllegalAccessException e) {
		 * e.printStackTrace();
		 * }
		 */

	}

	public static void main(String[] args) {
		new Editor().Run();
	}

	@Override
	public void OnInitialize() {
		this.PushLayer(new EditorLayer());
	}
}
