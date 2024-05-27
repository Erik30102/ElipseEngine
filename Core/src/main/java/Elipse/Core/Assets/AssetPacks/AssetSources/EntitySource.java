package Elipse.Core.Assets.AssetPacks.AssetSources;

import java.io.Serializable;

import Elipse.Core.Maths.Vector;

public class EntitySource implements Serializable {

	public String name;
	public Vector position;
	public Vector scale;
	public float rotation;
	public ComponentSource[] components;

}
