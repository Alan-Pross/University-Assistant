var c;
var $;
var w = 650;
var h = 650;
constant = 15;
var rad = 300;
var timeout = 0;

	c = document.getElementById("canvas");
	$ = c.getContext("2d");
	drawLines();
	setTimeout(function(){location.href="main.html";},3000)
	
function drawLines() {
	$.fillRect(650,650,w,h);
	$.translate(w/2,h/2);
	for (var i = 0; i < 25; i++) {
		for (var n = -45; n <= 45; n+=constant) {
			setTimeout("draw("+n+");",15 * timeout);
			timeout++;
		}
	}
}
function draw(n){
	$.beginPath();
	$.moveTo(0,rad);
	var radians = Math.PI/180*n;
	var x = (rad * Math.sin(radians)) / Math.sin(Math.PI/2 - radians);
	$.lineTo(x,0);
	$.strokeStyle="rgb(200,200,200)";
	$.lineWidth=1;
	$.stroke();
	$.rotate((Math.PI/180)*15);
}