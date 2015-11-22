package org.netcracker.unc.group16;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class CalendarPanel extends JPanel {
    // Дата Текущий день
    private GregorianCalendar presentDay;

    // Массив дат для отображения
    private int[][] dates;

    // Дата отображаемого иесяцв
    private int year;
    private int month;

    // Шаги сетки
    private double xGridStep;
    private double yGridStep;

    private static final String[] weekdays = {
            "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"
    };

    private static final int WEEKDAYS_HEIGHT = 24;

    private static final Color LINES_COLOR = Color.BLACK;
    private static final Color WEEKENDS_COLOR = new Color(213, 230, 244);
    private static final Color ANOTHER_MONTH_COLOR = new Color(237,237,237);
    private static final Color ANOTHER_MONTH_WEEKENDS_COLOR = new Color(225, 233, 240);

    public CalendarPanel() {
        setFont(new Font("Verdana", Font.BOLD, 13));

        presentDay = new GregorianCalendar();
        year = presentDay.get(Calendar.YEAR);
        month = presentDay.get(Calendar.MONTH);
        setBorder(new LineBorder(Color.BLACK));
        calcDaysPositions();

        addListeners();
    }

    private void addListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int r = (int) ((y - WEEKDAYS_HEIGHT) / yGridStep);
                int c = (int) (x / xGridStep);

                // По двойному клику
//                if (e.getClickCount() == 2) {
                    // TODO: open DayTimetablePanel
//                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void calcDaysPositions() {
        Calendar calendar = new GregorianCalendar(year, month, 1);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.set(Calendar.WEEK_OF_MONTH, calendar.getActualMinimum(Calendar.WEEK_OF_MONTH));
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        dates = new int[6][7];
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                dates[r][c] = calendar.get(Calendar.DAY_OF_MONTH);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }

    public void setYear(int year) {
        this.year = year;
        calcDaysPositions();
        repaint();
    }

    public void setMonth(int month) {
        this.month = month;
        calcDaysPositions();
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
        // Выключить сглаживание для текста ?
//        g2d.setRenderingHint ( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF );

        g2d.setColor(WEEKENDS_COLOR);
        g2d.fillRect((int)(xGridStep *5), WEEKDAYS_HEIGHT, (int)(width - xGridStep *5), height - WEEKDAYS_HEIGHT);

        boolean isCurMonth = false;
        for (int r = 0; r < 6; r++) {
            int yDatePos = (int) (WEEKDAYS_HEIGHT + yGridStep *r + (WEEKDAYS_HEIGHT + g2d.getFontMetrics().getAscent())/2);
            for (int c = 0; c < 7; c++) {
                if (dates[r][c] == 1) {
                    isCurMonth = !isCurMonth;
                }

                // Соседний месяц
                if (!isCurMonth) {
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
                } else {
                    if (dates[r][c] == presentDay.get(Calendar.DAY_OF_MONTH) &&
                            month == presentDay.get(Calendar.MONTH) &&
                            year == presentDay.get(Calendar.YEAR)) {

                        g2d.setColor(Color.BLACK);
                        g2d.drawRoundRect(
                                (int) (xGridStep * c - 1),
                                (int) (WEEKDAYS_HEIGHT + yGridStep * r - 1),
                                (int) xGridStep + 2,
                                (int) yGridStep + 2,
                                15, 15
                        );
                    }
                }

                int xDatePos = (int) (xGridStep *(c+1) - g2d.getFontMetrics().stringWidth(String.valueOf(dates[r][c])) - 5);
                g2d.setColor(Color.BLACK);
                g2d.drawString(String.valueOf(dates[r][c]), xDatePos, yDatePos);
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
            g2d.drawLine(0, WEEKDAYS_HEIGHT * 2 + (int) (yGridStep * i), width, WEEKDAYS_HEIGHT * 2 + (int) (yGridStep * i));
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
}
