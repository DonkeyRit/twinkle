package com.github.donkeyrit.javaapp.ui;

import com.github.donkeyrit.javaapp.panels.CustomPanel;

import javax.swing.*;

public class WindowLayout {

    private CustomPanel header;
    public CustomPanel getHeader() {
        return header;
    }
    public WindowLayout setHeader(CustomPanel header) {
        SetSpecificPanel(this.header, header);
        this.header = header;
        return this;
    }

    private CustomPanel sidebar;
    public CustomPanel getSidebar() {
        return sidebar;
    }
    public WindowLayout setSidebar(CustomPanel sidebar) {
        SetSpecificPanel(this.sidebar, sidebar);
        this.sidebar = sidebar;
        return this;
    }

    private CustomPanel content;
    public CustomPanel getContent() {
        return content;
    }
    public WindowLayout setContent(CustomPanel content) {
        SetSpecificPanel(this.content, content);
        this.content = content;
        return this;
    }
    public WindowLayout removeContent() {
        SetSpecificPanel(this.content, null);
        this.content = null;
        return this;
    }

    private CustomPanel fullPagePanel;
    public CustomPanel getFullPagePanel() {
        return fullPagePanel;
    }
    public WindowLayout setFullPagePanel(CustomPanel fullPagePanel) {
        this.fullPagePanel = fullPagePanel;
        setFullPagePanel();
        return this;
    }

    private JPanel canvas;
    public JPanel getCanvas() {
        return canvas;
    }

    public WindowLayout(JPanel canvas) {
        this.canvas = canvas;
    }

    private void setFullPagePanel() {
        canvas.removeAll();
        this.fullPagePanel.setBounds(fullPagePanel.getBoundsRectangle());
        canvas.add(fullPagePanel);
        canvas.revalidate();
        canvas.repaint();
    }

    private void SetSpecificPanel(JPanel previousPanel, CustomPanel newPanel) {

        if (this.fullPagePanel != null) {
            canvas.removeAll();
            this.fullPagePanel = null;
        }

        if(previousPanel != null){
            canvas.remove(previousPanel);
        }

        if(newPanel != null){
            newPanel.setBounds(newPanel.getBoundsRectangle());
            canvas.add(newPanel);
        }

        canvas.revalidate();
        canvas.repaint();
    }
}
