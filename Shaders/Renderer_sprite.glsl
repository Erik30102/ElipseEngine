#type vertex
#version 330 core

layout(location = 0) in vec3 a_Position;
layout(location = 1) in vec2 atexCoord;

uniform mat4 viewMat;
uniform mat4 projectionMat;
uniform mat4 transformMat;

out vec2 texCoord;
out vec2 WordPos;

void main() {
	texCoord = atexCoord;
	gl_Position = projectionMat * viewMat * transformMat * vec4(a_Position, 1.0);
	WordPos = vec3(transformMat * vec4(a_Position, 1.0)).xy;
}

#type fragment
#version 330 core
struct PointLight {
	vec2 position;
	vec3 color;
	float brightness;
};

#define MAX_POINT_LIGHTS 200

layout(location = 0) out vec4 color;

uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform int lightCount;

uniform vec3 ambientColor;
uniform float ambientStrenght;

uniform sampler2D tex;

in vec2 texCoord;
in vec2 WordPos;

void main() {
	vec4 c = texture(tex, texCoord);

	if(c.a < 0.1)
		discard;

	vec3 _out = vec3(0.0);

	for(int i = 0; i < lightCount; i++) {
		float diffX = pointLights[i].position.x - WordPos.x;
		float diffY = pointLights[i].position.y - WordPos.y;

		float distance2 = (diffX * diffX) + (diffY * diffY);

		if(distance2 < pointLights[i].brightness*pointLights[i].brightness) {
			float distance = sqrt(distance2);

			float attentuation = 1.0 - smoothstep(0, pointLights[i].brightness, distance);

			vec3 Diffuse = pointLights[i].color * clamp(attentuation, 0.0, 1.0);
			_out += Diffuse;
		}
	}

	color = c * vec4(ambientColor, 1) * ambientStrenght + vec4(_out, 1);
}