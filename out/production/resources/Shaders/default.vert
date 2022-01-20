#version 330

in vec2 pos;

uniform vec2 s, p, r;
uniform int f;

void main() {
    if(f == 1){
        gl_Position = vec4(pos / r * 2 - 1, 0, 1);
    }else {
        gl_Position = vec4((pos * s + p) / r * 2 - 1, 0, 1);
    }
    gl_Position.y = -gl_Position.y; //INVERT Y AXIS
}