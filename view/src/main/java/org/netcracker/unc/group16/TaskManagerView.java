package org.netcracker.unc.group16;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;


public class TaskManagerView implements ProgramInterface {
    private JFrame mainFrame;
    private Panel leftControlPanel;
    private Panel rightControlPanel;
    private Panel monthYearPanel;
    private JLabel lblMonthYear;
    private CalendarPanel calendarPanel;

    private String[] months = {
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    };

    public TaskManagerView(TaskManagerController taskManager) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        initGUI();
    }

    private void initGUI() {
        mainFrame = new JFrame("Task manager");
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        mainFrame.setPreferredSize(new Dimension(1440, 900));
        mainFrame.setMinimumSize(new Dimension(750, 650));
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setLayout(new BorderLayout());
        JPanel contentPane = new JPanel();
        mainFrame.getContentPane().add(contentPane, BorderLayout.PAGE_START);

        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();

        c1.anchor = GridBagConstraints.NORTH;

// First line
        leftControlPanel = new Panel();
        rightControlPanel = new Panel();

        c1.gridwidth = 1;
        c1.weightx = 1;
        c1.insets = new Insets(10,10,10,10);
        c1.anchor = GridBagConstraints.WEST;
        contentPane.add(leftControlPanel, c1);
        leftControlPanel.setLayout(new GridBagLayout());

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridy = 0;
        c2.anchor = GridBagConstraints.WEST;
        c2.gridx = 0;
        JButton btnCreateTask = new JButton("Создать задачу");
        leftControlPanel.add(btnCreateTask, c2);
        c2.gridx = 1;
        JButton btnViewTask = new JButton("Просмотр задач");
        leftControlPanel.add(btnViewTask, c2);

        c1.gridx = 1;
        c1.anchor = GridBagConstraints.EAST;
        contentPane.add(rightControlPanel, c1);
        rightControlPanel.setLayout(new GridBagLayout());
        c2.gridx = 2;
        JButton btnPrevMonth = new JButton("  <  ");
        rightControlPanel.add(btnPrevMonth, c2);
        c2.gridx = 3;
        JButton btnNextMonth = new JButton("  >  ");
        rightControlPanel.add(btnNextMonth, c2);

        btnPrevMonth.addActionListener(e -> {
            if (calendarPanel.getMonth() > 0) {
                calendarPanel.setMonth(calendarPanel.getMonth() - 1);
            } else {
                calendarPanel.setMonth(Calendar.DECEMBER);
                calendarPanel.setYear(calendarPanel.getYear() - 1);
            }
            lblMonthYear.setText(months[calendarPanel.getMonth()] + " " + calendarPanel.getYear());
        });

        btnNextMonth.addActionListener(e -> {
            if (calendarPanel.getMonth() < Calendar.DECEMBER) {
                calendarPanel.setMonth(calendarPanel.getMonth() + 1);
            } else {
                calendarPanel.setMonth(Calendar.JANUARY);
                calendarPanel.setYear(calendarPanel.getYear() + 1);
            }
            lblMonthYear.setText(months[calendarPanel.getMonth()] + " " + calendarPanel.getYear());
        });

// Second line
        monthYearPanel = new Panel();
        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridwidth = 2;
        c1.anchor = GridBagConstraints.CENTER;
        contentPane.add(monthYearPanel, c1);
        lblMonthYear = new JLabel();
        lblMonthYear.setFont(new Font("Verdana", 0, 15));
        monthYearPanel.add(lblMonthYear);


// Third line
        calendarPanel = new  CalendarPanel();
        mainFrame.add(calendarPanel);
        lblMonthYear.setText(months[calendarPanel.getMonth()] + " " + calendarPanel.getYear());

        mainFrame.pack();
        mainFrame.setVisible(true);
    }


    public void drawNotificate(){
    }

    public void drawTaskManager(){

    }

    public void drawMainWindow(){

    }

    public void addTaskButton(){

    }

    public void editTaskButton(){

    }

    public void deleteTaskButton(){

    }

    public void showByDayButton(){

    }

    public void showByWeekButton(){

    }

    public void showByMonthButton(){

    }

    public void showByYearButton(){

    }
}
