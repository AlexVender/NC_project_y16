package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.controller.TaskManagerController;
import org.netcracker.unc.group16.model.Appointment;
import org.netcracker.unc.group16.model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.*;
import java.util.List;


public class DayTimetablePanel extends JPanel {
    private final Color ELAPSED_TIME_BG_COLOR = new Color(200, 200, 200);
    private final Color TASKS_BG_COLOR = new Color(213, 230, 244);

    private static final int ROWS_HEIGHT = 60;
    private static final int TIME_COLUMN_WIDTH = 55;

    private static final int SCROLL_SPEED = 25;


    TaskManagerController taskManagerController;

    private int shift;
    private Calendar date;

    private boolean isPresentDay;

    private List<Appointment> tasks;
    
    public DayTimetablePanel(TaskManagerController taskManagerController) {
        this.taskManagerController = taskManagerController;

        setFont(new Font("Verdana", Font.BOLD, 12));

        addListeners();
    }

    public void updateTasks() {
        tasks = taskManagerController.getByDate(Appointment.class, date);
    }


    private void addListeners() {
        addMouseWheelListener(e -> {
            shift += e.getWheelRotation() * SCROLL_SPEED;
            if (shift < 0) {
                shift = 0;
            }

            int maxShift = ROWS_HEIGHT * 24 - getHeight();
            if (shift > maxShift) {
                shift = maxShift;
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

        for (int i = 0; i < 24; i++) {
            g2d.drawString(( i<10 ? "0" : "" ) + i + ":00",  7, 15 + ROWS_HEIGHT * i - shift);
            g2d.drawLine(0, ROWS_HEIGHT * i - shift, width, ROWS_HEIGHT * i - shift);
        }
        g2d.drawLine(0, ROWS_HEIGHT * 24 - 1 - shift, width, ROWS_HEIGHT * 24 - 1 - shift);

        Stroke defaultStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[] {1.0f}, 0.0f));

        g2d.setColor(ELAPSED_TIME_BG_COLOR);
        for (int i = 0; i < 24; i++) {
            g2d.drawLine(TIME_COLUMN_WIDTH + 2, ROWS_HEIGHT/2 + ROWS_HEIGHT * i  - shift,
                    width - 1, ROWS_HEIGHT/2 + ROWS_HEIGHT * i - shift);
        }
        g2d.setStroke(defaultStroke);

        // Размещение тасок
        for (Task task : tasks) {
            g2d.setColor(TASKS_BG_COLOR);
            Calendar taskTimeStart = task.getTime();
            int hourStart = taskTimeStart.get(Calendar.HOUR_OF_DAY);
            int minStart = taskTimeStart.get(Calendar.MINUTE);

            Calendar taskTimeEnd = ((Appointment)task).getEndTime();
            int hourEnd = taskTimeEnd.get(Calendar.HOUR_OF_DAY);
            int minEnd = taskTimeEnd.get(Calendar.MINUTE);

            int y1 = (int) Math.round((hourStart   + (double) minStart / 60) * ROWS_HEIGHT) - shift;
            int y2 = (int) Math.round((hourEnd     + (double) minEnd   / 60) * ROWS_HEIGHT) - shift;

            if (taskTimeStart.get(Calendar.YEAR) < taskTimeEnd.get(Calendar.YEAR) ||
                    taskTimeStart.get(Calendar.MONTH) < taskTimeEnd.get(Calendar.MONTH) ||
                    taskTimeStart.get(Calendar.DAY_OF_MONTH) < taskTimeEnd.get(Calendar.DAY_OF_MONTH)) {
                y2 = 24 * ROWS_HEIGHT - shift - 1;
            }

            g2d.fillRect(TIME_COLUMN_WIDTH + 2,
                    y1 + 1,
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

        updateTasks();
        repaint();
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

        updateTasks();
        repaint();
    }
}
