#version 330

out vec4 out_color;

uniform vec3 color;
uniform float alpha, radius, h;
uniform vec2 s, p, r;

float roundedBoxSDF(vec2 CenterPosition, vec2 Size, float Radius) {
    return length(max(abs(CenterPosition)-Size+Radius,0.0))-Radius;
}

void main() {
    if(radius > 0){
        vec2 pixel = gl_FragCoord.xy;
        pixel.y = h - pixel.y;
        float distance 		= roundedBoxSDF(pixel - p - (s / 2.0f), s / 2.0f, radius);
        float smoothedAlpha =  1.0f - smoothstep(0.0f, 2.0, distance); //AA
        out_color = vec4(color, alpha * smoothedAlpha);
    }else{
        out_color = vec4(color, alpha);
    }
}