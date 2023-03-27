package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.BoardPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DragAndDropListener implements MouseListener, MouseMotionListener {


    private BoardPanel boardPanel;

    public DragAndDropListener(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO implement
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO implement
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO implement
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
