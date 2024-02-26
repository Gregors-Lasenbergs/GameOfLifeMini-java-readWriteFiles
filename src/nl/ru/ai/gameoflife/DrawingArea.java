package nl.ru.ai.gameoflife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawingArea extends JPanel
{
  private static final long serialVersionUID=1L;

  private static final int SIZE=8;
  private static final int ROWS=40;
  private static final int COLS=60;
  private final Cell[][] cell;
  private final Color[][] color;

  DrawingArea()
  {
    setPreferredSize(new Dimension(COLS*SIZE,ROWS*SIZE));
    cell=new Cell[ROWS][COLS];
    color=new Color[ROWS][COLS];
    for(int i=0;i<ROWS;i++)
      for(int j=0;j<COLS;j++)
      {
        cell[i][j]=Cell.DEAD;
        color[i][j]=Color.BLACK;
      }
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    for(int i=0;i<ROWS;i++)
      for(int j=0;j<COLS;j++)
        switch(cell[i][j])
        {
          case DEAD:
        	g.setColor(Color.BLACK);
            g.drawLine(j*SIZE+SIZE/2,i*SIZE+SIZE/2,j*SIZE+SIZE/2,i*SIZE+SIZE/2);
            break;
          case LIVE:
        	g.setColor(color[i][j]);
            g.fillRect(j*SIZE+SIZE/2-SIZE/2,i*SIZE+SIZE/2-SIZE/2,SIZE,SIZE);
            break;
        }
  }
  
  /**
   * Update the screen on the specified position. the upper left corner
   * has row 0 and column 0
   * @param row screen row
   * @param col screen col
   * @param cell the content to display
   * @param color of the cell to display
   */
  public void updateScreen(int row, int col, Cell cell, Color color)
  {
    assert(row>=0 && row<ROWS) : "Invalid row specified";
    assert(col>=0 && col<COLS) : "Invalid column specified";
    assert(cell!=null) : "Invalid cell specified";
    assert(color!=null) : "Invalid color specified";
    this.cell[row][col]=cell;
    this.color[row][col]=color;
    repaint();
  }
}
