package ope.popup.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.internal.resources.Resource;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class OPEAction implements IObjectActionDelegate {

	private Shell shell;
	private ISelection m_Selection;

	/**
	 * Constructor for Action1.
	 */
	public OPEAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	@SuppressWarnings("restriction")
	public void run(IAction action) {
		if (m_Selection != null && (m_Selection instanceof StructuredSelection)) {
			StructuredSelection selection = (StructuredSelection) m_Selection;
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IProjectNature) {
				IProjectNature javaProject = (IProjectNature) firstElement;
				File toFile = new File("");
				IPath location = javaProject.getProject().getLocation();
				startOpen(location, toFile);
			} else if (firstElement instanceof CompilationUnit) {
				CompilationUnit cu = (CompilationUnit) firstElement;
				IPath location = cu.getCompilationUnit().getJavaProject().getProject().getLocation();
				File toFile = cu.getPath().toFile();
				startOpen(location, toFile);
			} else if (firstElement instanceof JarPackageFragmentRoot) {
				JarPackageFragmentRoot jpFRoot = (JarPackageFragmentRoot) firstElement;
				IPath location = jpFRoot.getPath();
				File toFile = new File("");
				startOpen(location, toFile);
			} else if (firstElement instanceof PackageFragmentRoot) {
				PackageFragmentRoot pFRoot = (PackageFragmentRoot) firstElement;
				IPath location = pFRoot.getJavaProject().getProject().getLocation();
				File toFile = pFRoot.getPath().toFile();
				startOpen(location, toFile);
			} else if (firstElement instanceof PackageFragment) {
				PackageFragment pFrag = (PackageFragment) firstElement;
				IPath location = pFrag.getJavaProject().getProject().getLocation();
				File toFile = pFrag.getPath().toFile();
				startOpen(location, toFile);
			} else if (firstElement instanceof SourceType) {
				SourceType sType = (SourceType) firstElement;
				IPath location = sType.getJavaProject().getProject().getLocation();
				File toFile = sType.getPath().toFile();
				startOpen(location, toFile);
			}else{
	            if(firstElement instanceof IResource){
	                openResource(firstElement);
	            }
			}
		}

	}

	private void openResource(Object firstElement) {
		IResource fragment = (IResource) firstElement;
		File toFile = fragment.getFullPath().toFile();
		IPath location = toFile.exists() ? null : fragment.getProject()
				.getLocation();
		startOpen(location, toFile);
	}

	private void startOpen(IPath locationURL, File toFile2) {
		File selectedFile;
		if (locationURL != null) {
			File toFile = locationURL.toFile();
			String absolutePath = toFile2.toString();
			int indexOf = absolutePath.indexOf("\\");
			String substring = absolutePath.substring(indexOf + 1);
			String substring2 = substring
					.substring(substring.indexOf("\\") + 1);
			selectedFile = new File(toFile, substring2);
			if (!selectedFile.exists() && toFile.exists())
				selectedFile = toFile;
		} else {
			selectedFile = toFile2;
		}
		SuExplorer explorer = new SuExplorer();
		explorer.setSelectedDir(selectedFile);
		explorer.exec();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		m_Selection = selection;
	}

}
