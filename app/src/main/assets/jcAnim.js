JustCanvas = function () {
    this._functions = "";
    this._times = 0;

    this.canvas = {
        width: JustCanvasNative.width,
        height: JustCanvasNative.height,
    };

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
        JustCanvasNative.call(this._functions, this._times);
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

    this.fill = function() {
        this._functions += this._make("fill");
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

    this.clearRect = function(x, y, width, height) {
        this._functions += this._make("clearRect", x, y, width, height);
        this._times++;
    };

    this.fillText = function(text, x, y) {
        this._functions += this._make("fillText", text, x, y);
        this._times++;
    }
}

function Example() {
    let ctx, width, height, cx, cy, unit;
    let angle = 0;
    let timerId = null;
    let running = false;
    let times = 100000;

    this.init = function(newCtx) {
        ctx = newCtx;
        width = ctx.canvas.width;
        height = ctx.canvas.height;
        cx = Math.floor(width / 3);
        cy = Math.floor(height / 2);
        unit = width / 12;
        // initial draw
        if (!running) {
            while(times-- > 0) {
                draw();
            }
        }
    };

    function draw() {
        clear();
        drawAxes(angle);
        drawSine(angle);
        drawCircle(angle);
        drawLever(angle);
        drawFpsLabel();

        ctx.flush();

        speedometer.update();
        angle = normalizeAngle(angle - Math.PI / 90);
    }

    function clear() {
        ctx.clearRect(0, 0, width, height);
    }

    function normalizeAngle(angle) {
        return angle > Math.PI * 2 ? angle - Math.PI * 2 : angle < 0 ? angle + Math.PI * 2 : angle;
    }

    function drawAxes(angle) {
        let p = normalizeAngle(Math.PI * 2 - angle);
        let p2 = normalizeAngle(Math.PI - angle);
        ctx.beginPath();
        // x and y axes
        ctx.moveTo(cx - 3 * unit, cy);
        ctx.lineTo(cx + 2.25 * Math.PI * unit, cy);
        ctx.moveTo(cx, cy - 1.5 * unit);
        ctx.lineTo(cx, cy + 1.5 * unit);
        // x axis ticks
        ctx.moveTo(cx + p * unit, cy - 5);
        ctx.lineTo(cx + p * unit, cy + 5);
        ctx.moveTo(cx + p2 * unit, cy - 5);
        ctx.lineTo(cx + p2 * unit, cy + 5);
        // y axis ticks
        ctx.moveTo(cx - 5, cy + unit);
        ctx.lineTo(cx + 5, cy + unit);
        ctx.moveTo(cx - 5, cy - unit);
        ctx.lineTo(cx + 5, cy - unit);
        ctx.stroke();
        // x axis labels
        ctx.fillText('π', cx + p * unit, cy + 8);
        ctx.fillText('2π', cx + p2 * unit, cy + 8);
        // y axis labels
        ctx.fillText('1', cx + 8, cy - unit);
        ctx.fillText('-1', cx + 8, cy + unit);
    }

    function drawSine(angle) {
        let x, y;
        let steps = 50;
        ctx.beginPath();
        ctx.moveTo(cx, cy + Math.sin(angle) * unit);
        for (let i = 0; i <= steps; i++) {
            x = i * 2 * Math.PI / steps;
            y = Math.sin(angle + x);
            ctx.lineTo(cx + x * unit, cy + y * unit);
        }
        ctx.lineWidth = 2;
        ctx.stroke();
    }

    function drawCircle() {
        let ccx = cx - 1.5 * unit;
        ctx.beginPath();
        ctx.arc(ccx, cy, unit, 0, 2 * Math.PI);
        ctx.stroke();
    }

    function drawLever(t) {
        let ccx = cx - 1.5 * unit;
        let x = ccx + Math.cos(t) * unit;
        let y = cy + Math.sin(t) * unit;
        ctx.lineWidth = 2;
        ctx.beginPath();
        // lines
        ctx.moveTo(x, y);
        ctx.lineTo(cx, y);
        ctx.moveTo(ccx, cy);
        ctx.lineTo(x, y);
        ctx.stroke();
        // hinges
        ctx.lineWidth = 1;
        ctx.beginPath();
        let radius = 3;
        ctx.arc(x, y, radius, 0, 2 * Math.PI);
        ctx.moveTo(cx + radius / 2, y);
        ctx.arc(cx, y, radius, 0, 2 * Math.PI);
        ctx.fill();
        ctx.stroke();
    }

    function drawFpsLabel() {
        ctx.fillText('FPS: ' + speedometer.fps.toFixed(1), 30, 100);
    }

    let speedometer = {
        start: 0,
        count: 0,
        fps: 0,
        update: function() {
            let now = Date.now();
            let time = now - this.start;
            this.count++;
            if (this.start === 0) {
                this.start = now;
            } else if (time >= 1000) {
                this.fps = this.count / (time / 1000);
                this.start = now;
                this.count = 0;
            }
        }
    };
}

var ctx = new JustCanvas();
var example = new Example();
example.init(ctx);
