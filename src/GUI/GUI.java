package GUI;

import LOG.SignInOut_LOG;
import LOG.Team_LOG;
import Team.Team;
import Team.TeamServices;
import User.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

        ActionListener createTeamBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MiddlePanel.removeAll();
                MiddlePanel.add(TeamCreatePanel());
                MiddlePanel.revalidate();
                MiddlePanel.repaint();
            }
        };

        ActionListener shareFileBHandler = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MiddlePanel.removeAll();
                MiddlePanel.add(FileSharePanel());
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

        createTeamB.addActionListener(createTeamBHandler);
        shareFileB.addActionListener(shareFileBHandler);
        signOutB.addActionListener(signOutBHandler);

        UserActionPanel.add(signOutB);
        AppActionButtonsPanel.add(createTeamB);
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
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(820, 1080));

        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(5,3));

        List<Team> teamList = TeamServices.GetMemberTable(user.getId());
        Dictionary<JCheckBox, Team> checkbox_user = new Hashtable<JCheckBox, Team>();
        for (Team team: teamList)
        {
            JCheckBox checkBox = new JCheckBox(team.getName());
            checkboxPanel.add(checkBox);
            checkbox_user.put(checkBox, team);
        }

        panel.add(checkboxPanel);
        return panel;
    }
}
