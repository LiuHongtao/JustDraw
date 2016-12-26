JustFunc = function (name, parameter) {
    this.name = name;
    this.parameter = parameter;
}

JustCanvas = function () {
    this._functions = [];

    this.flush = function() {
        call(JSON.stringify(this._functions));
        this._functions = [];
    };

    this.log = function(msg) {
        this._functions.push(new JustFunc("log", [msg]));
    };

    this.beginPath = function() {
        this._functions.push(new JustFunc("beginPath"));
    };

    this.closePath = function() {
        this._functions.push(new JustFunc("closePath"));
    };

    this.stroke = function() {
        this._functions.push(new JustFunc("stroke"));
    };

    this.moveTo = function(x, y) {
        this._functions.push(new JustFunc("moveTo", [x, y]));
    };

    this.lineTo = function(x, y) {
        this._functions.push(new JustFunc("lineTo", [x, y]));
    };

    this.arc = function(x, y, r, sAngle, eAngle, counterclockwise) {
        this._functions.push(new JustFunc("arc", [x, y, r, sAngle, eAngle, counterclockwise]));
    };

    this.rect = function(x, y, width, height) {
        this._functions.push(new JustFunc("rect", [x,y,width,height]));
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
