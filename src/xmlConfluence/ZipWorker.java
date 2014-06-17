package xmlConfluence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipWorker {

    List<String> fileListZip;
    List<String> fileListUnzip;
    //for unzipping
    private static final String INPUT_ZIP_FILE = "X:\\xml\\test.zip";
    private static final String OUTPUT_FOLDER = "X:\\xml\\Neuer Ordner";
    
    //for zipping
    private String OUTPUT_ZIP_FILE = "X:\\xml\\test.zip";
    private String SOURCE_FOLDER = "X:\\xml";
 
    public ZipWorker(String directoryToZip, String directoryForOutput) {
//    	TODO fix me
//    	SOURCE_FOLDER = directoryToZip;
//    	OUTPUT_ZIP_FILE = directoryForOutput;
    	fileListZip = new ArrayList<String>();
    }
    
    public void zipIt(String directoryForOutput){
    	this.generateFileList(new File(SOURCE_FOLDER));
    	
    	byte[] buffer = new byte[1024];
 
    	try{
	    	FileOutputStream fos = new FileOutputStream(OUTPUT_ZIP_FILE);
	    	ZipOutputStream zos = new ZipOutputStream(fos);
	 
	    	for(String file : this.fileListZip){
	//    		System.out.println("File Added : " + file);
	    		ZipEntry ze= new ZipEntry(file);
	        	zos.putNextEntry(ze);
	        	FileInputStream in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
	        	int len;
	        	while ((len = in.read(buffer)) > 0) {
	        		zos.write(buffer, 0, len);
        	}
        	in.close();
    	}
    	zos.closeEntry();
    	zos.close();
 
//    	System.out.println("Done");
    }catch(IOException ex){
       ex.printStackTrace();   
    }
   }
 
    /**
     * Traverse a directory and get all files,
     * and add the file into fileList  
     * @param node file or directory
     */
    public void generateFileList(File node){
    	//add file only
		if(node.isFile()){
			//if Zip is included ignore it
			if (!node.getAbsoluteFile().toString().endsWith(".zip")) {
				fileListZip.add(generateZipEntry(node.getAbsoluteFile().toString()));
			}
		}
	 
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				generateFileList(new File(node, filename));
			}
		}
    }
    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file){
    	return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
    
    public void unZipIt(String zipFile, String outputFolder){
    	//TODO fix me
    	zipFile = INPUT_ZIP_FILE;
    	outputFolder = OUTPUT_FOLDER;
    	
        byte[] buffer = new byte[1024];
    
        try{
    
	       	//create output directory is not exists
	       	File folder = new File(OUTPUT_FOLDER);
	       	if(!folder.exists()){
	       		folder.mkdir();
	       	}
	    
	       	//get the zip file content
	       	ZipInputStream zis = 
	       		new ZipInputStream(new FileInputStream(zipFile));
	       	//get the zipped file list entry
	       	ZipEntry ze = zis.getNextEntry();
	    
	       	while(ze!=null){
	    
	       		String fileName = ze.getName();
	       		File newFile = new File(outputFolder + File.separator + fileName);
	//	              System.out.println("file unzip : "+ newFile.getAbsoluteFile());
	       		//create all non exists folders
	       		//else you will hit FileNotFoundException for compressed folder
	       		new File(newFile.getParent()).mkdirs();
	    
	       		FileOutputStream fos = new FileOutputStream(newFile);             
	    
	       		int len;
	       		while ((len = zis.read(buffer)) > 0) {
	       			fos.write(buffer, 0, len);
	       		}
	       		fos.close();   
	       		ze = zis.getNextEntry();
	       	}
        zis.closeEntry();
       	zis.close();
//       	System.out.println("Done");
    
       }catch(IOException ex){
          ex.printStackTrace(); 
       }
    } 
}
