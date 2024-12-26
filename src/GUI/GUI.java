package GUI;

import AppFile.FileServices;
import LOG.SignInOut_LOG;
import LOG.Team_LOG;
import Team.Team;
import Team.TeamServices;
import User.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

public class GUI
{
    public static JFrame frame;
    private static User user;
    //Initializes frame and its some settings. Then adds the screen(s).
    public static void InitializeFrame()
    {
        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(1920,1080);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(RegisterScreen());

        frame.setVisible(true);
    }

    //Creates the screen where user registers.
    public static JPanel RegisterScreen()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10,1,0,0));

        panel.setPreferredSize(new Dimension(500, 340));
        panel.setOpaque(true);

        JLabel alertLabel = new JLabel("");
        JLabel emptyLabel = new JLabel("");
        JLabel usernameLBL = new JLabel("Username:");
        JLabel passwordLBL = new JLabel("Password:");
        JLabel password2LBL = new JLabel("Confirm Password:");
        JTextField usernameTF = new JTextField(20);
        JPasswordField passwordTF = new JPasswordField(20);
        JPasswordField password2TF = new JPasswordField(20);
        JButton registerB = new JButton("Register");
        JButton toSignInB = new JButton("Sign In");

        alertLabel.setPreferredSize(new Dimension(500,30));
        emptyLabel.setPreferredSize(new Dimension(500,30));
        usernameLBL.setPreferredSize(new Dimension(500,30));
        passwordLBL.setPreferredSize(new Dimension(500,30));
        password2LBL.setPreferredSize(new Dimension(500,30));
        usernameTF.setPreferredSize(new Dimension(500,30));
        passwordTF.setPreferredSize(new Dimension(500,30));
        password2TF.setPreferredSize(new Dimension(500,30));
        registerB.setPreferredSize(new Dimension(500,50));
        toSignInB.setPreferredSize(new Dimension(500,50));

        alertLabel.setHorizontalAlignment(JLabel.CENTER);

        ActionListener RegisterBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(Arrays.equals(passwordTF.getPassword(), password2TF.getPassword()))
                {
                    int code = UserServices.Register(usernameTF.getText(), Arrays.toString(passwordTF.getPassword()));
                    if(code == 2)
                    {
                        alertLabel.setText("Register Successful");
                        alertLabel.setForeground(Color.green);
                    }
                    else
                    {
                        alertLabel.setText("Register Failed");
                        alertLabel.setForeground(Color.red);
                    }
                }
                else
                {
                    alertLabel.setText("Register Failed");
                    alertLabel.setForeground(Color.red);
                }
            }
        };

        ActionListener toSignInBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.getContentPane().removeAll();
                frame.add(SignInScreen());
                frame.revalidate();
                frame.repaint();
            }
        };

        registerB.addActionListener(RegisterBHandler);
        toSignInB.addActionListener(toSignInBHandler);

        panel.add(alertLabel);
        panel.add(usernameLBL);
        panel.add(usernameTF);
        panel.add(passwordLBL);
        panel.add(passwordTF);
        panel.add(password2LBL);
        panel.add(password2TF);
        panel.add(emptyLabel);
        panel.add(registerB);
        panel.add(toSignInB);

        return panel;
    }

    //Creates the screen where user signs in.
    public static JPanel SignInScreen()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10,1,0,0));

        panel.setPreferredSize(new Dimension(500, 280));
        panel.setOpaque(true);

        JLabel alertLabel = new JLabel("");
        JLabel emptyLabel = new JLabel("");
        JLabel usernameLBL = new JLabel("Username:");
        JLabel passwordLBL = new JLabel("Password:");
        JTextField usernameTF = new JTextField(20);
        JPasswordField passwordTF = new JPasswordField(20);
        JButton SignInB = new JButton("Sign In");
        JButton toRegisterB = new JButton("Register");


        alertLabel.setPreferredSize(new Dimension(500,30));
        emptyLabel.setPreferredSize(new Dimension(500,30));
        usernameLBL.setPreferredSize(new Dimension(500,30));
        passwordLBL.setPreferredSize(new Dimension(500,30));
        usernameTF.setPreferredSize(new Dimension(500,30));
        passwordTF.setPreferredSize(new Dimension(500,30));
        SignInB.setPreferredSize(new Dimension(500,50));
        toRegisterB.setPreferredSize(new Dimension(500,50));

        alertLabel.setHorizontalAlignment(JLabel.CENTER);

        ActionListener SignInBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int code = UserServices.SignIn(usernameTF.getText(), Arrays.toString(passwordTF.getPassword()));
                if(code == 2)
                {
                    frame.getContentPane().removeAll();
                    frame.add(UserScreen());
                    frame.revalidate();
                    frame.repaint();

                    user = UserDBServices.GetUserWname(usernameTF.getText());
                    SignInOut_LOG.LogSignIn(user.getId(), "successful");
                }
                else if(code == 1)
                {
                    alertLabel.setText("Sign In Failed");
                    alertLabel.setForeground(Color.red);

                    user = UserDBServices.GetUserWname(usernameTF.getText());
                    SignInOut_LOG.LogSignIn(user.getId(), "failed");
                    user = null;
                }
                else
                {
                    alertLabel.setText("Sign In Failed");
                    alertLabel.setForeground(Color.red);
                }
            }
        };

        ActionListener toRegisterBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.getContentPane().removeAll();
                frame.add(RegisterScreen());
                frame.revalidate();
                frame.repaint();
            }
        };

        SignInB.addActionListener(SignInBHandler);
        toRegisterB.addActionListener(toRegisterBHandler);

        panel.add(alertLabel);
        panel.add(usernameLBL);
        panel.add(usernameTF);
        panel.add(passwordLBL);
        panel.add(passwordTF);
        panel.add(emptyLabel);
        panel.add(SignInB);
        panel.add(toRegisterB);

        return panel;
    }

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

        createTeamB.addActionListener(MiddlePanelHandler);
        fileUploadB.addActionListener(MiddlePanelHandler);
        fileDownloadB.addActionListener(MiddlePanelHandler);
        editFileB.addActionListener(MiddlePanelHandler);
        shareFileB.addActionListener(MiddlePanelHandler);
        signOutB.addActionListener(signOutBHandler);

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

        List<User> userList = UserServices.AllUsersExceptOne(user.getId());
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
                List<String> memberList = new ArrayList<>();
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

        List<Team> teamList = TeamServices.GetMemberTable(user.getId());
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
        for (File file : fileList)
        {
            JRadioButton checkBox = new JRadioButton(file.getName());
            checkboxFilePanel.add(checkBox);
            checkbox_file.put(checkBox, file);
            fileButtonGroup.add(checkBox);
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

        JLabel fileLabel = new JLabel("Select your file:");
        JFileChooser fileChooser = new JFileChooser();
        JButton uploadB = new JButton("Upload File");

        ActionListener uploadBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                File uploadFile = fileChooser.getSelectedFile();
                FileServices.FileUpload(uploadFile, user.getId(), uploadFile.getName());
            }
        };

        uploadB.addActionListener(uploadBHandler);

        panel.add(fileLabel);
        panel.add(fileChooser);
        panel.add(uploadB);

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

        List<Team> TeamList = TeamServices.GetMemberTable(user.getId());

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

        List<Team> TeamList = TeamServices.GetMemberTable(user.getId());

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
}
