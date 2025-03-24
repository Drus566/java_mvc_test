package iplm.gui.window.detail;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatLineBorder;
import iplm.gui.button.DirectoryButton;
import iplm.gui.button.SearchButton;
import iplm.gui.button.UpdateButton;
import iplm.gui.textfield.InputText;
import iplm.gui.window.AWindow;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class DetailSettingsWindow extends AWindow {
    private SearchButton search_btn;
    private DirectoryButton open_dir_btn;
    private DirectoryButton select_dir_btn;
    private JPanel drag_and_drop_panel;
    private JFileChooser select_dir_panel;
    private InputText select_dir_path_field;
    private UpdateButton update_btn;

    public DirectoryButton getOpenDirButton() { return open_dir_btn; }
    public SearchButton getSearchButton() { return search_btn; }
    public InputText getSelectDirPathField() { return select_dir_path_field; }
    public DirectoryButton getSelectDirButton() { return select_dir_btn; }
    public UpdateButton getUpdateButton() { return update_btn; }
    public JFileChooser getSelectDirPanel() { return select_dir_panel; }

    public DetailSettingsWindow() {
        build();
        afterBuild();
    }

    @Override
    public void build() {
        setTitle("Настройки");
        m_panel = new JPanel(new MigLayout("inset 10, fill"));

        Color border_color = new Color(211, 211, 211);
        drag_and_drop_panel = new JPanel(new MigLayout("fill"));
        drag_and_drop_panel.setBackground(Color.WHITE);
        drag_and_drop_panel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        drag_and_drop_panel.putClientProperty(FlatClientProperties.STYLE, "arc: 30"); // Sets the corner radius to 20 pixels
        drag_and_drop_panel.setBorder(new FlatLineBorder(new Insets(0,0,0,0), border_color, 1, 30));

        select_dir_path_field = new InputText();
        select_dir_path_field.setText("Путь к папке с деталями...");
        select_dir_path_field.setHorizontalAlignment(JTextField.CENTER);
        select_dir_path_field.putClientProperty(FlatClientProperties.STYLE, "arc: 15"); // Sets the corner radius to 20 pixels
        select_dir_path_field.setToolTipText("Чтобы применить путь, нажмите Enter");

        select_dir_panel = new JFileChooser();

        open_dir_btn = new DirectoryButton();
        open_dir_btn.setText("Открыть папку с деталями");
        open_dir_btn.setToolTipText(null);

        select_dir_btn = new DirectoryButton();
        select_dir_btn.setText("Выбрать папку с деталями");
        select_dir_btn.setToolTipText(null);

        search_btn = new SearchButton();
        search_btn.setText("Автопоиск папки с деталями");
        search_btn.setToolTipText(null);

        update_btn = new UpdateButton();
        update_btn.setText("Синхронизироваться с папкой детали");
        update_btn.setToolTipText("Сканирует папку детали и если в ней есть недобавленные детали, добавляет их");

        arrangeComponents();
    }

    public void arrangeComponents() {
        m_panel.add(drag_and_drop_panel, "grow");

        JPanel wrapper = new JPanel(new MigLayout());
        wrapper.setBackground(drag_and_drop_panel.getBackground());
        wrapper.add(search_btn, "align center, wrap");
        wrapper.add(select_dir_btn, "align center, wrap");
        wrapper.add(open_dir_btn, "align center, wrap");
        wrapper.add(update_btn, "align center, wrap");
        wrapper.add(select_dir_path_field, "align center, width 320:pref:max");
        drag_and_drop_panel.add(wrapper, "align 50% 50%");
    }
}
