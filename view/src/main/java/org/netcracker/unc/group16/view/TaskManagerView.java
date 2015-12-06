package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.controller.NotificatorModel;
import org.netcracker.unc.group16.model.Observer;
import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.model.Appointment;
import org.netcracker.unc.group16.model.TaskManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;


public class TaskManagerView extends JFrame implements ProgramInterface, Observer {
    TaskManagerModel taskManagerModel;
    NotificatorModel notificatorModel;

//    private JFrame mainFrame;
    private Panel leftControlPanel;
    private Panel rightControlPanel;
    private JButton btnCreateAppointment;
    private JButton btnCreateTask;
    private JButton btnViewTasks;
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
    private JTable tasksTable;


    enum States {
        calendar, dayTimetable, tasksTable
    }

    private States menuState;


    public TaskManagerView(TaskManagerModel taskManagerModel) {
        this.taskManagerModel = taskManagerModel;
        taskManagerModel.registerObserver(this);
        menuState = States.calendar;

        initGUI();
        addListeners();
    }

    public TaskManagerView(NotificatorModel notificatorModel){
        this.notificatorModel = notificatorModel;
        this.taskManagerModel = notificatorModel.getTaskManagerModel();

        menuState = States.calendar;

        initGUI();
        addListeners();
    }

    private void initGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        setTitle("Task manager");
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 800));
        setMinimumSize(new Dimension(750, 650));
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("view/resources/icon.png"));

        setLayout(new BorderLayout());
        JPanel contentPane = new JPanel();
        add(contentPane, BorderLayout.PAGE_START);

        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();


    // First line
        leftControlPanel = new Panel();
        leftControlPanel.setLayout(new BoxLayout(leftControlPanel, BoxLayout.LINE_AXIS));

        btnCreateAppointment = new JButton("Создать встречу");
        leftControlPanel.add(btnCreateAppointment);

        leftControlPanel.add(Box.createHorizontalStrut(5));

        btnCreateTask = new JButton("Создать задачу");
        leftControlPanel.add(btnCreateTask);

        leftControlPanel.add(Box.createHorizontalStrut(5));

        btnViewTasks = new JButton("Просмотр задач");
        leftControlPanel.add(btnViewTasks);

        leftControlPanel.add(Box.createHorizontalStrut(5));

        btnBack = new JButton("<- Назад");
        btnBack.setVisible(false);
        leftControlPanel.add(btnBack);


        rightControlPanel = new Panel();
        rightControlPanel.setLayout(new BoxLayout(rightControlPanel, BoxLayout.LINE_AXIS));

        btnDoublePrev = new JButton(" << ");
        rightControlPanel.add(btnDoublePrev);

        rightControlPanel.add(Box.createHorizontalStrut(5));

        btnPrev = new JButton("  <  ");
        rightControlPanel.add(btnPrev);

        btnNext = new JButton("  >  ");
        rightControlPanel.add(btnNext);

        rightControlPanel.add(Box.createHorizontalStrut(5));

        btnDoubleNext = new JButton(" >> ");
        rightControlPanel.add(btnDoubleNext);


        c1.gridx = 0;
        c1.insets = new Insets(5, 5, 5, 0);
        c1.anchor = GridBagConstraints.NORTHWEST;
        contentPane.add(leftControlPanel, c1);

        c1.gridx = 0;
        c1.weightx = 1.0;
        c1.anchor = GridBagConstraints.CENTER;
        contentPane.add(Box.createHorizontalGlue(), c1);

        c1.gridx = 2;
        c1.weightx = 0.0;
        c1.insets = new Insets(5, 0, 5, 5);
        c1.anchor = GridBagConstraints.NORTHEAST;
        contentPane.add(rightControlPanel, c1);


    // Second line
        monthYearPanel = new Panel();
        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridwidth = 3;
        c1.anchor = GridBagConstraints.CENTER;
        contentPane.add(monthYearPanel, c1);
        lblMonthYear = new JLabel();
        lblMonthYear.setFont(new Font("Verdana", 0, 15));
        monthYearPanel.add(lblMonthYear);


    // Third line
        workPanel = new JPanel();
        workPanel.setLayout(new CardLayout());
        add(workPanel);

        calendarPanel = new  CalendarPanel(taskManagerModel);
        workPanel.add(calendarPanel, "Calendar");

        dayTimetablePanel = new DayTimetablePanel(taskManagerModel);
        workPanel.add(dayTimetablePanel, "DayTimetable");

        tasksTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(tasksTable);
        workPanel.add(scrollPane, "TasksTable");

        updateMonthLabel();
        pack();
        setVisible(true);
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
                    break;

                case tasksTable:
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
                    break;

                case tasksTable:
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
                    break;

                case tasksTable:
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
                    break;

                case tasksTable:
            }
            updateMonthLabel();
        });

        btnCreateAppointment.addActionListener(e -> {
            try {
                NewTaskDialog newTaskDialog = new NewTaskDialog(this, Appointment.class);
                if (newTaskDialog.showDialog() == NewTaskDialog.OK) {
                    taskManagerModel.addTask(newTaskDialog.getResult());
                    calendarPanel.repaint();
                }
            } catch (IllegalAccessException | InstantiationException ex) {
                ex.printStackTrace();
            }
        });

        btnCreateTask.addActionListener(e -> {
            try {
                NewTaskDialog newTaskDialog = new NewTaskDialog(this, Task.class);
                if (newTaskDialog.showDialog() == NewTaskDialog.OK) {
                    taskManagerModel.addTask(newTaskDialog.getResult());
                    calendarPanel.repaint();
                }
            } catch (IllegalAccessException | InstantiationException ex) {
                ex.printStackTrace();
            }
        });

        btnViewTasks.addActionListener(e -> {
            TasksTableModel tableModel = null;

            switch (menuState) {
                case calendar:
                    Calendar date = Calendar.getInstance();
                    date.set(Calendar.MONTH, calendarPanel.getMonth());
                    date.set(Calendar.YEAR, calendarPanel.getYear());

                    tableModel = new TasksTableModel(taskManagerModel, date, false);
                    break;

                case dayTimetable:
                    tableModel = new TasksTableModel(taskManagerModel, dayTimetablePanel.getDate(), true);
                    break;

                default:
                    return;
            }

//            DefaultTableModel tableModel = new DefaultTableModel(5, 2);

            btnBack.setVisible(true);
            tasksTable.setModel(tableModel);
            menuState = States.tasksTable;
            CardLayout cardLayout = (CardLayout) (workPanel.getLayout());
            cardLayout.show(workPanel, "TasksTable");
        });

        calendarPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // По двойному клику
                if (e.getClickCount() == 2) {
                    int x = e.getX();
                    int y = e.getY();

                    if (y > CalendarPanel.WEEKDAYS_HEIGHT) {
                        int row = (int) ((y - CalendarPanel.WEEKDAYS_HEIGHT) / calendarPanel.getYGridStep());
                        int column = (int) (x / calendarPanel.getXGridStep());

                        Calendar date = calendarPanel.getDateOfCell(row, column);
                        dayTimetablePanel.setDate(date);

                        btnBack.setVisible(true);
                        menuState = States.dayTimetable;
                        updateMonthLabel();

                        CardLayout cardLayout = (CardLayout) (workPanel.getLayout());
                        cardLayout.show(workPanel, "DayTimetable");

                        revalidate();
                        repaint();
                    }
                }
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

    public void updateMonthLabel() {
        String month;
        switch (menuState) {
            case calendar:
                month = Month.of(calendarPanel.getMonth() + 1).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
                lblMonthYear.setText(month + " " + calendarPanel.getYear());
                break;

            case dayTimetable:
                Calendar date = dayTimetablePanel.getDate();
                int dayOfWeekInt = date.get(Calendar.DAY_OF_WEEK);
                String dayOfWeek = DayOfWeek.of((dayOfWeekInt > 1) ? (dayOfWeekInt - 1) : 7).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
                month = Month.of(calendarPanel.getMonth() + 1).getDisplayName(TextStyle.FULL, Locale.getDefault());
                lblMonthYear.setText(date.get(Calendar.DAY_OF_MONTH) + " " + month + ", " + date.get(Calendar.YEAR) + " – " + dayOfWeek);
                break;

        }
    }

    //Если как-то изменяются эти два поля - все наблюдатели об этом знают
    public void update(Map<Integer, Task> hashMapTasks, Integer tasksCnt){

    }
}
