package ElipseEditor.ImguiComponent;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector4f;

import Elipse.Core.Logger;
import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Assets.Editor.EditorAssetManager;
import Elipse.Core.Input.Input;
import Elipse.Core.Input.KeyCode;
import Elipse.Core.Maths.Vector;
import Elipse.Core.Project.Project;
import Elipse.Core.Tilemap.Tilemap;
import Elipse.Renderer.OrthograhicCamera;
import Elipse.Renderer.Batching.RenderBatch;
import Elipse.Renderer.Batching.Sprite;
import Elipse.Renderer.Batching.Spritesheet;
import Elipse.Renderer.Opengl.Framebuffer;
import Elipse.Renderer.Opengl.RendererApi;
import Elipse.Renderer.Opengl.Texture.Texture2D;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;

public class TilemapEditor {

	private Framebuffer tilemapFbo = new Framebuffer(200, 200);
	private int tilemapFboWidth = 200, tilemapFboHeight = 200;

	private Framebuffer SpritesheetFbo = new Framebuffer(200, 200);
	private int SpritesheetFboWidth = 200, SpritesheetFboHeight = 200;

	private int[] spriteHeight = { 16 }, spriteWidth = { 16 };
	private Texture2D spritesheetTexture = null;

	public TilemapEditor() {
		spritesheetCamera.Move(new Vector());
		tilemapFboCamera.Move(new Vector());
	}

	public void OnImGui() {
		ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0f, 0f);
		if (ImGui.begin("Tilemap editor", ImGuiWindowFlags.NoScrollbar)) {
			ImVec2 windowSize = new ImVec2();
			ImGui.getContentRegionAvail(windowSize);

			if (windowSize.x != tilemapFboWidth || windowSize.y != tilemapFboHeight) {
				tilemapFboWidth = (int) windowSize.x;
				tilemapFboHeight = (int) windowSize.y;

				tilemapFbo.Resize(tilemapFboWidth, tilemapFboHeight);

				tilemapFboCamera.SetFOV(5);
				tilemapFboCamera.Resize(tilemapFboWidth, tilemapFboHeight);
			}

			tilemapFbo.Bind();
			RendererApi.SetViewport(tilemapFboWidth, tilemapFboHeight);
			RendererApi.setClearColor(0.1f, 0.1f, 0.1f);
			RendererApi.clear();

			TilemapEditorRender();

			tilemapFbo.Unbind();

			ImGui.image(tilemapFbo.GetTexture().GetTextureId(), windowSize.x, windowSize.y, 0, 1, 1, 0);
		}
		ImGui.end();

		if (ImGui.begin("Spritesheet selector")) {
			ImVec2 windowSize = new ImVec2();
			ImGui.getContentRegionAvail(windowSize);

			if (windowSize.x != SpritesheetFboWidth) {
				SpritesheetFboWidth = (int) windowSize.x;
				SpritesheetFboHeight = (int) 100;

				SpritesheetFbo.Resize(SpritesheetFboWidth, SpritesheetFboHeight);

				spritesheetCamera.SetFOV(2);

				spritesheetCamera.Resize((int) windowSize.x, 100);
			}

			SpritesheetFbo.Bind();
			RendererApi.SetViewport(SpritesheetFboWidth, SpritesheetFboHeight);
			RendererApi.setClearColor(0.1f, 0.1f, 0.1f);
			RendererApi.clear();

			SpritesheetEditorRender();

			SpritesheetFbo.Unbind();

			ImGui.image(SpritesheetFbo.GetTexture().GetTextureId(), windowSize.x, 100, 0, 1, 1, 0);

			if (ImGui.selectable("Select Texture"))
				AssetPicker.Open("Tilemapeditor", AssetType.TEXTURE2D);

			if (AssetPicker.Display("Tilemapeditor")) {
				this.spritesheetTexture = AssetPicker.GetSelected(Texture2D.class);
			}

			ImGui.dragInt("Sprite Height", spriteHeight);
			ImGui.dragInt("Sprite Width", spriteWidth);

			if (ImGui.button("Create Spritesheet")) {
				if (this.spritesheetTexture == null || spriteHeight[0] == 0 || spriteWidth[0] == 0) {
					Logger.c_warn("Please select a texture and sprite dimensions before creating a spritesheet");
					return;
				}

				Spritesheet = new Spritesheet(spritesheetTexture, spriteWidth[0], spriteHeight[0]);
				tilemap = new Tilemap(Spritesheet, 20, 10);
			}

			if (ImGui.button("Save all")) {
				EditorAssetManager assetManager = (EditorAssetManager) Project.GetActive().GetAssetManager();

				assetManager.AddAssetToProject(Spritesheet, Project.GetActive().GetAssetDir() + "\\Spritesheet.elsheet");
				assetManager.AddAssetToProject(tilemap, Project.GetActive().GetAssetDir() + "\\Tilemap.elmap");
			}
		}
		ImGui.end();
		ImGui.popStyleVar();
	}

	private RenderBatch renderBatch = new RenderBatch(2000);
	private Spritesheet Spritesheet;

	private OrthograhicCamera spritesheetCamera = new OrthograhicCamera();

	private Vector SpritesheetCameraPos = new Vector();

	private void SpritesheetEditorRender() {
		if (Spritesheet == null)
			return;

		if (ImGui.isWindowHovered() && Input.IsMouseButtonPressed(KeyCode.EL_MOUSE_BUTTON_2)) {
			SpritesheetCameraPos.add((float) Input.GetDeltaMouse().x, 0);
			spritesheetCamera.Move(SpritesheetCameraPos);
		}

		Vector2d mousePos = Input.GetMousePos();
		mousePos.sub(ImGui.getWindowPosX(), ImGui.getWindowPosY());
		mousePos.div(ImGui.getContentRegionAvailX(), 100);
		mousePos.mul(2);
		mousePos.sub(1, 1);

		Vector4f newPos = new Vector4f((float) mousePos.x, (float) mousePos.y, 0, 1);

		newPos.mul(spritesheetCamera.GetInversView().mul(spritesheetCamera.GetInversProjection(), new Matrix4f()));

		if (Input.IsMouseButtonPressed(KeyCode.EL_MOUSE_BUTTON_1) && ImGui.isWindowHovered()) {
			int index = (int) Math.floor(newPos.x + 0.5f);
			if (index >= 0 && index < Spritesheet.getSprites().size()) {
				this.currentTileIndex = index;
			}
		}

		renderBatch.Begin();

		List<Sprite> sprites = Spritesheet.getSprites();

		for (int i = 0; i < sprites.size(); i++) {
			renderBatch.AddSprite(sprites.get(i), new Vector(i, 0));
		}

		renderBatch.reloadData();
		renderBatch.render(spritesheetCamera.GetView(), spritesheetCamera.GetProjection());
	}

	private OrthograhicCamera tilemapFboCamera = new OrthograhicCamera();
	private Vector tilemapFboCameraPos = new Vector();

	private int currentTileIndex = 0;

	private Tilemap tilemap;

	private void TilemapEditorRender() {

		if (tilemap == null)
			return;

		if (ImGui.isWindowHovered() && Input.IsMouseButtonPressed(KeyCode.EL_MOUSE_BUTTON_2)) {
			tilemapFboCameraPos.add((float) Input.GetDeltaMouse().x / 10f, (float) -Input.GetDeltaMouse().y / 10f);
			tilemapFboCamera.Move(tilemapFboCameraPos);
		}

		Vector2d mousePos = Input.GetMousePos();
		mousePos.sub(ImGui.getWindowPosX(), ImGui.getWindowPosY());
		mousePos.div(ImGui.getContentRegionAvailX(), ImGui.getContentRegionAvailY());
		mousePos.mul(2);
		mousePos.sub(1, 1);

		Vector4f newPos = new Vector4f((float) mousePos.x, (float) -mousePos.y, 0, 1);

		newPos.mul(tilemapFboCamera.GetInversView().mul(tilemapFboCamera.GetInversProjection(), new Matrix4f()));

		if (Spritesheet != null) {
			renderBatch.Begin();

			renderBatch.AddSprite(Spritesheet.getSprites().get(
					currentTileIndex), new Vector((float) Math.round(newPos.x), (float) Math.round(newPos.y)));

			for (int x = 0; x < tilemap.GetWidth(); x++) {
				for (int y = 0; y < tilemap.GetHeight(); y++) {
					renderBatch.AddSprite(Spritesheet.getSprites().get(tilemap.GetTile(x, y)), new Vector(x, y));
				}
			}

			renderBatch.reloadData();
			renderBatch.render(tilemapFboCamera.GetView(), tilemapFboCamera.GetProjection());
		}

		if (Input.IsMouseButtonPressed(KeyCode.EL_MOUSE_BUTTON_1) && ImGui.isWindowHovered()) {
			int x = Math.round(newPos.x);
			int y = Math.round(newPos.y);
			if (x >= 0 && x < tilemap.GetWidth() && y >= 0 && y < tilemap.GetHeight()) {
				tilemap.SetTile(x, y, currentTileIndex);
			}
		}

	}
}
