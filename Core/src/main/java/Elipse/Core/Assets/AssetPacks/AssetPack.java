package Elipse.Core.Assets.AssetPacks;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AssetPack implements Serializable {
	public enum HEADERS {
		TEXTURE2D(1),
		SCENE(2);

		private int header;

		public int getHeader() {
			return header;
		}

		private HEADERS(int header) {
			this.header = header;
		}

		public static HEADERS getHeader(int header) {
			for (HEADERS h : HEADERS.values()) {
				if (h.getHeader() == header) {
					return h;
				}
			}
			return null;
		}
	}

	// private static final long serialVersion = 1;

	private byte[] appBinary;
	private Map<String, AssetInfo> assetInfoMap = new HashMap<>();

	public void SetAppBinary(byte[] appBinary) {
		this.appBinary = appBinary;
	}

	public void AddAsset(String id, AssetInfo assetInfo) {
		assetInfoMap.put(id, assetInfo);
	}

	public byte[] GetAppBinary() {
		return appBinary;
	}

	public Map<String, AssetInfo> GetAssetInfoMap() {
		return assetInfoMap;
	}

	public AssetInfo GetAssetInfo(UUID id) {
		return assetInfoMap.get(id);
	}

	public static void SaveToDisk(String path, AssetPack assetPack) {
		try (FileOutputStream fileOut = new FileOutputStream(path)) {
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(assetPack);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static AssetPack LoadFromDisk(String path) {
		AssetPack assetPack = null;
		try (FileInputStream fileIn = new FileInputStream(path)) {
			ObjectInputStream in = new ObjectInputStream(fileIn);
			assetPack = (AssetPack) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return assetPack;
	}
}
