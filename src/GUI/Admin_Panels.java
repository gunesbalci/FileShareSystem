package GUI;

import AppFile.FileServices;
import Team.Team;
import Team.TeamServices;
import User.User;
import User.UserServices;
import User.UserDBServices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import static GUI.GUI.user;

public class Admin_Panels
{
    private static ActionListener MiddlePanelHandler;
    private static Dictionary<JRadioButton, User> radioButton_user; // Store globally
    private static ButtonGroup userButtonGroup;

    public static JPanel Admin_Screen()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);

        JPanel leftStaticPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel();
        JPanel MiddlePanel = new JPanel();
        JPanel leftButtonsPanel = new JPanel(new GridLayout(2,1,0,10));
        JButton showUsersB = new JButton("Show Users");
        JButton showLogFilesB = new JButton("Show Log Files");

        leftStaticPanel.setPreferredSize(new Dimension(400, 1080));
        rightPanel.setPreferredSize(new Dimension(400, 1080));
        MiddlePanel.setPreferredSize(new Dimension(820, 1080));
        leftButtonsPanel.setPreferredSize(new Dimension(300,110));

        leftButtonsPanel.setAlignmentX(0);

        leftStaticPanel.setBackground(Color.red);
        rightPanel.setBackground(Color.green);
        MiddlePanel.setBackground(Color.blue);

        MiddlePanelHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JPanel middlecontent = new JPanel();
                JPanel rightcontent = new JPanel();

                User selectedUser = getSelectedUser();

                switch (e.getActionCommand())
                {
                    case "showUsers":
                        middlecontent = showUsersPanel();
                        rightcontent = showUsersRIGHTPanel();
                        break;
                    case "showLogFiles":
                        middlecontent = showLogFilesPanel();
                        rightcontent = showLogFilesRIGHTPanel();
                        break;

                    case "UserInfo":
                        if(selectedUser != null)
                        {
                            middlecontent = UserInfoPanel(selectedUser);
                        }
                        break;
                    case "UserDelete":
                        if(selectedUser != null)
                        {
                            middlecontent = UserDeletePanel(selectedUser);
                        }
                        break;
                    case "Request":
                        middlecontent = showLogFilesPanel();
                        break;
                    case "Limit":
                        if(selectedUser != null)
                        {
                            middlecontent = UserLimitPanel(selectedUser);
                        }
                        break;
                }

                MiddlePanel.removeAll();
                MiddlePanel.add(middlecontent);
                MiddlePanel.revalidate();
                MiddlePanel.repaint();

                rightPanel.removeAll();
                rightPanel.add(rightcontent);
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        };

        showUsersB.setActionCommand("showUsers");
        showLogFilesB.setActionCommand("showLogFiles");

        showUsersB.addActionListener(MiddlePanelHandler);
        showLogFilesB.addActionListener(MiddlePanelHandler);

        leftStaticPanel.add(showUsersB);
        leftStaticPanel.add(showLogFilesB);

        panel.add(leftStaticPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        panel.add(MiddlePanel, BorderLayout.CENTER);

        return panel;
    }

    public static JPanel showUsersPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(820, 1080));

        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new GridLayout(5,4));
        radioButtonPanel.setPreferredSize(new Dimension(600,140));

        java.util.List<User> userList = UserServices.AllUsersExceptOne(user.getId());
        radioButton_user = new Hashtable<JRadioButton, User>();
        userButtonGroup = new ButtonGroup();
        for (User users: userList)
        {
            JRadioButton radioButton = new JRadioButton(users.getUsername());
            radioButtonPanel.add(radioButton);
            radioButton_user.put(radioButton, users);
            userButtonGroup.add(radioButton);
        }


        panel.add(radioButtonPanel);

        return panel;
    }

    public static JPanel showLogFilesPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(820, 1080));

        return panel;
    }

    public static JPanel showUsersRIGHTPanel()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 1080));

        JPanel insidePanel = new JPanel(new GridLayout(4,1,0,10));
        insidePanel.setPreferredSize(new Dimension(300, 250));

        JButton infoB = new JButton("Info");
        JButton deleteB = new JButton("Delete User");
        JButton requestB = new JButton("Show Password Request");
        JButton limitB = new JButton("Limit File Storage");

        infoB.setActionCommand("UserInfo");
        deleteB.setActionCommand("UserDelete");
        requestB.setActionCommand("Request");
        limitB.setActionCommand("Limit");

        infoB.addActionListener(MiddlePanelHandler);
        deleteB.addActionListener(MiddlePanelHandler);
        requestB.addActionListener(MiddlePanelHandler);
        limitB.addActionListener(MiddlePanelHandler);

        insidePanel.add(infoB);
        insidePanel.add(deleteB);
        insidePanel.add(requestB);
        insidePanel.add(limitB);

        panel.add(insidePanel);

        return panel;
    }

    public static JPanel showLogFilesRIGHTPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(400, 1080));

        return panel;
    }

    public static JPanel UserInfoPanel(User user)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(820, 1080));

        String usernameString = "Username: " + user.getUsername();
        String passworString = "Password: " + user.getPassword();

        List<Team> teamList = TeamServices.GetMemberTable(user.getId());
        File[] fileList = FileServices.GetUserFiles(user.getId());

        JLabel username = new JLabel(usernameString);
        JLabel password = new JLabel(passworString);

        JPanel teamPanel = new JPanel();
        JPanel filePanel = new JPanel();
        teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.Y_AXIS));
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));

        teamPanel.add(new JLabel("Teams:"));
        if(!teamList.isEmpty())
        {
            for (Team team : teamList)
            {
                JLabel teamLabel = new JLabel(team.getId());
                teamPanel.add(teamLabel);
            }
        }
        else
        {
            teamPanel.add(new JLabel("Has no teams."));
        }


        filePanel.add(new JLabel("Files:"));
        if(fileList != null)
        {
            for(File file : fileList)
            {
                JLabel fileLabel = new JLabel(file.getName());
                filePanel.add(fileLabel);
            }
        }
        else
        {
            filePanel.add(new JLabel("Has no files."));
        }

        JPanel insidePanel = new JPanel();
        insidePanel.setLayout(new BoxLayout(insidePanel, BoxLayout.Y_AXIS));
        insidePanel.setPreferredSize(new Dimension(600, 1000));

        insidePanel.add(username);
        insidePanel.add(Box.createVerticalStrut(10));
        insidePanel.add(password);
        insidePanel.add(Box.createVerticalStrut(10));
        insidePanel.add(teamPanel);
        insidePanel.add(Box.createVerticalStrut(10));
        insidePanel.add(filePanel);

        panel.add(insidePanel);

        return panel;
    }

    public static JPanel UserDeletePanel(User user)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(820, 1080));

        JLabel resultLabel = new JLabel();
        String result = "User " + user.getUsername() + " is deleted successfully.";

        UserDBServices.DeleteUser(user.getId());
        resultLabel.setText(result);

        panel.add(resultLabel);

        return panel;
    }

    public static JPanel UserLimitPanel(User user)
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(820, 1080));

        JPanel insidePanel = new JPanel();
        insidePanel.setLayout(new BoxLayout(insidePanel, BoxLayout.Y_AXIS));

        JLabel result = new JLabel();
        result.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel("Select a file size limit:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel radiobuttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JRadioButton rb50 = new JRadioButton("50 mB");
        JRadioButton rb100 = new JRadioButton("100 mB");
        JRadioButton rb200 = new JRadioButton("200 mB");

        ButtonGroup radiobuttonGroup = new ButtonGroup();
        radiobuttonGroup.add(rb50);
        radiobuttonGroup.add(rb100);
        radiobuttonGroup.add(rb200);

        radiobuttonPanel.add(rb50);
        radiobuttonPanel.add(rb100);
        radiobuttonPanel.add(rb200);

        JButton setLimit = new JButton("Set Limit");
        setLimit.setAlignmentX(Component.CENTER_ALIGNMENT);

        ActionListener setLimitHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(rb50.isSelected())
                {
                    String limit = "50";
                    System.out.println(limit);
                    UserDBServices.UpdateUser(user.getId(), "max_fileSize", limit);
                    result.setText("File size limit is set to 50 mB.");
                }
                else if(rb100.isSelected())
                {
                    String limit = "100";
                    System.out.println(limit);
                    UserDBServices.UpdateUser(user.getId(), "max_fileSize", limit);
                    result.setText("File size limit is set to 100 mB.");
                }
                else if(rb200.isSelected())
                {
                    String limit = "200";
                    System.out.println(limit);
                    UserDBServices.UpdateUser(user.getId(), "max_fileSize", limit);
                    result.setText("File size limit is set to 200 mB.");
                }
                else
                {
                    result.setText("Select a size limit first.");
                }
            }
        };

        setLimit.addActionListener(setLimitHandler);

        insidePanel.add(result);
        insidePanel.add(Box.createVerticalStrut(10));
        insidePanel.add(label);
        insidePanel.add(Box.createVerticalStrut(10));
        insidePanel.add(radiobuttonPanel);
        insidePanel.add(Box.createVerticalStrut(10));
        insidePanel.add(setLimit);

        panel.add(insidePanel);

        return panel;
    }

    public static User getSelectedUser()
    {
        if (userButtonGroup != null && radioButton_user != null)
        {
            ButtonModel selectedModel = userButtonGroup.getSelection();
            if (selectedModel != null)
            {
                for (Enumeration<JRadioButton> keys = radioButton_user.keys(); keys.hasMoreElements(); )
                {
                    JRadioButton button = keys.nextElement();
                    if (button.getModel() == selectedModel)
                    {
                        User selectedUser = radioButton_user.get(button);
                        if (selectedUser != null)
                        {
                            return selectedUser;
                        }
                        break;
                    }
                }
            }
        }
        return null;
    }
}