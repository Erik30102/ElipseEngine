#type vertex
#version 330 core

layout(location = 0) in vec3 a_Position;
layout(location = 1) in vec2 atexCoord;

uniform mat4 viewMat;
uniform mat4 projectionMat;
uniform mat4 transformMat;

out vec2 texCoord;

void main() {
	texCoord = atexCoord;
	gl_Position = projectionMat * viewMat * transformMat * vec4(a_Position, 1.0);
}

#type fragment
#version 330 core
layout(location = 0) out vec4 color;

uniform sampler2D tex;

in vec2 texCoord;

void main() {
	vec4 c = texture(tex, texCoord);

	if(c.a < 0.1)
		discard;

	color = c;
}