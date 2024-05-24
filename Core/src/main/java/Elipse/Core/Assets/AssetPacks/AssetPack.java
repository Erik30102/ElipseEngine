package Elipse.Core.Assets.AssetPacks;

public class AssetPack {
	public enum HEADERS {
		ASSETPACK(0),
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

	// private byte[] appBinary;
}
