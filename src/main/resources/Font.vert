#version 330 core

layout(location = 0) in vec3 vertex;
layout(location = 1) in vec2 tex;

uniform mat4 transformation;
uniform mat4 projection;

out vec2 p_tex;

void main()
{
	gl_Position = projection * transformation * vec4(vertex, 1.0);
	p_tex = tex;
}

