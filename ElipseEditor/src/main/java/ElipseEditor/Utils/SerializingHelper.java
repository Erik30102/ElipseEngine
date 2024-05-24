package ElipseEditor.Utils;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Elipse.Core.Logger;
import Elipse.Core.Project.Project;

public class SerializingHelper {
	public static String Serialize(Type type, Object serilaizer, Object src) {
		Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(type, serilaizer).create();
		return gson.toJson(src);
	}

	public static boolean SaveToPath(String RelativePath, String content) {
		try {
			Files.writeString(Path.of(Project.GetActive().GetAssetDir() + "/" + RelativePath), content);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Logger.c_error("Failed to write file to assetdir at path: " + RelativePath);
			return false;
		}
	}
}
