package vsdl.omnigui.api;

import vsdl.omnigui.window.OmniFrame;

import java.util.EventListener;

public abstract class BoundEventListener implements EventListener{
    private Gui boundGui = null;
    private boolean isBound = false;

    protected Gui getBoundGui() {
        return boundGui;
    }

    protected OmniFrame getBoundFrame() {
        return isBound ? boundGui.getFrame() : null;
    }

    public void bind(Gui gui) {
        if (isBound) throw new IllegalStateException("Already bound");
        this.boundGui = gui;
        this.isBound = true;
    }
}
