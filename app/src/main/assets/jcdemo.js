JustCall = function (name, parameter) {
    this.name = name;
    this.parameter = parameter;
}

JustCanvas = function () {
    this._functions = [];

    this.flush = function() {
        call(this._functions);
        this._functions = [];
    };

    this.log = function(msg) {
        this._functions.push(new JustCall("log", [msg]));
    };

    this.beginPath = function() {
        this._functions.push(new JustCall("beginPath"));
    };

    this.closePath = function() {
        this._functions.push(new JustCall("closePath"));
    };

    this.stroke = function() {
        this._functions.push(new JustCall("stroke"));
    };

    this.moveTo = function(x, y) {
        this._functions.push(new JustCall("moveTo", [x, y]));
    };

    this.lineTo = function(x, y) {
        this._functions.push(new JustCall("lineTo", [x, y]));
    };

    this.arc = function(x, y, r, sAngle, eAngle, counterclockwise) {
        this._functions.push(new JustCall("arc", [x, y, r, sAngle, eAngle, counterclockwise]));
    };

    this.rect = function(x, y, width, height) {
        this._functions.push(new JustCall("rect", [x,y,width,height]));
    };
}

function random(num){
    return parseInt(Math.random()*num+1);
}

ctx = new JustCanvas();
for (var i = 0; i < $count$; i++) {
    var x = random(screenWidth);
    var y = random(screenHeight);

    ctx.beginPath();
    ctx.moveTo(x, y);
    ctx.lineTo(x + 300, y);
    ctx.closePath();
    ctx.rect(x, y, 20, 20);
    ctx.arc(x, y, 2, 0, 360, false);
    ctx.stroke();
}
ctx.flush();
