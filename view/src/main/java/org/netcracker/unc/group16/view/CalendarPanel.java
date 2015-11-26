package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.model.TaskManagerModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;


public class CalendarPanel extends JPanel {
    TaskManagerModel taskManagerModel;

    // Дата Текущий день
    private GregorianCalendar presentDay;

    // Дата отображаемого иесяцв
    private int year;
    private int month;

    // Шаги сетки
    private double xGridStep;
    private double yGridStep;

    private static final String[] weekdays = {
            "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"
    };

    public static final int WEEKDAYS_HEIGHT = 24;
    public static final int CELL_HEAD_HEIGHT = 18;

    private static final Color LINES_COLOR = Color.BLACK;
    private static final Color WEEKENDS_COLOR = new Color(213, 230, 244);
    private static final Color ANOTHER_MONTH_COLOR = new Color(237,237,237);
    private static final Color ANOTHER_MONTH_WEEKENDS_COLOR = new Color(225, 233, 240);

    public CalendarPanel(TaskManagerModel taskManagerModel) {
        this.taskManagerModel = taskManagerModel;

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
        this.month = month;
        repaint();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int height = getHeight();
        int width = getWidth();
        xGridStep = width / 7;
        yGridStep = (height - WEEKDAYS_HEIGHT) / 6;


        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Сглаживание
        g2d.setRenderingHint ( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        // Выключить сглаживание для текста
        g2d.setRenderingHint ( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF );

        g2d.setColor(WEEKENDS_COLOR);
        g2d.fillRect((int)(xGridStep *5), WEEKDAYS_HEIGHT, (int)(width - xGridStep *5), height - WEEKDAYS_HEIGHT);


        Calendar calendarIter = new GregorianCalendar(year, month, 1);
        calendarIter.setMinimalDaysInFirstWeek(7);
        calendarIter.set(Calendar.WEEK_OF_MONTH, calendarIter.getActualMinimum(Calendar.WEEK_OF_MONTH));
        calendarIter.set(Calendar.DAY_OF_WEEK, calendarIter.getFirstDayOfWeek());
        for (int r = 0; r < 6; r++) {
            int yDatePos = (int) (WEEKDAYS_HEIGHT + yGridStep * r + (CELL_HEAD_HEIGHT + g2d.getFontMetrics().getAscent()) / 2);
            for (int c = 0; c < 7; c++) {
                int yearIter = calendarIter.get(Calendar.YEAR);
                int monthIter = calendarIter.get(Calendar.MONTH);
                int dateIter = calendarIter.get(Calendar.DAY_OF_MONTH);

                Map<Integer, Task> tasks = taskManagerModel.getTasksByDate(yearIter, monthIter, dateIter);

                // Соседний месяц
                if (monthIter != month) {
                    // Выбор цвета будние/выходные
                    if (c < 5) {
                        g2d.setColor(ANOTHER_MONTH_COLOR);
                    } else {
                        g2d.setColor(ANOTHER_MONTH_WEEKENDS_COLOR);
                    }

                    g2d.fillRect(
                            (int) (xGridStep * c ),
                            (int) (WEEKDAYS_HEIGHT + yGridStep * r),
                            (int) xGridStep,
                            (int) yGridStep
                    );
                }

                // Если ячейка календаря отображает сегодняшнюю дату
                if (dateIter == presentDay.get(Calendar.DAY_OF_MONTH) &&
                        monthIter == (presentDay.get(Calendar.MONTH) ) &&
                        yearIter == presentDay.get(Calendar.YEAR)) {
                    g2d.setColor(new Color(0,0, 200));
                } else {
                    g2d.setColor(Color.BLACK);
                }


                int xDatePos = (int) (xGridStep *(c+1) - g2d.getFontMetrics().stringWidth(String.valueOf(calendarIter.get(Calendar.DAY_OF_MONTH))) - 5);
                g2d.drawString(String.valueOf(calendarIter.get(Calendar.DAY_OF_MONTH)), xDatePos, yDatePos);


                int tasksOnDayCnt = 0;
                for (Task task : tasks.values()) {
                    int shift = (int)(WEEKDAYS_HEIGHT + yGridStep * r + CELL_HEAD_HEIGHT);
                    int taskHeight = 14;
                    int yTaskPos =  (taskHeight + 2) * tasksOnDayCnt;

                    if (yTaskPos + taskHeight > yGridStep - WEEKDAYS_HEIGHT ) {
                        break;
                    }

                    g2d.setColor(Color.ORANGE);
                    g2d.fillRect(
                            (int) (xGridStep * c + 2),
                            shift + yTaskPos + 2,
                            (int) xGridStep - 3,
                            taskHeight);

                    g2d.setColor(Color.BLACK);
                    int hour = task.getTime().get(Calendar.HOUR_OF_DAY);
                    int min = task.getTime().get(Calendar.MINUTE);
                    g2d.drawString((hour>9 ? "" : "0") + hour + ":" + min + " " + task.getTitle(),
                            (int) (xGridStep*c) + 5, shift + yTaskPos  + g2d.getFontMetrics().getAscent());

                    tasksOnDayCnt++;
                }

                calendarIter.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        // Сетка
        //   Вертикальные линии
        for (int i = 1; i <= 6; i++) {
            g2d.drawLine((int) (xGridStep * i), 0, (int) (xGridStep * i), height);
        }
        //   Горизонтальные линии
        for (int i = 0; i < 6; i++) {
            g2d.setColor(new Color(166, 166, 166));
            g2d.drawLine(0, WEEKDAYS_HEIGHT + CELL_HEAD_HEIGHT + (int) (yGridStep * i), width, WEEKDAYS_HEIGHT + CELL_HEAD_HEIGHT + (int) (yGridStep * i));
            g2d.setColor(LINES_COLOR);
            g2d.drawLine(0, WEEKDAYS_HEIGHT + (int)(yGridStep *i), width, WEEKDAYS_HEIGHT + (int)(yGridStep *i));
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
