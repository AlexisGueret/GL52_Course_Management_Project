package core.document;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

/**
 * 
 * @author lucas
 *
 */
public class Document {

	
	
	/**
	 * 
	 */
	public Document() 
	{
		
	}

	/**
	 * @param cours
	 * @param name of the file
	 */
	public static void openpdfcourse(String cours, String name) {

	      try {

	        File pdfFile = new File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator") + "Course" + System.getProperty("file.separator") + name);
	        if (pdfFile.exists()) {

	            if (Desktop.isDesktopSupported()) {
	                Desktop.getDesktop().open(pdfFile);
	            } else {
	                System.out.println("Awt Desktop is not supported!");
	            }

	        } else {
	            System.out.println("The file does not exist!");
	        }


	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }

	    }
	
	
	/**
	 * @param cours
	 * @param project 
	 * @param name of the file
	 */	
	public static void openpdfproject(String cours, String project,String name) {

	      try {

	        File pdfFile = new File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator")  + "Project" + System.getProperty("file.separator") + project + System.getProperty("file.separator") + name);
	        if (pdfFile.exists()) {

	            if (Desktop.isDesktopSupported()) {
	                Desktop.getDesktop().open(pdfFile);
	            } else {
	                System.out.println("Awt Desktop is not supported!");
	            }

	        } else {
	            System.out.println("The file does not exist!");
	        }


	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }

	    }
	
	/**
	 * @param cours
	 * @return list of file (String[])
	 */
	public static String[] SeeRepositorycourse(String cours) {
		
		  if(!new File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator") + "Course").exists())
	      {
	          // Create the folder with all his parents
	          new File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator") + "Course").mkdirs();
	      }
		
	    try {
		File repository = new File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator") + "Course");
        String list[] = repository.list();      
        return list;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	      }
	    return null;
        }

	/**
	 * @param cours
	 * @param project 
	 * @return list of file (String[])
	 */
	public static String[] seeRepositoryproject(String cours,String project) {

        if(!new File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator")  + "Project" + System.getProperty("file.separator") + project).exists())
      {
          // Create the folder with all his parents
          new File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator")  + "Project" + System.getProperty("file.separator") + project).mkdirs();
      }
		try {
		File repository = new File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator")  + "Project" + System.getProperty("file.separator") + project);
        String list[] = repository.list();
        return list;
		 } catch (Exception ex) {
		        ex.printStackTrace();
		      }
		    return null;
        }
	
	
	/**
	 * @param cours
	 * @param name
	 * @throws IOException
	 */
	public static void saveFilecourse(String cours) throws IOException {
		try {
		JFileChooser dialogue = new JFileChooser(new File("."));
		File fichier = null;
		File rep = new File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator") + "Course");
		if (dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) 
			{
				fichier = dialogue.getSelectedFile();
			}
		if (fichier!=null)
			{
				fichier.renameTo(new File(rep, fichier.getName()));
			}
		 } catch (Exception ex) {
		        ex.printStackTrace();
		      }
	}
	
	/**
	 * @param cours
	 * @param project
	 * @param name
	 * @throws IOException
	 */
	public static void saveFileproject(String cours, String project) throws IOException {
		try {
		JFileChooser dialogue = new JFileChooser(new File("."));
		File fichier = null;
		File rep = new  File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator")  + "Project" + System.getProperty("file.separator") + project);
		if (dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) 
			{
				fichier = dialogue.getSelectedFile();
			}
		if (fichier!=null)
			{
				fichier.renameTo(new File(rep, fichier.getName()));
			}
		 } catch (Exception ex) {
		        ex.printStackTrace();
		      }
	}


	/**
	 * @param cours
	 * @param project
	 * @param num 
	 * @param name
	 * @throws IOException
	 */
	public static void saveFileprojectsubject(String cours, String project, String num) throws IOException {
		try {
		JFileChooser dialogue = new JFileChooser(new File("."));
		File fichier = null;
		File rep = new  File("Resources" + System.getProperty("file.separator") + cours + System.getProperty("file.separator")  + "Project" + System.getProperty("file.separator") + project);
		if (dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) 
			{
				fichier = dialogue.getSelectedFile();
			}
		if (fichier!=null)
			{	   fichier.renameTo(new File(rep, num + ".pdf"));
		
			}
		 } catch (Exception ex) {
		        ex.printStackTrace();
		      }
	}
    
}


