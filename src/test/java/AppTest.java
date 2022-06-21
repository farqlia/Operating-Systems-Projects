import simulation_5.visualization.App;

import javax.swing.*;

public class AppTest {

    public static void main(String[] args) {

        App app = new App();
        app.setUp("helpfulstudent", 1, 20, 20);

        SwingUtilities.invokeLater(() -> app.setVisible(true));

    }

}
