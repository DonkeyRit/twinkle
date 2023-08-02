package com.github.donkeyrit.twinkle.layouts;

import java.awt.LayoutManager;
import javax.swing.JPanel;

public interface CustomCardLayout extends LayoutManager
{
	void putPanel(String key, JPanel panel);
	void showPanel(String key);
}
