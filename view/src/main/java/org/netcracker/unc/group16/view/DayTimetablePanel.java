package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.controller.TaskManagerController;
import org.netcracker.unc.group16.model.Appointment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.List;


public class DayTimetablePanel extends JPanel {
    private final Color ELAPSED_TIME_BG_COLOR = new Color(245, 245, 245);
    private final Color TASKS_BG_COLOR = new Color(213, 230, 244);
    private final Color TASKS_BORDER_COLOR = new Color(183, 200, 214);

    private static final int ROWS_HEIGHT = 60;
    private static final int TIME_COLUMN_WIDTH = 55;

    private static final int SCROLL_SPEED = 25;


    private TaskManagerController taskManagerController;

    private int shift;
    private Calendar date;

    private boolean isPresentDay;

    private List<Appointment> tasks;
    private int mouseLastPressedY;
    private int draggedAppLocalId;
    private Calendar savStartTime;
    private Calendar savEndTime;


    public DayTimetablePanel(TaskManagerController taskManagerController) {
        this.taskManagerController = taskManagerController;

        setFont(new Font("Verdana", Font.BOLD, 12));

        addListeners();
    }
    
    private int timeToY(Calendar time) {
        return (int) Math.round((time.get(Calendar.HOUR_OF_DAY) + (double) time.get(Calendar.MINUTE) / 60) * ROWS_HEIGHT) - shift;
    }

    private Calendar yToTime(int y) {
        double tmp = (y + shift) / (double)ROWS_HEIGHT;
        int hour = (int) tmp;
        int min = (int) Math.round((tmp - hour) * 60);

        Calendar time = (Calendar) date.clone();
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, min);
        return time;
    }

    public void updateTasks() {
        tasks = taskManagerController.getByDay(Appointment.class, date);
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

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        mouseLastPressedY = e.getY() + shift;
                        draggedAppLocalId = -1;

                        Calendar time = yToTime(e.getY());

                        for (int i = tasks.size() - 1; i >= 0; i--) {
                            Appointment task = tasks.get(i);

                            if (time.compareTo(task.getTime()) > 0 && time.compareTo(task.getEndTime()) < 0) {
                                draggedAppLocalId = i;
                                savStartTime = task.getTime();
                                savEndTime = task.getEndTime();

                                if (e.getClickCount() == 2) {
                                    if (e.getX() >= getWidth() - 17 && e.getY() <= timeToY(task.getTime()) + 15) {
                                        taskManagerController.remove(task.getId());
                                        tasks.remove(i);
                                    } else {
                                        NewTaskDialog newTaskDialog = new NewTaskDialog(null, task); // fixme
                                        if (newTaskDialog.showDialog() == NewTaskDialog.OK) {
                                            taskManagerController.edit(task.getId(), newTaskDialog.getResult());
                                        }
                                    }
                                }
                                repaint();
                                return;
                            }
                        }
                        break;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (draggedAppLocalId != -1) {
                    tasks.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));
                    repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                double dy = e.getY() + shift - mouseLastPressedY;
                int appointmentDragY = (int) (dy / (ROWS_HEIGHT/4));

                if (draggedAppLocalId != -1 && appointmentDragY != 0) {
                    Appointment task = tasks.get(draggedAppLocalId);

                    Calendar startTime = (Calendar) savStartTime.clone();
                    Calendar endTime = (Calendar) savEndTime.clone();

                    startTime.add(Calendar.MINUTE, 15 * appointmentDragY);
                    endTime.add(Calendar.MINUTE, 15 * appointmentDragY);

                    if (startTime.get(Calendar.DAY_OF_MONTH) < savStartTime.get(Calendar.DAY_OF_MONTH) ||
                            startTime.get(Calendar.DAY_OF_MONTH) > savStartTime.get(Calendar.DAY_OF_MONTH)) {
                        return;
                    }

                    task.setEndTime(null);
                    task.setTime(startTime);
                    task.setEndTime(endTime);
                    repaint();

                    taskManagerController.edit(task.getId(), task);
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Calendar currentDate = Calendar.getInstance();

        int width = getWidth();
        int height = getHeight();

        if (date.before(currentDate)) {
            g2d.setColor(ELAPSED_TIME_BG_COLOR);
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.fillRect(0, 0, width, height);

        if (isPresentDay) {
            int currentTimeY = timeToY(currentDate);

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

        g2d.setColor(new Color(200, 200, 200));
        for (int i = 0; i < 24; i++) {
            g2d.drawLine(TIME_COLUMN_WIDTH + 2, ROWS_HEIGHT/2 + ROWS_HEIGHT * i  - shift,
                    width - 1, ROWS_HEIGHT/2 + ROWS_HEIGHT * i - shift);
        }
        g2d.setStroke(defaultStroke);

        // Размещение тасок
        for (Appointment task : tasks) {
            Calendar taskTimeStart = task.getTime();
            Calendar taskTimeEnd   = task.getEndTime();

            int y1;
            int y2;

            y1 = timeToY(taskTimeStart);
            y2 = timeToY(taskTimeEnd);

            if (taskTimeStart.get(Calendar.YEAR) < taskTimeEnd.get(Calendar.YEAR) ||
                    taskTimeStart.get(Calendar.MONTH) < taskTimeEnd.get(Calendar.MONTH) ||
                    taskTimeStart.get(Calendar.DAY_OF_MONTH) < taskTimeEnd.get(Calendar.DAY_OF_MONTH)) {
                if (taskTimeStart.compareTo(date) <= 0) {
                    y1 = -shift;
                } else {
                    y1 = timeToY(taskTimeStart);
                }

                Calendar tmpEndOfDate = (Calendar) date.clone();
                tmpEndOfDate.add(Calendar.DAY_OF_MONTH, 1);
                if (taskTimeEnd.compareTo(tmpEndOfDate) >= 0) {
                    y2 = 24 * ROWS_HEIGHT - shift - 1;
                } else {
                    y2 = timeToY(taskTimeEnd);
                }
            }

            g2d.setColor(TASKS_BG_COLOR);
            g2d.fillRect(TIME_COLUMN_WIDTH + 2,
                    y1,
                    width - TIME_COLUMN_WIDTH - 4,
                    y2 - y1);

            g2d.setColor(TASKS_BORDER_COLOR);
            g2d.drawRect(TIME_COLUMN_WIDTH + 2,
                    y1,
                    width - TIME_COLUMN_WIDTH - 4,
                    y2 - y1);

            // Кнопка удаления
            int deleteBtnX = width - 16;
            int deleteBtnY = y1 + 1;
            g2d.setColor(new Color(200, 200, 250));
            g2d.fillRect(deleteBtnX,
                    deleteBtnY,
                    13,
                    13);
            g2d.setColor(new Color(50, 50, 150));
            g2d.drawRect(deleteBtnX,
                    deleteBtnY,
                    13,
                    13);
            g2d.drawLine(deleteBtnX, deleteBtnY, deleteBtnX + 13, deleteBtnY + 13);
            g2d.drawLine(deleteBtnX + 13, deleteBtnY, deleteBtnX, deleteBtnY + 13);


            g2d.setColor(Color.BLACK);
            g2d.drawString(task.getTitle(), TIME_COLUMN_WIDTH + 7 , y1 + g2d.getFontMetrics().getAscent());
        }

        // Если сегодняшний день
        if (isPresentDay) {
            int currentTimeY = timeToY(currentDate);
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
            shift = timeToY(Calendar.getInstance()) + shift - getHeight()/3 ;

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
