package Elipse.Core.Scripting;

import java.lang.reflect.InvocationTargetException;

import Elipse.Core.Logger;
import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.ECSSystem;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;

public abstract class Script {

	private Class<?> baseClazz;

	public Script(Class<?> baseClazz) {
		this.baseClazz = baseClazz;
	}

	public enum ScriptType {
		SCRIPTABLEOBJ,
		BASECOMPONENT,
		COMPONENT,
		SYSTEM;

		public static boolean CheckType(ScriptType type, Class<?> clazz) {
			return (type == SCRIPTABLEOBJ && ScriptableObject.class.isAssignableFrom(clazz))
					|| (type == BASECOMPONENT && BaseComponent.class.isAssignableFrom(clazz))
					|| (type == COMPONENT && Component.class.isAssignableFrom(clazz))
					|| (type == SYSTEM && ECSSystem.class.isAssignableFrom(clazz));
		}
	}

	public abstract ScriptType GetScriptType();

	public Class<?> GetBaseClazz() {
		return baseClazz;
	}

	@SuppressWarnings("unchecked")
	public <T> T GetScript(Class<T> type) {
		if (!ScriptType.CheckType(this.GetScriptType(), type)) {
			Logger.c_error("the script is not of type: " + type.getName() + ", it is of type: " + this.GetScriptType());
			return null;
		}

		T script;
		try {
			script = (T) ScriptEngine.GetInstance().GetClassLoader().loadClass(this.baseClazz.getCanonicalName())
					.getConstructors()[0]
					.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException | ClassNotFoundException e) {

			Logger.c_error("failed to load class: " + this.baseClazz.getCanonicalName() + " through from the script obj");
			e.printStackTrace();

			return null;
		}

		return script;
	}
}
