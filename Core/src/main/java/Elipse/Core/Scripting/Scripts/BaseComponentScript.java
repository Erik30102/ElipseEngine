package Elipse.Core.Scripting.Scripts;

import Elipse.Core.Scripting.Script;

public class BaseComponentScript extends Script {

	public BaseComponentScript(Class<?> baseClazz) {
		super(baseClazz);
	}

	@Override
	public ScriptType GetScriptType() {
		return ScriptType.BASECOMPONENT;
	}

}
