package GUI;

import AppFile.FileServices;
import LOG.ShareUpload_LOG;
import LOG.SignInOut_LOG;
import LOG.Team_LOG;
import Team.Team;
import Team.TeamServices;
import User.User;
import User.UserServices;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import static GUI.GUI.*;

public class User_Panels
{
    public static JPanel UserScreen()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);

        JPanel AppActionPanel = new JPanel(new GridBagLayout());
        JPanel UserActionPanel = new JPanel();
        JPanel MiddlePanel = new JPanel();
        JPanel AppActionButtonsPanel = new JPanel(new GridLayout(2,1,0,10));
        JButton createTeamB = new JButton("Create Team");
        JButton fileUploadB = new JButton("Upload File");
        JButton fileDownloadB = new JButton("Download File");
        JButton editFileB = new JButton("Edit File");
        JButton shareFileB = new JButton("Share File");
        JButton changeUsernameB = new JButton("Change Username");
        JButton signOutB = new JButton("Sign Out");

        AppActionPanel.setPreferredSize(new Dimension(400, 1080));
        UserActionPanel.setPreferredSize(new Dimension(400, 1080));
        MiddlePanel.setPreferredSize(new Dimension(820, 1080));
        AppActionButtonsPanel.setPreferredSize(new Dimension(300,110));

        AppActionButtonsPanel.setAlignmentX(0);

        AppActionPanel.setBackground(Color.red);
        UserActionPanel.setBackground(Color.green);
        MiddlePanel.setBackground(Color.blue);


        ActionListener MiddlePanelHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JPanel middlecontent = new JPanel();

                switch (e.getActionCommand())
                {
                    case "createTeam":
                        middlecontent = TeamCreatePanel();
                        break;
                    case "fileUpload":
                        middlecontent = FileUploadPanel();
                        break;
                    case "fileDownload":
                        middlecontent = FileDownloadPanel();
                        break;
                    case "fileEdit":
                        middlecontent = FileEditPanel();
                        break;
                    case "fileShare":
                        middlecontent = FileSharePanel();
                        break;
                    case "changeUsername":
                        middlecontent = ChangeUsernamePanel();
                }

                MiddlePanel.removeAll();
                MiddlePanel.add(middlecontent);
                MiddlePanel.revalidate();
                MiddlePanel.repaint();
            }
        };

        ActionListener signOutBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.getContentPane().removeAll();
                frame.add(SignInScreen());
                frame.revalidate();
                frame.repaint();

                SignInOut_LOG.LogSignOut(user.getId(), "successful");
                user = null;
            }
        };

        createTeamB.setActionCommand("createTeam");
        fileUploadB.setActionCommand("fileUpload");
        fileDownloadB.setActionCommand("fileDownload");
        editFileB.setActionCommand("fileEdit");
        shareFileB.setActionCommand("fileShare");
        changeUsernameB.setActionCommand("changeUsername");

        createTeamB.addActionListener(MiddlePanelHandler);
        fileUploadB.addActionListener(MiddlePanelHandler);
        fileDownloadB.addActionListener(MiddlePanelHandler);
        editFileB.addActionListener(MiddlePanelHandler);
        shareFileB.addActionListener(MiddlePanelHandler);
        changeUsernameB.addActionListener(MiddlePanelHandler);

        signOutB.addActionListener(signOutBHandler);

        UserActionPanel.add(changeUsernameB);
        UserActionPanel.add(signOutB);
        AppActionButtonsPanel.add(createTeamB);
        AppActionButtonsPanel.add(fileUploadB);
        AppActionButtonsPanel.add(fileDownloadB);
        AppActionButtonsPanel.add(editFileB);
        AppActionButtonsPanel.add(shareFileB);
        AppActionPanel.add(AppActionButtonsPanel);

        panel.add(AppActionPanel, BorderLayout.WEST);
        panel.add(UserActionPanel, BorderLayout.EAST);
        panel.add(MiddlePanel, BorderLayout.CENTER);

        return panel;
    }

    public static JPanel TeamCreatePanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(820, 1080));

        JPanel teamNamePanel = new JPanel();
        teamNamePanel.setLayout(new GridLayout(3,1,0,10));
        teamNamePanel.setPreferredSize(new Dimension(400,140));

        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(5,3));
        checkboxPanel.setPreferredSize(new Dimension(400,140));

        java.util.List<User> userList = UserServices.AllUsersExceptOne(user.getId());
        Dictionary<JCheckBox, User> checkbox_user = new Hashtable<JCheckBox, User>();
        for (User users: userList)
        {
            JCheckBox checkBox = new JCheckBox(users.getUsername());
            checkboxPanel.add(checkBox);
            checkbox_user.put(checkBox, users);
        }
        JButton createB = new JButton("Create Team");
        JTextField textField = new JTextField();
        JLabel nameTeam = new JLabel("Team name:");

        ActionListener createBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String teamName = textField.getText();
                Enumeration<JCheckBox> keys = checkbox_user.keys();
                java.util.List<String> memberList = new ArrayList<>();
                while (keys.hasMoreElements())
                {
                    JCheckBox checkBox = keys.nextElement();
                    if(checkBox.isSelected())
                    {
                        User user = checkbox_user.get(checkBox);
                        memberList.add(user.getId());
                    }
                }
                memberList.add(user.getId());
                Team team = TeamServices.CreateTable(teamName, memberList);
                Team_LOG.LogTeam(user.getId(), team.getId());
            }
        };

        createB.addActionListener(createBHandler);

        teamNamePanel.add(nameTeam);
        teamNamePanel.add(textField);
        teamNamePanel.add(createB);
        panel.add(checkboxPanel);
        panel.add(teamNamePanel);

        return panel;
    }

    public static JPanel FileSharePanel()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(820, 1080));

        JPanel team_file_panels = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JPanel teamPanel = new JPanel(new GridLayout(2,1));
        JPanel filePanel = new JPanel(new GridLayout(2,1));
        JPanel checkboxTeamPanel = new JPanel(new GridLayout(5,3));
        JPanel checkboxFilePanel = new JPanel(new GridLayout(5,3));

        teamPanel.setPreferredSize(new Dimension(350, 300));
        filePanel.setPreferredSize(new Dimension(350, 300));

        teamPanel.setBorder(new LineBorder(Color.gray));
        filePanel.setBorder(new LineBorder(Color.gray));

        java.util.List<Team> teamList = TeamServices.GetMemberTable(user.getId());
        Dictionary<JRadioButton, Team> checkbox_user = new Hashtable<JRadioButton, Team>();
        ButtonGroup teamButtonGroup = new ButtonGroup();
        for (Team team: teamList)
        {
            JRadioButton checkBox = new JRadioButton(team.getName());
            checkboxTeamPanel.add(checkBox);
            checkbox_user.put(checkBox, team);
            teamButtonGroup.add(checkBox);
        }

        File[] fileList = FileServices.GetUserFiles(user.getId());
        Dictionary<JRadioButton, File> checkbox_file = new Hashtable<>();
        ButtonGroup fileButtonGroup = new ButtonGroup();
        if(fileList != null)
        {
            for (File file : fileList)
            {
                JRadioButton checkBox = new JRadioButton(file.getName());
                checkboxFilePanel.add(checkBox);
                checkbox_file.put(checkBox, file);
                fileButtonGroup.add(checkBox);
            }
        }

        JLabel filePanelL = new JLabel("Select a file:");
        JLabel teamPanelL = new JLabel("Select a team:");
        JButton shareFileB = new JButton("Share File");

        ActionListener shareFileBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Team team = new Team();
                boolean teamSelected = false;
                for (AbstractButton button : java.util.Collections.list(teamButtonGroup.getElements()))
                {
                    if (button.isSelected())
                    {
                        team = checkbox_user.get(button);
                        teamSelected = true;
                        break;
                    }
                }

                File file = new File("");
                boolean fileSelected = false;
                for (AbstractButton button : java.util.Collections.list(fileButtonGroup.getElements()))
                {
                    if (button.isSelected())
                    {
                        file = checkbox_file.get(button);
                        fileSelected = true;
                        break;
                    }
                }

                if(teamSelected && fileSelected)
                {
                    FileServices.ShareFile(file, team.getId(), file.getName());
                    ShareUpload_LOG.LogFileShare(user.getId(), "successful", file);
                }
            }
        };

        shareFileB.addActionListener(shareFileBHandler);

        teamPanel.add(teamPanelL);
        teamPanel.add(checkboxTeamPanel);
        filePanel.add(filePanelL);
        filePanel.add(checkboxFilePanel);
        team_file_panels.add(teamPanel);
        team_file_panels.add(filePanel);
        panel.add(team_file_panels);
        panel.add(shareFileB);
        return panel;
    }

    public static JPanel FileUploadPanel()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(820, 1080));

        JPanel insidePael = new JPanel();
        insidePael.setLayout(new BoxLayout(insidePael, BoxLayout.Y_AXIS));

        JLabel result = new JLabel();
        JLabel fileLabel = new JLabel("Select your file:");
        JFileChooser fileChooser = new JFileChooser();
        JButton uploadB = new JButton("Upload File");

        ActionListener uploadBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                File uploadFile = fileChooser.getSelectedFile();
                System.out.println("limit:" + user.getMax_fileSize());
                System.out.println("filesize:" + uploadFile.length());
                if((uploadFile.length() <= user.getMax_fileSize()) || (user.getMax_fileSize() == 0))
                {
                    FileServices.FileUpload(uploadFile, user.getId(), uploadFile.getName());
                    result.setText("File is uploaded successfully.");
                    result.setForeground(Color.green);
                }
                else
                {
                    result.setText("Selected file is too large.");
                    result.setForeground(Color.red);
                }
            }
        };

        uploadB.addActionListener(uploadBHandler);

        insidePael.add(result);
        insidePael.add(Box.createVerticalStrut(10));
        insidePael.add(fileLabel);
        insidePael.add(Box.createVerticalStrut(10));
        insidePael.add(fileChooser);
        insidePael.add(Box.createVerticalStrut(10));
        insidePael.add(uploadB);

        panel.add(insidePael);

        return panel;
    }

    public static JPanel FileEditPanel()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(820, 1080));

        JLabel fileLabel = new JLabel("Select your file:");
        JLabel teamFileLabel = new JLabel("Select your team's file:");
        JButton editFileB = new JButton("Edit File");

        JPanel filePanel = new JPanel(new GridLayout(2,1));
        JPanel radioBFilePanel = new JPanel(new GridLayout(5,3));
        JPanel teamFilePanel = new JPanel(new GridLayout(2,1));
        JPanel radioBTeamFilePanel = new JPanel(new GridLayout(5,3));

        filePanel.setPreferredSize(new Dimension(350, 300));
        teamFilePanel.setPreferredSize(new Dimension(350, 300));

        java.util.List<Team> TeamList = TeamServices.GetMemberTable(user.getId());

        File[] fileList = FileServices.GetUserFiles(user.getId());
        java.util.List<File> teamFileList = FileServices.GetTeamFiles(TeamList);

        Dictionary<JRadioButton, File> checkbox_file = new Hashtable<>();
        ButtonGroup fileButtonGroup = new ButtonGroup();
        if(fileList != null)
        {
            for (File file : fileList)
            {
                JRadioButton radioButton = new JRadioButton(file.getName());
                radioBFilePanel.add(radioButton);
                checkbox_file.put(radioButton, file);
                fileButtonGroup.add(radioButton);
            }
        }
        else
        {
            JLabel errorMessage = new JLabel("No file uploaded.");
            radioBFilePanel.add(errorMessage);
        }

        if(teamFileList != null)
        {
            for (File file : teamFileList)
            {
                JRadioButton radioButton = new JRadioButton(file.getName());
                radioBTeamFilePanel.add(radioButton);
                checkbox_file.put(radioButton, file);
                fileButtonGroup.add(radioButton);
            }
        }
        else
        {
            JLabel errorMessage = new JLabel("No file shared.");
            radioBTeamFilePanel.add(errorMessage);
        }

        ActionListener editFileBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                File file;
                for (AbstractButton button : java.util.Collections.list(fileButtonGroup.getElements()))
                {
                    if (button.isSelected())
                    {
                        file = checkbox_file.get(button);
                        FileServices.EditFile(file);
                        break;
                    }
                }
            }
        };

        editFileB.addActionListener(editFileBHandler);

        filePanel.add(fileLabel);
        filePanel.add(radioBFilePanel);

        teamFilePanel.add(teamFileLabel);
        teamFilePanel.add(radioBTeamFilePanel);

        panel.add(filePanel);
        panel.add(teamFilePanel);
        panel.add(editFileB);

        return panel;
    }

    public static JPanel FileDownloadPanel()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(820, 1080));

        JLabel fileLabel = new JLabel("Select your file:");
        JLabel teamFileLabel = new JLabel("Select your team's file:");
        JButton downloadFileB = new JButton("Download File");

        JPanel filePanel = new JPanel(new GridLayout(2,1));
        JPanel radioBFilePanel = new JPanel(new GridLayout(5,3));
        JPanel teamFilePanel = new JPanel(new GridLayout(2,1));
        JPanel radioBTeamFilePanel = new JPanel(new GridLayout(5,3));

        filePanel.setPreferredSize(new Dimension(350, 300));
        teamFilePanel.setPreferredSize(new Dimension(350, 300));

        java.util.List<Team> TeamList = TeamServices.GetMemberTable(user.getId());

        File[] fileList = FileServices.GetUserFiles(user.getId());
        List<File> teamFileList = FileServices.GetTeamFiles(TeamList);

        Dictionary<JRadioButton, File> checkbox_file = new Hashtable<>();
        ButtonGroup fileButtonGroup = new ButtonGroup();
        if(fileList != null)
        {
            for (File file : fileList)
            {
                JRadioButton radioButton = new JRadioButton(file.getName());
                radioBFilePanel.add(radioButton);
                checkbox_file.put(radioButton, file);
                fileButtonGroup.add(radioButton);
            }
        }
        else
        {
            JLabel errorMessage = new JLabel("No file uploaded.");
            radioBFilePanel.add(errorMessage);
        }

        if(teamFileList != null)
        {
            for (File file : teamFileList)
            {
                JRadioButton radioButton = new JRadioButton(file.getName());
                radioBTeamFilePanel.add(radioButton);
                checkbox_file.put(radioButton, file);
                fileButtonGroup.add(radioButton);
            }
        }
        else
        {
            JLabel errorMessage = new JLabel("No file shared.");
            radioBTeamFilePanel.add(errorMessage);
        }

        ActionListener downloadFileBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                File file;
                for (AbstractButton button : java.util.Collections.list(fileButtonGroup.getElements()))
                {
                    if (button.isSelected())
                    {
                        file = checkbox_file.get(button);
                        FileServices.FileDownload(file, user.getId());
                        ShareUpload_LOG.LogFileDownload(user.getId(), "successful", file);
                        break;
                    }
                }
            }
        };

        downloadFileB.addActionListener(downloadFileBHandler);

        filePanel.add(fileLabel);
        filePanel.add(radioBFilePanel);

        teamFilePanel.add(teamFileLabel);
        teamFilePanel.add(radioBTeamFilePanel);

        panel.add(filePanel);
        panel.add(teamFilePanel);
        panel.add(downloadFileB);

        return panel;
    }

    public static JPanel ChangeUsernamePanel()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(820, 1080));

        JPanel insidePanel = new JPanel(new GridLayout(4,1));
        insidePanel.setPreferredSize(new Dimension(300, 120));

        JLabel warningLabel = new JLabel("");
        JLabel label = new JLabel("New username:");
        JTextField usernameTextField = new JTextField();
        JButton changeUsername = new JButton("Change Username");

        warningLabel.setPreferredSize(new Dimension(300, 30));
        label.setPreferredSize(new Dimension(300, 30));
        usernameTextField.setPreferredSize(new Dimension(300, 30));
        changeUsername.setPreferredSize(new Dimension(300, 30));

        ActionListener changeUsernameHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String newUsername = usernameTextField.getText();

                int code = UserServices.ChangeUsername(user.getId(), newUsername);
                if(code == 0)
                {
                    warningLabel.setForeground(Color.red);
                    warningLabel.setText("Invalid username.");
                }
                if(code == 2)
                {
                    warningLabel.setForeground(Color.green);
                    warningLabel.setText("Username is changed.");
                }
            }
        };

        changeUsername.addActionListener(changeUsernameHandler);

        insidePanel.add(warningLabel);
        insidePanel.add(label);
        insidePanel.add(usernameTextField);
        insidePanel.add(changeUsername);

        panel.add(insidePanel);

        return panel;
    }
}
