#type vertex
#version 330 core

layout(location = 0) in vec3 a_Position;
layout(location = 1) in vec2 a_texCoord;
layout(location = 2) in float a_texId;

uniform mat4 viewMat;
uniform mat4 projectionMat;

out vec2 texCoord;
out vec2 WordPos;
out float texId;

void main() {
	texCoord = a_texCoord;
	texId = a_texId;
	WordPos = a_Position.xy;
	gl_Position = projectionMat * viewMat * vec4(a_Position, 1.0);
}

#type fragment
#version 330 core

layout(location = 0) out vec4 color;

in vec2 texCoord;
in vec2 WordPos;
in float texId;

uniform sampler2D textures[8];

void main() {
	int id = int(texId);
	color = texture(textures[id], texCoord);
}