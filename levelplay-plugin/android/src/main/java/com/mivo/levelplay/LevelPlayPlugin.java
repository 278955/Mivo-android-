package com.mivo.levelplay;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.JSObject;

@CapacitorPlugin(name = "LevelPlay")
public class LevelPlayPlugin extends Plugin {

    @PluginMethod
    public void getStatus(PluginCall call) {
        JSObject result = new JSObject();
        result.put("integrated", false);
        result.put("message", "LevelPlay SDK setup pending");
        call.resolve(result);
    }
}
