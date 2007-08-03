package org.slf4j.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Converter {

	private List<File> javaFiles;

	private AbstractMatcher matcher;

	private Writer writer;

	private String source;

	private String destination;

	private boolean delDestination;

	private int conversionType;

	private boolean commentConversion;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Converter converter = new Converter();

		if (args.length > 0) {
			converter.source = args[0];
			converter.destination = converter.source + "/converted";
			if (args.length > 1) {
				converter.destination = args[1];
			}
			if (args.length > 2) {
				converter.delDestination = Boolean.parseBoolean(args[2]);
			}
		} else {
			converter.source = new File("").getAbsolutePath();
			converter.destination = new File(converter.source + "/converted")
					.getAbsolutePath();
		}

		converter.conversionType = Constant.JCL_TO_SLF4J;
		if (converter.init()) {
			File fileSource = converter.initSource();
			File fileDest = converter.initDestination();
			if (fileSource != null && fileDest != null) {
				converter.copy(fileSource);
				converter.selectFiles(fileDest);
				converter.convert(converter.javaFiles);
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean init() {
		matcher = AbstractMatcher.getMatcherImpl(conversionType);
		if (matcher == null) {
			return false;
		}
		matcher.setCommentConversion(commentConversion);
		writer = new Writer();
		return true;
	}

	/**
	 * 
	 * @return
	 */
	private File initSource() {
		System.out.println("source " + source);
		File fileSource = new File(source);
		if (!fileSource.isDirectory()) {
			System.out.println("source path is not a valid source directory");
			return null;
		}
		return fileSource;
	}

	/**
	 * 
	 * @return
	 */
	private File initDestination() {
		System.out.println("dest " + destination);
		File fileDest = new File(destination);
		if (fileDest.exists()) {
			if (delDestination) {
				delete(fileDest);
			} else {
				System.out.println("Destination file already exists");
			}
		}
		else if (!fileDest.mkdir()) {
			System.out.println("dest path is not a valid source directory");
			return null;
		}
		return fileDest;
	}

	/**
	 * 
	 * @param fdest
	 */
	private void delete(File fdest) {
		if (fdest.isDirectory()) {
			File[] files = fdest.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					delete(files[i]);
				}
			}
			fdest.delete();
		} else {
			fdest.delete();
		}
	}

	/**
	 * 
	 * @param fsource
	 */
	private void copy(File fsource) {
		String curentFileName = fsource.getAbsolutePath().substring(
				source.length());
		File fdest = new File(destination + "/" + curentFileName);
		if (fsource.isDirectory()) {
			File[] files = fsource.listFiles();
			fdest.mkdir();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					copy(files[i]);
				}
			}
		} else {
			copy(fsource, fdest);
		}
	}

	/**
	 * 
	 * @param fsource
	 * @param fdest
	 */
	private void copy(File fsource, File fdest) {
		try {
			FileInputStream fis = new FileInputStream(fsource);
			FileOutputStream fos = new FileOutputStream(fdest);
			FileChannel channelSource = fis.getChannel();
			FileChannel channelDest = fos.getChannel();
			if (channelSource.isOpen() && channelDest.isOpen()) {
				channelSource.transferTo(0, channelSource.size(), channelDest);
				channelSource.close();
				channelDest.close();
			} else {
				System.out.println("error copying file "
						+ fsource.getAbsolutePath());
			}

		} catch (FileNotFoundException exc) {
			System.out.println(exc.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	private List<File> selectFiles(File file) {
		if (javaFiles == null) {
			javaFiles = new ArrayList<File>();
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					selectFiles(files[i]);
				}
			}
		} else {
			if (file.getName().endsWith(".java")) {
				javaFiles.add(file);
			}
		}
		return javaFiles;
	}

	/**
	 * 
	 * @param lstFiles
	 */
	private void convert(List<File> lstFiles) {
		Iterator<File> itFile = lstFiles.iterator();
		while (itFile.hasNext()) {
			File currentFile = itFile.next();
			convert(currentFile);
		}
	}

	/**
	 * 
	 * @param file
	 */
	private void convert(File file) {
		File newFile = new File(file.getAbsolutePath() + "new");
		try {
			boolean isEmpty = false;
			writer.initFileWriter(newFile);
			FileReader freader = new FileReader(file);
			BufferedReader breader = new BufferedReader(freader);
			String line;
			String newLine;
			while (!isEmpty) {
				line = breader.readLine();
				if (line != null) {
					newLine = matcher.replace(line);
					writer.write(newLine);
				} else {
					isEmpty = true;
					writer.closeFileWriter();
					copy(newFile, file);
					delete(newFile);
				}
			}
		} catch (IOException exc) {
			System.out.println("error reading file " + exc);
		}
	}
}
