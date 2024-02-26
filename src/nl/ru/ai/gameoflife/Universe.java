package nl.ru.ai.gameoflife;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Universe extends JFrame
{
  private static final long serialVersionUID=1L;
  
  private final DrawingArea drawingArea;
  private static Universe universe;
  
  private Universe()
  {
    setTitle("Game of Life");
    setLocation(200,300);
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        System.exit(1);
      }
    });
    Container contentPane=getContentPane();
    drawingArea=new DrawingArea();
    contentPane.add(drawingArea);
    pack();
    setResizable(false);
    setVisible(true);
  }

  /**
   * Update the screen on the specified position. The upper left corner
   * has row 0 and column 0
   * @param row screen row
   * @param col screen col
   * @param cell the content to display
   * @param color the cell color to use
   */
  public static void updateScreen(int row, int col, Cell cell, Color color)
  {
    assert row>=0 && row<40  : "Invalid row specified";
    assert col>=0 && col<60  : "Invalid column specified";
    assert cell!=null  : "Invalid cell specified";
    assert color!=null  : "Invalid color specified";
    if(universe==null)
      universe=new Universe();
    universe.drawingArea.updateScreen(row,col,cell,color);
  }
  /**
   * Sleep for the specified amount of milliseconds
   * @param milliseconds milliseconds between each frame
   */
  public static void sleep(int milliseconds)
  {
    try
    {
      Thread.sleep(milliseconds);
    }
    catch(InterruptedException ignored)
    {
      ;
    }
  }
}
