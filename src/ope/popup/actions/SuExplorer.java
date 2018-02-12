package ope.popup.actions;

import java.io.File;
import java.io.IOException;

public class SuExplorer  {
	 private boolean m_showTree;
     private boolean m_openNewExplorer;
     private String m_rootPath;
     private String m_selectedPath;


	public SuExplorer() {
		m_rootPath = null;
		setShowTree(true);
		setOpenNewExplorer(false);
	}
    public void setShowTree(boolean showTree)
    {
        m_showTree = showTree;
    }
    public void exec()
    {
    	try {
			Runtime.getRuntime().exec(getWindowsCommand(), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void setOpenNewExplorer(boolean newExplorer)
    {
        m_openNewExplorer = newExplorer;
    }

    public void setSelectedDir(File path)
    {
        if(path != null)
            m_selectedPath = path.getAbsolutePath();
    }

    protected String getWindowsCommand()
    {
        String args = null;
        String command = "explorer";
        if(m_openNewExplorer)
            args = "/n";
        if(m_showTree)
            if(args != null)
                args = args + "/e";
            else
                args = "/e";
        if(m_rootPath != null)
            if(args != null)
                args = args + ",/root," + m_rootPath;
            else
                args = "/root," + m_rootPath;
        if(m_selectedPath != null)
        {
            String c = "\"";
            if(args != null)
                args = args + ",";
            args = args + "/select," + c + m_selectedPath + c;
        }
        return command + " " + args;
    }



}
