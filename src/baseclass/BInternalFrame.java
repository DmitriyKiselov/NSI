package baseclass;

import javax.swing.JInternalFrame;
import my.guisap.FormRegister;
import my.guisap.GuiStaticVariables;
import my.guisap.sql.SqlOperations;
import my.guisap.utils.LogClass;

/**
 *
 * @author dima
 */
public class BInternalFrame extends JInternalFrame {

    public SqlOperations sql = new SqlOperations();
    public LogClass log = LogClass.getInstance();
    public FormRegister fr = FormRegister.getInstance();

    public BInternalFrame() {
    }

    public BInternalFrame(String title) {
        super(title);
    }

    public BInternalFrame(String title, boolean resizable) {
        super(title, resizable);
    }

    public BInternalFrame(String title, boolean resizable, boolean closable) {
        super(title, resizable, closable);
    }

    public BInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable) {
        super(title, resizable, closable, maximizable);
    }

    public BInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
    }

    public final void setCenter() {
        setBounds(GuiStaticVariables.screenWidth / 2 - getWidth() / 2, (GuiStaticVariables.screenHeight - 70) / 2 - getHeight() / 2, getWidth(), getHeight());
    }

    // нужно ли сохранять и восстанавливать размер и местоположение формы
    public boolean needSaveSize = true;
}
