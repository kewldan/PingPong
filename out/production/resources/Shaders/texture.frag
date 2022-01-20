#version 330 core

in vec2 fTexCoords;

uniform sampler2D uTexture;
uniform float a;

out vec4 color;

void main()
{
    color = texture(uTexture, fTexCoords);
    color.a *= a;
}

