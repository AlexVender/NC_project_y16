package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.model.TaskManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;
import java.util.Map;


public class DayTimetablePanel extends JPanel {
    TaskManagerModel taskManagerModel;

    private int shift;
    private Calendar date;

    private static final int ROWS_HEIGHT = 50;
    private static final int TIME_COLUMN_WIDTH = 55;
    private boolean isPresentDay;

    public DayTimetablePanel(TaskManagerModel taskManagerModel) {
        this.taskManagerModel = taskManagerModel;

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

                int maxShift = ROWS_HEIGHT * 24 - getHeight();
                if (shift > maxShift)
                    shift = maxShift;
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Calendar currentDate = Calendar.getInstance();

        int width = getWidth();
        int height = getHeight();

        if (date.before(currentDate)) {
            g2d.setColor(new Color(245, 245, 245));
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.fillRect(0, 0, width, height);

        if (isPresentDay) {
            int currentTimeY = (int) Math.round((currentDate.get(Calendar.HOUR_OF_DAY) + (double) currentDate.get(Calendar.MINUTE) / 60) * ROWS_HEIGHT) - shift;

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, currentTimeY, width, height - currentTimeY);
        }

            g2d.setColor(Color.BLACK);
        g2d.drawLine(0, 0, 0, height);
        g2d.drawLine(TIME_COLUMN_WIDTH, 0, TIME_COLUMN_WIDTH, height);
        g2d.drawLine(width - 1, 0, width - 1, height);

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
        g2d.setStroke(defaultStroke);

        // Размещение тасок
        Map<Integer, Task> tasks = taskManagerModel.getTasksByDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));


        for (Task task : tasks.values()) {
            g2d.setColor(new Color(213, 230, 244));
            Calendar taskTime = task.getTime();
            int hour = taskTime.get(Calendar.HOUR_OF_DAY);
            int min = taskTime.get(Calendar.MINUTE);

            int y1 = (int) Math.round((hour   + (double) min / 60) * ROWS_HEIGHT) - shift + 1;
//            System.out.println(y1);
            int y2 = (int) Math.round((hour+1 + (double) min / 60) * ROWS_HEIGHT) - shift;

            g2d.fillRect(TIME_COLUMN_WIDTH + 2,
                    y1,
                    width - TIME_COLUMN_WIDTH - 4,
                    y2 - y1);

            g2d.setColor(Color.BLACK);
            g2d.drawString(task.getTitle(), TIME_COLUMN_WIDTH + 7 , y1 + g2d.getFontMetrics().getAscent());
        }

        // Если сегодняшний день
        if (isPresentDay) {
            int currentTimeY = (int) Math.round((currentDate.get(Calendar.HOUR_OF_DAY) + (double) currentDate.get(Calendar.MINUTE) / 60) * ROWS_HEIGHT) - shift;
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[]{1.0f}, 0.0f));
            g2d.drawLine(0, currentTimeY, width, currentTimeY);
            g2d.setStroke(defaultStroke);
            int[] triangleX = {0, 5, 0};
            int[] triangleY = {currentTimeY - 3, currentTimeY, currentTimeY + 3};
            g2d.fillPolygon(triangleX, triangleY, 3);
        }
    }

    private boolean isPresentDayCheck() {
        Calendar currentDate = Calendar.getInstance();

        isPresentDay = date.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH) &&
                date.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                date.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR);
        return isPresentDay;
    }

    public Calendar getDate() {
        return (Calendar)date.clone();
    }

    public void setDate(Calendar date) {
        this.date = date;

        if (isPresentDayCheck()) {
            Calendar currentDate = Calendar.getInstance();
            shift = (int) Math.round((currentDate.get(Calendar.HOUR_OF_DAY) +
                    (double) currentDate.get(Calendar.MINUTE) / 60) * ROWS_HEIGHT) - getHeight()/3 ;

            int maxShift = ROWS_HEIGHT * 24 - getHeight();
            if (shift > maxShift)
                shift = maxShift;

            if (shift < 0)
                shift = 0;
        }
    }

    public void setDay(int day) {
        date.set(Calendar.DAY_OF_MONTH, day);

        if (isPresentDayCheck()) {
            Calendar currentDate = Calendar.getInstance();
            shift = (int) Math.round((currentDate.get(Calendar.HOUR_OF_DAY) +
                    (double) currentDate.get(Calendar.MINUTE) / 60) * ROWS_HEIGHT) - getHeight()/3 ;

            int maxShift = ROWS_HEIGHT * 24 - getHeight();
            if (shift > maxShift)
                shift = maxShift;

            if (shift < 0)
                shift = 0;
        }
    }
}
