package Elipse.Core.Scripting.Scripts;

import Elipse.Core.Assets.Asset.AssetType;
import Elipse.Core.Scripting.Script;

public class ComponentScript extends Script {

	public ComponentScript(Class<?> baseClazz) {
		super(baseClazz);
	}

	@Override
	public ScriptType GetScriptType() {
		return ScriptType.COMPONENT;
	}

}
