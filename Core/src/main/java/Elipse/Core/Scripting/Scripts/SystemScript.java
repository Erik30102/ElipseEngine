package Elipse.Core.Scripting.Scripts;

import Elipse.Core.Scripting.Script;

public class SystemScript extends Script {

	public SystemScript(Class<?> baseClazz) {
		super(baseClazz);
	}

	@Override
	public ScriptType GetScriptType() {
		return ScriptType.SYSTEM;
	}
}
