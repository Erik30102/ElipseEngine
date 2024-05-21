package Elipse.Core.Scripting.Scripts;

import Elipse.Core.Scripting.Script;

public class ScriptableObjectScript extends Script {

	public ScriptableObjectScript(Class<?> baseClazz) {
		super(baseClazz);
	}

	@Override
	public ScriptType GetScriptType() {
		return ScriptType.SCRIPTABLEOBJ;
	}

}
