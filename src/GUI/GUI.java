package GUI;

import LOG.SignInOut_LOG;
import User.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
        JPanel panel = new JPanel();
        panel.setOpaque(true);

        JButton signOutB = new JButton("Sign Out");

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

        signOutB.addActionListener(signOutBHandler);

        panel.add(signOutB);

        return panel;
    }
}
