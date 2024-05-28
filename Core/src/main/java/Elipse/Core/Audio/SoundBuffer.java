package Elipse.Core.Audio;

import org.lwjgl.openal.AL11;
import org.lwjgl.stb.STBVorbisInfo;

public class SoundBuffer {
	private final int bufferId;

	public SoundBuffer(String filepath) {
		this.bufferId = AL11.alGenBuffers();
		STBVorbisInfo info = STBVorbisInfo.malloc();
	}
}
