package com.qisi.image;

import gui.ava.html.image.util.SynchronousHTMLEditorKit;

import javax.swing.*;

public class JeditPaneLearn {

    public static void main(String[] args) {
        JEditorPane editorPane = new JEditorPane();
        final SynchronousHTMLEditorKit kit = new SynchronousHTMLEditorKit();
        editorPane.setEditorKitForContentType("text/html", kit);
        editorPane.setContentType("text/html");
        editorPane.setText(Html2ImageUtil.html);
        JFrame jFrame = new JFrame();
        jFrame.setSize(900, 1080);
        jFrame.add(editorPane);
        jFrame.setVisible(true);
    }
}
