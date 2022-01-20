#version 330 core
in vec2 aPos;

out vec2 fTexCoords;
uniform vec2 s, p, r;

void main()
{
    fTexCoords = aPos;

    gl_Position = vec4((aPos * s + p) / r * 2 - 1, 0, 1);
    gl_Position.y = -gl_Position.y; //INVERT Y AXIS
}