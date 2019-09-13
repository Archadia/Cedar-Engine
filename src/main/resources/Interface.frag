#version 330 core

in vec2 p_tex;

uniform vec4 color;
uniform vec4 borderColor;
uniform vec2 stepSize;

out vec4 fragColor;

bool isBorder(vec2 coord)
{
	if(coord.x < 0 || coord.x > 1 || coord.y < 0 || coord.y > 1) {
		return true;
	}
	return false;
}

void main()
{
	vec2 trueStepSize = stepSize * 1;
	fragColor = vec4(color);
	if(isBorder(p_tex + vec2(0, trueStepSize.y)) ||
			isBorder(p_tex + vec2(0, -trueStepSize.y)) ||
			isBorder(p_tex + vec2(trueStepSize.x, 0)) ||
			isBorder(p_tex + vec2(-trueStepSize.x, 0)) ||

			isBorder(p_tex + vec2(-trueStepSize.x, trueStepSize.y)) ||
			isBorder(p_tex + vec2(-trueStepSize.x, -trueStepSize.y)) ||

			isBorder(p_tex + vec2(trueStepSize.x, trueStepSize.y)) ||
			isBorder(p_tex + vec2(trueStepSize.x, -trueStepSize.y))) {
		fragColor = vec4(borderColor);
	}
}
