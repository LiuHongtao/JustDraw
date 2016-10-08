function mRandom(num){
    return parseInt(Math.random()*num+1);
}

var canvas = window.createCanvas();
var ctx = canvas.context;

var maxNum = $count$;
for (var i = 0; i < maxNum; i++) {
    var x = mRandom(screenWidth);
    var y = mRandom(screenHeight);

    ctx.beginPath();
    ctx.moveTo(x, y);
    ctx.lineTo(x + 300, y);
    ctx.closePath();
    ctx.rect(x, y, 20, 20);
    ctx.arc(x, y, 2, 0, 360, false);
    ctx.stroke();
}