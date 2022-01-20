#version 330 core

uniform sampler2D u_texture;
uniform vec4 u_color;
uniform float smoothing;

varying vec2 fTexCoords;

out vec4 color;

void main()
{
    float distance = texture2D(u_texture, fTexCoords).a;
    float alpha = smoothstep(0.5 - smoothing, 0.5 + smoothing, distance);
    gl_FragColor = vec4(u_color.rgb, u_color.a * alpha);
}