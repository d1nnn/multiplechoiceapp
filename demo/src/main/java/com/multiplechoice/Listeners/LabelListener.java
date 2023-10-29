package com.multiplechoice.Listeners;

import javafx.event.EventHandler;
import javafx.scene.control.Label;

public interface LabelListener extends EventHandler {
    void ChangeLabelText(String text);
}
