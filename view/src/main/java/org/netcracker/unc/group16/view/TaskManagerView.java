package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.model.TaskManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;


public class TaskManagerView implements ProgramInterface {
    TaskManagerModel taskManagerModel;

    private JFrame mainFrame;
    private Panel leftControlPanel;
    private Panel rightControlPanel;
    private JButton btnCreateTask;
    private JButton btnViewTask;
    private JButton btnBack;
    private JButton btnPrev;
    private JButton btnNext;
    private JButton btnDoublePrev;
    private JButton btnDoubleNext;
    private JPanel workPanel;
    private Panel monthYearPanel;
    private JLabel lblMonthYear;

    private CalendarPanel calendarPanel;
    private DayTimetablePanel dayTimetablePanel;


    enum States {
        calendar, dayTimetable
    }

    private States menuState;

    private static final String[] months = {
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    };

    public TaskManagerView(TaskManagerModel taskManagerModel) {
        this.taskManagerModel = taskManagerModel;
        menuState = States.calendar;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        initGUI();
        addListeners();
        // TODO: DELETE IT
//        NewTaskDialog newTaskDialog = new NewTaskDialog(mainFrame);
//        newTaskDialog.showDialog();
//        mainFrame.dispose();
//        System.exit(0);
    }

    private void initGUI() {
        mainFrame = new JFrame("Task manager");
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        mainFrame.setPreferredSize(new Dimension(1440, 900));
        mainFrame.setMinimumSize(new Dimension(750, 650));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("view/resources/icon.png"));

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
        btnCreateTask = new JButton("Создать задачу");
        leftControlPanel.add(btnCreateTask, c2);
        c2.gridx = 1;
        btnViewTask = new JButton("Просмотр задач");
        leftControlPanel.add(btnViewTask, c2);
        c2.gridx = 2;
        btnBack = new JButton("<- Назад");
        btnBack.setVisible(false);
        leftControlPanel.add(btnBack, c2);

        c1.gridx = 1;
        c1.weightx = 0;
        c1.anchor = GridBagConstraints.EAST;
        contentPane.add(rightControlPanel, c1);
        rightControlPanel.setLayout(new GridBagLayout());


        c2.gridx = 0;
        btnDoublePrev = new JButton("  <<  ");
        rightControlPanel.add(btnDoublePrev, c2);
        c2.gridx = 1;
        c2.insets = new Insets(0, 5, 0, 0);
        btnPrev = new JButton("  <  ");
        rightControlPanel.add(btnPrev, c2);
        c2.gridx = 2;
        c2.insets = new Insets(0, 0, 0, 5);
        btnNext = new JButton("  >  ");
        rightControlPanel.add(btnNext, c2);
        c2.gridx = 3;
        c2.insets = new Insets(0, 0, 0, 0);
        btnDoubleNext = new JButton("  >>  ");
        rightControlPanel.add(btnDoubleNext, c2);

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
        workPanel = new JPanel();
        workPanel.setLayout(new CardLayout());
        mainFrame.add(workPanel);

        calendarPanel = new  CalendarPanel(taskManagerModel);
        workPanel.add(calendarPanel, "Calendar");

        dayTimetablePanel = new DayTimetablePanel(taskManagerModel);
        dayTimetablePanel.setVisible(false);
        workPanel.add(dayTimetablePanel, "DayTimetable");

        updateMonthLabel();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void addListeners() {
        // Кнопка назад
        btnBack.addActionListener(e -> {
            btnBack.setVisible(false);
            menuState = States.calendar;
            updateMonthLabel();

            CardLayout cardLayout = (CardLayout)(workPanel.getLayout());
            cardLayout.show(workPanel, "Calendar");
        });

        btnPrev.addActionListener(e -> {
            switch (menuState) {
                case calendar:
                    calendarPanel.setMonth(calendarPanel.getMonth() - 1);
                    break;

                case dayTimetable:
                    Calendar date = dayTimetablePanel.getDate();
                    date.add(Calendar.DAY_OF_MONTH, -1);
                    dayTimetablePanel.setDate(date);
            }
            updateMonthLabel();
        });

        btnNext.addActionListener(e -> {
            switch (menuState) {
                case calendar:
                    calendarPanel.setMonth(calendarPanel.getMonth() + 1);
                    break;

                case dayTimetable:
                    Calendar date = dayTimetablePanel.getDate();
                    date.add(Calendar.DAY_OF_MONTH, 1);
                    dayTimetablePanel.setDate(date);
            }
            updateMonthLabel();
        });

        btnDoublePrev.addActionListener(e1 -> {
            switch (menuState) {
                case calendar:
                    calendarPanel.setYear(calendarPanel.getYear() - 1);
                    break;

                case dayTimetable:
                    Calendar date = dayTimetablePanel.getDate();
                    date.add(Calendar.MONTH, - 1);
                    dayTimetablePanel.setDate(date);
            }
            updateMonthLabel();
        });

        btnDoubleNext.addActionListener(e1 -> {
            switch (menuState) {
                case calendar:
                    calendarPanel.setYear(calendarPanel.getYear() + 1);
                    break;

                case dayTimetable:
                    Calendar date = dayTimetablePanel.getDate();
                    date.add(Calendar.MONTH, + 1);
                    dayTimetablePanel.setDate(date);
            }
            updateMonthLabel();
        });

        NewTaskDialog newTaskDialog = new NewTaskDialog(mainFrame);
        btnCreateTask.addActionListener(e -> {
            if (newTaskDialog.showDialog() == NewTaskDialog.OK) {
                taskManagerModel.addTask(newTaskDialog.getResult());
            }
        });


        calendarPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                // По двойному клику
                if (e.getClickCount() == 2) {
                    int x = e.getX();
                    int y = e.getY();

                    if (y > CalendarPanel.WEEKDAYS_HEIGHT) {
                        int r = (int) ((y - CalendarPanel.WEEKDAYS_HEIGHT) / calendarPanel.getYGridStep());
                        int c = (int) (x / calendarPanel.getXGridStep());

                        Calendar date = calendarPanel.getDateOfCell(r, c);
                        dayTimetablePanel.setDate(date);
                        CardLayout cardLayout = (CardLayout) (workPanel.getLayout());
                        cardLayout.show(workPanel, "DayTimetable");

                        menuState = States.dayTimetable;
                        updateMonthLabel();

                        btnBack.setVisible(true);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public void updateMonthLabel() {
        switch (menuState) {
            case calendar:
                lblMonthYear.setText(months[calendarPanel.getMonth()] + " " + calendarPanel.getYear());
                break;

            case dayTimetable:
                Calendar date = dayTimetablePanel.getDate();

                String month = months[date.get(Calendar.MONTH)];
                if (date.get(Calendar.MONTH) == 2 || date.get(Calendar.MONTH) == 7) {
                    month = month + 'а';
                } else {
                    month = month.substring(0, month.length() - 1) + 'я';
                }
                lblMonthYear.setText(date.get(Calendar.DAY_OF_MONTH) + " " + month + ", " + date.get(Calendar.YEAR));
                break;

        }
        dayTimetablePanel.repaint();
    }
}
