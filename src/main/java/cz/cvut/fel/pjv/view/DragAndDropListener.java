package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.view.BoardPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;

public class DragAndDropListener implements MouseListener, MouseMotionListener {


    private final BoardPanel boardPanel;

    private boolean isDragged = false;
    private char sourceX;
    private int sourceY;
    private int offsetX;
    private int offsetY;

    /**
     * Constructor.
     *
     * @param boardPanel is panel that where MouseListener and MouseMotionListener handle events.
     */
    public DragAndDropListener(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    /**
     * On mousePressed store source position and calculate offset for dragging.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        sourceX = getColumnPosition(e);
        sourceY = getRowPosition(e);
        Game.logger.log(Level.FINER, "sourceX: " + sourceX + " sourceY: " + sourceY);

        offsetX = e.getPoint().x - boardPanel.SQUARE_DIMENSION * (getColumnPosition(e) - 'a' + 1);
        offsetY = e.getPoint().y - boardPanel.SQUARE_DIMENSION * (9 - getRowPosition(e));
        Game.logger.log(Level.FINER, "draggedX: " + offsetX + " draggedY: " + offsetY);
    }

    /**
     * On mouseReleased submit Move for validity and remove from Drag Layer.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (isDragged) {
            boardPanel.postDrag();
            boardPanel.submitMoveRequest(sourceX, sourceY, getColumnPosition(e), getRowPosition(e));
        }
        isDragged = false;
    }

    /**
     * On mouseDragged keep changing position of Piece to mouse position.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDragged) {
            boardPanel.drag(e.getPoint().x - offsetX, e.getPoint().y - offsetY);
        } else {
            boardPanel.preDrag(sourceX, sourceY, e.getPoint().x - offsetX, e.getPoint().y - offsetY);
            isDragged = true;
        }
    }


    private char getColumnPosition(MouseEvent e) {
        return (char) ('a' - 1 + e.getPoint().x / boardPanel.SQUARE_DIMENSION);
    }

    private int getRowPosition(MouseEvent e) {
        return 9 - e.getPoint().y / boardPanel.SQUARE_DIMENSION;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Game.logger.log(Level.FINE, "x: " + getColumnPosition(e) + "(" + e.getPoint().x + ")");
        Game.logger.log(Level.FINE, "y: " + getRowPosition(e) + "(" + e.getPoint().y + ")");

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
