cordova.define('cordova/plugin_list', function(require, exports, module) {
  module.exports = [
   {
                 "id": "yn-plugin-videoplay.videoplay",
                 "file": "plugins/yn-plugin-videoplay/www/videoplay.js",
                 "pluginId": "yn-plugin-videoplay",
                 "clobbers": [
                    "yn.plugin.videoplay"
                 ]
             }];
  module.exports.metadata = {
    "cordova-plugin-whitelist": "1.3.4"
  };
});