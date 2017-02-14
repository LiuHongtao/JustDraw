JustCanvas = function () {
    this._functions = "";
    this._times = 0;

    this._make = function() {
        var i = 0, n = arguments.length;
        var content = "[" + arguments[i++] + "]";
        for (; i < n; i++) {
            content += arguments[i] + ",";
        }
        content += "&";
        return content
    }

    this.flush = function() {
        just.call(this._functions, this._times);
        this._functions = "";
        this._times = 0;
    };

    this.log = function(msg) {
        this._functions += this._make("log", msg);
        this._times++;
    };

    this.beginPath = function() {
        this._functions += this._make("beginPath");
        this._times++;
    };

    this.closePath = function() {
        this._functions += this._make("closePath");
        this._times++;
    };

    this.stroke = function() {
        this._functions += this._make("stroke");
        this._times++;
    };

    this.moveTo = function(x, y) {
        this._functions += this._make("moveTo", x, y);
        this._times++;
    };

    this.lineTo = function(x, y) {
        this._functions += this._make("lineTo", x, y);
        this._times++;
    };

    this.arc = function(x, y, r, sAngle, eAngle, counterclockwise) {
        this._functions += this._make("arc", x, y, r, sAngle, eAngle, counterclockwise);
        this._times++;
    };

    this.rect = function(x, y, width, height) {
        this._functions += this._make("rect", x, y, width, height);
        this._times++;
    };

    this.fillText = function(text, x, y) {
        this._functions += this._make("fillText", text, x, y);
        this._times++;
    }
}

function random(num){
    return parseInt(Math.random()*num+1);
}

ctx = new JustCanvas();
ctx.log("just canvas");
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
