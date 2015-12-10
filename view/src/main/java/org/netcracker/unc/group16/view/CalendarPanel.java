package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.controller.TaskManagerController;
import org.netcracker.unc.group16.model.Appointment;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class CalendarPanel extends JPanel {
    private TaskManagerController taskManagerController;

    // Дата текущего дня
    private GregorianCalendar presentDay;

    // Дата отображаемого месяца
    private int year;
    private int month;

    // Шаги сетки
    private double xGridStep;
    private double yGridStep;

    private static final String[] weekdays = {
            "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"
    };

    private static final String suffix = "...";

    public static final int WEEKDAYS_HEIGHT = 24;
    public static final int CELL_HEAD_HEIGHT = 18;

    private static final Color SEPARATING_LINES_COLOR = new Color(0, 0, 0);
    private static final Color HORIZONTAL_LINE_IN_CELLS_COLOR = new Color(166, 166, 166);
    private static final Color WEEKENDS_BG_COLOR = new Color(213, 230, 244);
    private static final Color ANOTHER_MONTH_BG_COLOR = new Color(237,237,237);
    private static final Color ANOTHER_MONTH_WEEKENDS_BG_COLOR = new Color(225, 233, 240);

    public CalendarPanel(TaskManagerController taskManagerController) {
        this.taskManagerController = taskManagerController;

        setFont(new Font("Verdana", Font.BOLD, 12));

        presentDay = new GregorianCalendar();
        year = presentDay.get(Calendar.YEAR);
        month = presentDay.get(Calendar.MONTH);
        setBorder(new LineBorder(Color.BLACK));

        addListeners();
    }

    private void addListeners() {
    }


    public void setYear(int year) {
        this.year = year;
        repaint();
    }

    public void setMonth(int month) {
        this.month = (month % 12) + ((month < 0) ? 12 : 0);
        this.year += (month / 12) + ((month < 0) ? -1 : 0);
        repaint();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int height = getHeight();
        int width = getWidth();
        xGridStep = Math.round(((double) width) / 7.0);
        yGridStep = Math.round((double)(height - WEEKDAYS_HEIGHT) / 6.0);


        // Фон
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Сглаживание
        g2d.setRenderingHint ( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        // Выключить сглаживание для текста
        g2d.setRenderingHint ( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF );

        // Фон выходных
        g2d.setColor(WEEKENDS_BG_COLOR);
        g2d.fillRect((int)(xGridStep *5), WEEKDAYS_HEIGHT, (int)(width - xGridStep *5), height - WEEKDAYS_HEIGHT);


        Calendar calendarIter = new GregorianCalendar(year, month, 1);
        calendarIter.setMinimalDaysInFirstWeek(7);
        calendarIter.set(Calendar.WEEK_OF_MONTH, calendarIter.getActualMinimum(Calendar.WEEK_OF_MONTH));
        calendarIter.set(Calendar.DAY_OF_WEEK, calendarIter.getFirstDayOfWeek());
        for (int row = 0; row < 6; row++) {
            int yDatePos = (int) (WEEKDAYS_HEIGHT + yGridStep * row + (CELL_HEAD_HEIGHT + g2d.getFontMetrics().getAscent()) / 2);
            for (int column = 0; column < 7; column++) {
                List<Appointment> tasks = taskManagerController.getByDay(Appointment.class, calendarIter);

                int yearIter = calendarIter.get(Calendar.YEAR);
                int monthIter = calendarIter.get(Calendar.MONTH);
                int dateIter = calendarIter.get(Calendar.DAY_OF_MONTH);


                // Соседний месяц
                if (monthIter != month) {
                    // Выбор цвета будние/выходные
                    if (column < 5) {
                        g2d.setColor(ANOTHER_MONTH_BG_COLOR);
                    } else {
                        g2d.setColor(ANOTHER_MONTH_WEEKENDS_BG_COLOR);
                    }

                    g2d.fillRect(
                            (int) (xGridStep * column),
                            (int) (WEEKDAYS_HEIGHT + yGridStep * row),
                            (int) xGridStep + 1,
                            (int) yGridStep + 1
                    );
                }

                // Если ячейка календаря отображает сегодняшнюю дату
                if (dateIter == presentDay.get(Calendar.DAY_OF_MONTH) &&
                        monthIter == (presentDay.get(Calendar.MONTH) ) &&
                        yearIter == presentDay.get(Calendar.YEAR)) {
                    g2d.setColor(Color.RED);
                    int x = (int) (xGridStep * column);
                    int y = (int) (yGridStep * row) + WEEKDAYS_HEIGHT;
                    g2d.drawRoundRect(x - 1, y - 1, (int) xGridStep + 2, (int) yGridStep + 2, 15, 15);
                }


                g2d.setColor(Color.BLACK);
                FontMetrics fontMetrics = g2d.getFontMetrics();
                int xDatePos = (int) (xGridStep *(column+1) - fontMetrics.stringWidth(String.valueOf(calendarIter.get(Calendar.DAY_OF_MONTH))) - 7);
                g2d.drawString(String.valueOf(calendarIter.get(Calendar.DAY_OF_MONTH)), xDatePos, yDatePos);


                int tasksOnDayCnt = 0;
                for (Appointment task : tasks) {
                    int shift = (int)(WEEKDAYS_HEIGHT + yGridStep * row + CELL_HEAD_HEIGHT);
                    int taskHeight = 14;
                    int yTaskPos = (taskHeight + 2) * tasksOnDayCnt;

                    if (yTaskPos + taskHeight > yGridStep - WEEKDAYS_HEIGHT ) {
                        break;
                    }

                    g2d.setColor(Color.ORANGE);
                    g2d.fillRect(
                            (int) (xGridStep * column) + 2,
                            shift + yTaskPos + 2,
                            (int) xGridStep - 3,
                            taskHeight);

                    if (task.getTime().get(Calendar.YEAR) == calendarIter.get(Calendar.YEAR) &&
                            task.getTime().get(Calendar.MONTH) == calendarIter.get(Calendar.MONTH) &&
                            task.getTime().get(Calendar.DAY_OF_MONTH) == calendarIter.get(Calendar.DAY_OF_MONTH)) {

                        int hour = task.getTime().get(Calendar.HOUR_OF_DAY);
                        int min = task.getTime().get(Calendar.MINUTE);

                        String formedText = (hour > 9 ? "" : "0") + hour + ":" +
                                (min > 9 ? "" : "0") + min + " " + task.getTitle();

                        int dx = fontMetrics.stringWidth(formedText) - ((int) xGridStep - 6 - fontMetrics.stringWidth(suffix));
                        if (dx > 0) {
                            for (int i = 1; i < formedText.length(); i++) {
                                if (fontMetrics.bytesWidth(formedText.getBytes(), formedText.length() - i, i) >= dx) {
                                    formedText = formedText.substring(0, formedText.length() - i);
                                    break;
                                }
                            }
                            formedText = formedText + suffix;
                        }
                        g2d.setColor(Color.BLACK);
                        g2d.drawString(formedText, (int) (xGridStep * column) + 3, shift + yTaskPos + fontMetrics.getAscent());
                    }
                    tasksOnDayCnt++;
                }

                calendarIter.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        // Сетка
        //   Горизонтальные линии
        for (int i = 0; i < 6; i++) {
            g2d.setColor(HORIZONTAL_LINE_IN_CELLS_COLOR);
            g2d.drawLine(0, WEEKDAYS_HEIGHT + CELL_HEAD_HEIGHT + (int) (yGridStep * i), width, WEEKDAYS_HEIGHT + CELL_HEAD_HEIGHT + (int) (yGridStep * i));
            g2d.setColor(SEPARATING_LINES_COLOR);
            g2d.drawLine(0, WEEKDAYS_HEIGHT + (int)(yGridStep *i), width, WEEKDAYS_HEIGHT + (int)(yGridStep *i));
        }
        //   Вертикальные линии
        for (int i = 1; i <= 6; i++) {
            g2d.drawLine((int) (xGridStep * i), 0, (int) (xGridStep * i), height);
        }

        // Подписи дней недель
        for (int i = 0; i < 7; i++) {
            int x = (int)(xGridStep *i + (xGridStep - g2d.getFontMetrics().stringWidth(weekdays[i])) / 2) + 1;
            int y = (WEEKDAYS_HEIGHT + g2d.getFontMetrics().getAscent())/2;
            g2d.drawString(weekdays[i], x, y);
        }
    }

    public Calendar getDateOfCell(int row, int col) {
        Calendar date = new GregorianCalendar(year, month, 1);
        date.setMinimalDaysInFirstWeek(7);
        date.set(Calendar.WEEK_OF_MONTH, date.getActualMinimum(Calendar.WEEK_OF_MONTH));
        date.set(Calendar.DAY_OF_WEEK, date.getFirstDayOfWeek());

        date.add(Calendar.DAY_OF_MONTH, row*7 + col);

        return date;
    }

    public double getXGridStep() {
        return xGridStep;
    }

    public double getYGridStep() {
        return yGridStep;
    }
}
