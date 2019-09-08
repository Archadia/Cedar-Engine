#version 330 core

in vec2 p_tex;

uniform sampler2D map;
uniform vec4 color;
uniform float width = 0.40;
uniform float edge = 0.25;

out vec4 fragColor;


void main()
{
	float distance = 1.0 - texture(map, p_tex).a;
	float alpha = 1.0 - smoothstep(width, width + edge, distance);

	fragColor = vec4(color.rgb, alpha);

}
