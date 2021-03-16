cordova.define("yn-plugin-videoplay.videoplay", function(require, exports, module) {

    var exec = require('cordova/exec');

    // 跳转到原生视图
    exports.startPlayVideo = function(opts, successFn, failureFn) {
        exec(successFn, failureFn, "YNVideoPlay", "startPlayVideo", opts);
    }

});
