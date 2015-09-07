var startapp = {
    start: function(message, completeCallback, errorCallback) {
			cordova.exec(completeCallback, errorCallback, "StartApp", "start", (typeof message === 'string') ? [message] : message);
	}
}

module.exports = startapp;

