#version 330 core
layout(location = 0) in vec2 aPos;
layout(location = 1) in vec2 tex;

out vec2 fTexCoords;
uniform vec2 s, p, r;

void main()
{
    fTexCoords = tex;

    gl_Position = vec4((aPos * s + p) / r * 2 - 1, 0, 1);
    gl_Position.y = -gl_Position.y; //INVERT Y AXIS
}