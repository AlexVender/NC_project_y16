package org.netcracker.unc.group16;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;


public class DayTimetablePanel extends JPanel {
    private int shift;
    private int velocity;


    private static final int ROWS_HEIGHT = 50;
    private static final int TIME_COLUMN_WIDTH = 55;

    public DayTimetablePanel() {
        setFont(new Font("Verdana", Font.BOLD, 13));


        addListeners();
    }

    private void addListeners() {
        addMouseWheelListener(e -> {
            shift += e.getWheelRotation() * 25;
            if (shift < 0) {
                shift = 0;
            }

            int maxShift = ROWS_HEIGHT * 24;
            if (shift + getHeight() > maxShift) {
                shift = maxShift - getHeight();
            }

            repaint();
        });

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (shift < 0)
                    shift = 0;

                int maxShift = ROWS_HEIGHT * 24;
                if (shift + getHeight() > maxShift)
                    shift = maxShift - getHeight();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, 0, 0, height);
        g2d.drawLine(TIME_COLUMN_WIDTH, 0, TIME_COLUMN_WIDTH, height);
        g2d.drawLine(width-1, 0, width-1, height);

        Stroke st1 = g2d.getStroke();
        Stroke st2 = new BasicStroke();

        for (int i = 0; i < 24; i++) {
            g2d.drawString(( i<10 ? "0" : "" ) + i + ":00",  7, 15 + ROWS_HEIGHT * i - shift);
            g2d.drawLine(0, ROWS_HEIGHT * i - shift, width, ROWS_HEIGHT * i - shift);
        }
        g2d.drawLine(0, ROWS_HEIGHT * 24 - 1 - shift, width, ROWS_HEIGHT * 24 - 1 - shift);

        Stroke defaultStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[] {1.0f}, 0.0f));

        g2d.setColor(new Color(200, 200, 200));
        for (int i = 0; i < 24; i++) {
            g2d.drawLine(TIME_COLUMN_WIDTH + 2, ROWS_HEIGHT/2 + ROWS_HEIGHT * i  - shift,
                    width - 1, ROWS_HEIGHT/2 + ROWS_HEIGHT * i - shift);
        }

        Calendar time = Calendar.getInstance();
        int currentTimeY = (int) Math.round((time.get(Calendar.HOUR_OF_DAY) + (double) time.get(Calendar.MINUTE) / 60) * ROWS_HEIGHT) - shift ;
        g2d.setColor(Color.BLUE);
        g2d.drawLine(0, currentTimeY, width, currentTimeY);
        g2d.setStroke(defaultStroke);
        int[] triangleX = {0, 5, 0};
        int[] triangleY = {currentTimeY-3, currentTimeY, currentTimeY+3};
        g2d.fillPolygon(triangleX, triangleY, 3);

    }
}
