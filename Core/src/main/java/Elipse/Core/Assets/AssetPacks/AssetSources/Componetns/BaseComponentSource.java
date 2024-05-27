package Elipse.Core.Assets.AssetPacks.AssetSources.Componetns;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Elipse.Core.Assets.AssetPacks.AssetSources.ComponentSource;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;
import Elipse.Core.Scripting.ScriptEngine;

public class BaseComponentSource extends ComponentSource {

	// private String clazzName;
	private byte[] data;

	public BaseComponentSource(BaseComponent baseComponent) {
		super();

		// clazzName = baseComponent.getClass().getCanonicalName();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
			out.writeObject(baseComponent);
			out.flush();
			this.data = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseComponent getComponent() {
		ByteArrayInputStream bis = new ByteArrayInputStream(this.data);

		try {
			ObjectInput in = new ObjectInputStream(bis) {
				protected java.lang.Class<?> resolveClass(java.io.ObjectStreamClass desc)
						throws java.io.IOException, ClassNotFoundException {
					try {
						return Class.forName(desc.getName(), true, ScriptEngine.GetInstance().GetClassLoader());
					} catch (Exception e) {
						e.printStackTrace();
					}

					return super.resolveClass(desc);
				};
			};

			return (BaseComponent) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ComponentType GetComponentType() {
		return ComponentType.BaseComponent;
	}

}
