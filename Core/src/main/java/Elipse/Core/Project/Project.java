package Elipse.Core.Project;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import Elipse.Core.Logger;
import Elipse.Core.Assets.AssetManager;
import Elipse.Core.Assets.EditorAssetManager;
import Elipse.Core.ECS.Scene;
import Elipse.Utils.Serializer.LocalSceneSerializer;
import Elipse.Utils.Serializer.UUIDSerializer;

public class Project {

	private transient static Project INSTANCE;

	private String name;
	private UUID StartScene;
	private String ProjectDir, AssetDir, AssetMapPath, ScriptProject;

	// TODO: dynamic when i implement a runtime Asset Manager
	private transient AssetManager assetManager = new EditorAssetManager();

	public Project(String dir, String Name) {
		ProjectDir = dir;
		AssetDir = dir + "/Assets";
		AssetMapPath = AssetDir + "/AssetMap.elbank";
		ScriptProject = dir + "/Scripts";
		name = Name;

		try {
			Files.createDirectories(Path.of(ProjectDir));
			Files.createDirectories(Path.of(AssetDir));
			Files.createDirectories(Path.of(ScriptProject));
		} catch (IOException e) {
			Logger.c_error("Unable to Create Project at location: " + ProjectDir);
			e.printStackTrace();

			return;
		}

		INSTANCE = this;

		Scene startScene = new Scene();
		startScene.Create("Das ist ein Test");

		Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Scene.class, new LocalSceneSerializer())
				.create();

		String json = gson.toJson(startScene);
		try {
			Files.writeString(Path.of(AssetDir + "/StartScene.el"), json);
			UUID id = ((EditorAssetManager) assetManager).ImportAsset(AssetDir + "/StartScene.el");

			this.StartScene = id;
		} catch (IOException e) {
			Logger.c_error("Project could not be saved");
			e.printStackTrace();
		}
		Save();
	}

	/**
	 * load project from path
	 * 
	 * @param path path of the .elprj file of the project
	 */
	public static Project LoadProject(String path) {
		try {
			String contents = Files.readString(Path.of(path));

			Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(UUID.class, new UUIDSerializer()).create();
			Project project = gson.fromJson(contents, Project.class);
			INSTANCE = project;

			project.assetManager = new EditorAssetManager();

			((EditorAssetManager) project.GetAssetManager()).DeserializeAssetBank();

			return project;
		} catch (IOException e) {
			Logger.c_error("Project at location: " + path + " could not be loaded");
			e.printStackTrace();
		}

		return null;
	}

	public String GetProjectDir() {
		return ProjectDir;
	}

	public String GetAssetDir() {
		return AssetDir;
	}

	public String GetAssetMapPath() {
		return AssetMapPath;
	}

	public AssetManager GetAssetManager() {
		return assetManager;
	}

	public UUID GetStartScene() {
		return StartScene;
	}

	public String GetName() {
		return name;
	}

	public String GetScriptProjectPath() {
		return ScriptProject;
	}

	public static Project GetActive() {
		return INSTANCE;
	}

	public void Save() {
		Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(UUID.class, new UUIDSerializer()).create();
		String json = gson.toJson(this);

		try {
			Files.writeString(Path.of(ProjectDir + "/Project.elprj"), json);
		} catch (IOException e) {
			Logger.c_error("Project could not be saved");
			e.printStackTrace();
		}

		// TODO: clean up

		Gson scene_gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Scene.class, new LocalSceneSerializer())
				.create();

		String scene_json = scene_gson.toJson((Scene) this.assetManager.GetAsset(StartScene));
		try {
			Files.writeString(Path.of(AssetDir + "/StartScene.el"), scene_json);
		} catch (IOException e) {
			Logger.c_error("Project could not be saved");
			e.printStackTrace();
		}
	}
}
